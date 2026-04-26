package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.LoanLine;
import enriquevb.biblioteca.models.LoanLineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {BookMapper.class})
public interface LoanLineMapper {

    LoanLine loanLineDtoToLoanLine(LoanLineDTO loanLineDTO);

    @Mapping(target = "loan", ignore = true)
    LoanLineDTO loanLineToLoanLineDto(LoanLine loanLine);
}
