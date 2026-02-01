package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Loan;
import enriquevb.biblioteca.models.LoanDTO;
import org.mapstruct.Mapper;

@Mapper
public interface LoanMapper {

    Loan loanDtoToLoan(LoanDTO loanDTO);

    LoanDTO loanToLoanDto(Loan loan);
}
