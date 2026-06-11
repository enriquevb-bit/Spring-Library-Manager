package enriquevb.biblioteca.services;

import enriquevb.biblioteca.entities.Book;
import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.MemberState;
import enriquevb.biblioteca.models.RequestedLoanItems;
import enriquevb.biblioteca.repositories.BookRepository;
import enriquevb.biblioteca.repositories.LoanRepository;
import enriquevb.biblioteca.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoanServiceJPAIT {

    @Autowired
    LoanService loanService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LoanRepository loanRepository;

    private Member activeMember() {
        return memberRepository.findAllByMemberState(MemberState.ACTIVE, PageRequest.of(0, 1))
                .getContent().get(0);
    }

    private Book bookWithCopies() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAvailableCopies() >= 2)
                .findFirst().orElseThrow();
    }

    // Sin @Transactional a propósito: cada llamada al servicio va en su propia
    // transacción, como en producción, y el propio borrado deja el stock como estaba.
    @Test
    void deleteActiveLoanRestoresStock() {
        Member member = activeMember();
        Book book = bookWithCopies();
        int initialCopies = book.getAvailableCopies();

        LoanDTO loan = loanService.createLoan(member.getId(),
                List.of(new RequestedLoanItems<>(book.getId(), 2)));
        assertThat(bookRepository.findById(book.getId()).get().getAvailableCopies())
                .isEqualTo(initialCopies - 2);

        Boolean deleted = loanService.deleteLoanById(loan.getId());

        assertThat(deleted).isTrue();
        assertThat(loanRepository.existsById(loan.getId())).isFalse();
        assertThat(bookRepository.findById(book.getId()).get().getAvailableCopies())
                .isEqualTo(initialCopies);
    }

    // Sin @Transactional a propósito: cada llamada al servicio va en su propia
    // transacción, como en producción, y el propio borrado deja el stock como estaba.
    @Test
    void deleteReservedLoanRestoresStock() {
        Member member = activeMember();
        Book book = bookWithCopies();
        int initialCopies = book.getAvailableCopies();

        LoanDTO loan = loanService.reserveLoan(member.getId(),
                List.of(new RequestedLoanItems<>(book.getId(), 1)));

        assertThat(loanService.deleteLoanById(loan.getId())).isTrue();
        assertThat(bookRepository.findById(book.getId()).get().getAvailableCopies())
                .isEqualTo(initialCopies);
    }

    // Sin @Transactional a propósito: cada llamada al servicio va en su propia
    // transacción, como en producción, y el propio borrado deja el stock como estaba.
    @Test
    void deleteReturnedLoanDoesNotRestoreStockTwice() {
        Member member = activeMember();
        Book book = bookWithCopies();
        int initialCopies = book.getAvailableCopies();

        LoanDTO loan = loanService.createLoan(member.getId(),
                List.of(new RequestedLoanItems<>(book.getId(), 2)));
        loanService.returnLoan(loan.getId());
        assertThat(bookRepository.findById(book.getId()).get().getAvailableCopies())
                .isEqualTo(initialCopies);

        assertThat(loanService.deleteLoanById(loan.getId())).isTrue();

        assertThat(bookRepository.findById(book.getId()).get().getAvailableCopies())
                .isEqualTo(initialCopies);
    }

    @Test
    void deleteLoanByIdNotFoundReturnsFalse() {
        assertThat(loanService.deleteLoanById(UUID.randomUUID())).isFalse();
    }
}
