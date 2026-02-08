package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.LoanState;
import enriquevb.biblioteca.models.RequestedLoanItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {

    Map<UUID, LoanDTO> loanMap;

    public LoanServiceImpl() {
        this.loanMap = new HashMap<>();

        LoanDTO loan1 = LoanDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .loanState(LoanState.ACTIVE)
                .loanDate(LocalDateTime.now().minusDays(5))
                .expiringDate(LocalDateTime.now().plusDays(25))
                .dueDate(null)
                .build();

        LoanDTO loan2 = LoanDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .loanState(LoanState.RETURNED)
                .loanDate(LocalDateTime.now().minusDays(30))
                .expiringDate(LocalDateTime.now().minusDays(1))
                .dueDate(LocalDateTime.now().minusDays(3))
                .build();

        LoanDTO loan3 = LoanDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .loanState(LoanState.OVERDUE)
                .loanDate(LocalDateTime.now().minusDays(45))
                .expiringDate(LocalDateTime.now().minusDays(15))
                .dueDate(null)
                .build();

        loanMap.put(loan1.getId(), loan1);
        loanMap.put(loan2.getId(), loan2);
        loanMap.put(loan3.getId(), loan3);
    }

    @Override
    public LoanDTO createLoan(UUID memberId, List<RequestedLoanItems<UUID, Integer>> items) {

        //TODO

        return null;
    }

    @Override
    public Page<LoanDTO> listLoans(LoanState loanState, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(loanMap.values()));
    }

    @Override
    public Optional<LoanDTO> getLoanById(UUID loanId) {
        return Optional.ofNullable(loanMap.get(loanId));
    }

    @Override
    public LoanDTO saveNewLoan(LoanDTO loanDTO) {
        LoanDTO savedLoan = LoanDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .loanState(loanDTO.getLoanState())
                .loanDate(loanDTO.getLoanDate())
                .expiringDate(loanDTO.getExpiringDate())
                .dueDate(loanDTO.getDueDate())
                .build();

        loanMap.put(savedLoan.getId(), savedLoan);
        return savedLoan;
    }

    @Override
    public Optional<LoanDTO> updateLoanById(UUID id, LoanDTO loanDTO) {
        LoanDTO existing = loanMap.get(id);

        if (existing != null) {
            existing.setLoanState(loanDTO.getLoanState());
            existing.setLoanDate(loanDTO.getLoanDate());
            existing.setExpiringDate(loanDTO.getExpiringDate());
            existing.setDueDate(loanDTO.getDueDate());
            return Optional.of(existing);
        }

        return Optional.empty();
    }

    @Override
    public Boolean deleteLoanById(UUID loanId) {
        if (loanMap.containsKey(loanId)) {
            loanMap.remove(loanId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<LoanDTO> patchLoanById(UUID id, LoanDTO loanDTO) {
        LoanDTO existing = loanMap.get(id);

        if (existing != null) {
            if (loanDTO.getLoanState() != null) {
                existing.setLoanState(loanDTO.getLoanState());
            }
            if (loanDTO.getLoanDate() != null) {
                existing.setLoanDate(loanDTO.getLoanDate());
            }
            if (loanDTO.getExpiringDate() != null) {
                existing.setExpiringDate(loanDTO.getExpiringDate());
            }
            if (loanDTO.getDueDate() != null) {
                existing.setDueDate(loanDTO.getDueDate());
            }
            return Optional.of(existing);
        }

        return Optional.empty();
    }
}
