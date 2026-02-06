package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Loan;
import enriquevb.biblioteca.models.LoanState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BootstrapData.class)
class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;

    @Test
    void testSaveLoan() {
        Loan savedLoan = loanRepository.save(Loan.builder()
                .loanState(LoanState.ACTIVE)
                .loanDate(LocalDateTime.now())
                .expiringDate(LocalDateTime.now().plusDays(14))
                .build());

        loanRepository.flush();

        assertThat(savedLoan).isNotNull();
        assertThat(savedLoan.getId()).isNotNull();
    }
}
