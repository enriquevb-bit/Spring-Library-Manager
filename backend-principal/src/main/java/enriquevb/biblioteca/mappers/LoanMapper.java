package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Loan;
import enriquevb.biblioteca.models.LoanDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {MemberMapper.class, LoanLineMapper.class})
public interface LoanMapper {

    Loan loanDtoToLoan(LoanDTO loanDTO);

    LoanDTO loanToLoanDto(Loan loan);
}
