package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.LoanState;
import enriquevb.biblioteca.models.RequestedLoanItems;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanService {

    Page<LoanDTO> listLoans(LoanState loanState, Integer pageNumber, Integer pageSize);

    Optional<LoanDTO> getLoanById(UUID loanId);

    LoanDTO saveNewLoan(LoanDTO loanDTO);

    Boolean deleteLoanById(UUID loanId);

    Optional<LoanDTO> updateLoanById(UUID id, LoanDTO loanDTO);

    Optional<LoanDTO> patchLoanById(UUID id, LoanDTO loanDTO);

    LoanDTO createLoan(UUID memberId, List<RequestedLoanItems<UUID, Integer>> items);

    LoanDTO returnLoan(UUID loanId);

}
