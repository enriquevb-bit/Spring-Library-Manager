package enriquevb.biblioteca.services;

import enriquevb.biblioteca.controllers.MemberNotActiveException;
import enriquevb.biblioteca.controllers.NotEnoughCopiesException;
import enriquevb.biblioteca.controllers.NotFoundException;
import enriquevb.biblioteca.entities.Book;
import enriquevb.biblioteca.entities.Loan;
import enriquevb.biblioteca.entities.LoanLine;
import enriquevb.biblioteca.mappers.BookMapper;
import enriquevb.biblioteca.mappers.LoanLineMapper;
import enriquevb.biblioteca.mappers.LoanMapper;
import enriquevb.biblioteca.mappers.MemberMapper;
import enriquevb.biblioteca.models.*;
import enriquevb.biblioteca.repositories.BookRepository;
import enriquevb.biblioteca.repositories.LoanRepository;
import enriquevb.biblioteca.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class LoanServiceJPA implements LoanService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    private final LoanLineMapper loanLineMapper;
    private final LoanMapper loanMapper;
    private final MemberMapper memberMapper;
    private final BookMapper bookMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Transactional
    @Override
    public LoanDTO createLoan(UUID memberId, List<RequestedLoanItems<UUID, Integer>> items) {

        MemberDTO requestedMember = memberMapper.memberToMemberDto(memberRepository.findById(memberId).orElseThrow(NotFoundException::new));

        if (requestedMember.getMemberState() == MemberState.ACTIVE && !items.isEmpty()){

            LoanDTO loanDTO = LoanDTO.builder()
                    .loanDate(LocalDateTime.now())
                    .loanState(LoanState.ACTIVE)
                    .build();
            loanDTO.setExpiringDate(loanDTO.getLoanDate().plusDays(14));
            loanDTO.setMember(requestedMember);

            Set<LoanLineDTO> loanLines = new HashSet<>();

            for (RequestedLoanItems<UUID, Integer> item : items){
                UUID bookId = item.getBookId();
                Integer quantity = item.getQuantity();
                Book book = bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
                BookDTO bookDTO = bookMapper.bookToBookDto(book);

                if (book.getAvailableCopies() >= quantity) {

                    book.setAvailableCopies(book.getAvailableCopies() - quantity);
                    bookRepository.save(book);

                    LoanLineDTO loanLineDTO = LoanLineDTO.builder()
                            .orderedQuantity(quantity)
                            .build();
                    loanLineDTO.setBook(bookDTO);
                    loanLineDTO.setLoan(loanDTO);

                    loanLines.add(loanLineDTO);
                }else {

                    throw new NotEnoughCopiesException();
                }

            }
            loanDTO.setLoanLines(loanLines);
            return loanMapper.loanToLoanDto(loanRepository.save(loanMapper.loanDtoToLoan(loanDTO)));
        }else {

            throw new MemberNotActiveException();
        }
    }

    @Override
    public Optional<LoanDTO> getLoanById(UUID loanId) {
        return Optional.ofNullable(loanMapper
                .loanToLoanDto(loanRepository.findById(loanId).orElse(null)));
    }

    @Override
    public Page<LoanDTO> listLoans(LoanState loanState, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Loan> loanPage;

        if (loanState != null) {
            loanPage = loanRepository.findAllByLoanState(loanState, pageRequest);
        } else {
            loanPage = loanRepository.findAll(pageRequest);
        }

        return loanPage.map(loanMapper::loanToLoanDto);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize != null && pageSize > 0) {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        } else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by(Sort.Order.asc("loanDate"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    @Override
    public LoanDTO saveNewLoan(LoanDTO loanDTO) {
        return loanMapper.loanToLoanDto(loanRepository
                .save(loanMapper.loanDtoToLoan(loanDTO)));
    }

    @Override
    public Optional<LoanDTO> updateLoanById(UUID id, LoanDTO loanDTO) {
        AtomicReference<Optional<LoanDTO>> atomicReference = new AtomicReference<>();

        loanRepository.findById(id).ifPresentOrElse(foundLoan -> {
            foundLoan.setLoanState(loanDTO.getLoanState());
            foundLoan.setLoanDate(loanDTO.getLoanDate());
            foundLoan.setExpiringDate(loanDTO.getExpiringDate());
            foundLoan.setDueDate(loanDTO.getDueDate());
            atomicReference.set(Optional.of(loanMapper
                    .loanToLoanDto(loanRepository.save(foundLoan))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteLoanById(UUID loanId) {
        if (loanRepository.existsById(loanId)) {
            loanRepository.deleteById(loanId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<LoanDTO> patchLoanById(UUID id, LoanDTO loanDTO) {
        AtomicReference<Optional<LoanDTO>> atomicReference = new AtomicReference<>();

        loanRepository.findById(id).ifPresentOrElse(foundLoan -> {
            if (loanDTO.getLoanState() != null) {
                foundLoan.setLoanState(loanDTO.getLoanState());
            }
            if (loanDTO.getLoanDate() != null) {
                foundLoan.setLoanDate(loanDTO.getLoanDate());
            }
            if (loanDTO.getExpiringDate() != null) {
                foundLoan.setExpiringDate(loanDTO.getExpiringDate());
            }
            if (loanDTO.getDueDate() != null) {
                foundLoan.setDueDate(loanDTO.getDueDate());
            }
            atomicReference.set(Optional.of(loanMapper
                    .loanToLoanDto(loanRepository.save(foundLoan))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
