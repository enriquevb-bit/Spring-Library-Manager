package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Loan;
import enriquevb.biblioteca.models.LoanDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = {MemberMapper.class, LoanLineMapper.class})
public interface LoanMapper {

    Loan loanDtoToLoan(LoanDTO loanDTO);

    LoanDTO loanToLoanDto(Loan loan);

    @AfterMapping
    default void linkLoanLines(@MappingTarget Loan loan) {
        if (loan.getLoanLines() != null) {
            loan.getLoanLines().forEach(line -> line.setLoan(loan));
        }
    }
}
