package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.LoanLine;
import enriquevb.biblioteca.models.LoanLineDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {BookMapper.class, LoanMapper.class})
public interface LoanLineMapper {

    LoanLine loanLineDtoToLoanLine(LoanLineDTO loanLineDTO);

    LoanLineDTO loanLineToLoanLineDto(LoanLine loanLine);
}
