package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.entities.Loan;
import enriquevb.biblioteca.models.LoanState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    Page<Loan> findAllByLoanState(LoanState loanState, Pageable pageable);
}
