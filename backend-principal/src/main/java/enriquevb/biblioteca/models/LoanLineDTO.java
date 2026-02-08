package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import enriquevb.biblioteca.entities.Book;
import enriquevb.biblioteca.entities.Loan;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonDeserialize(builder = LoanLineDTO.LoanLineDTOBuilder.class)
@Builder
@Data
public class LoanLineDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("orderedQuantity")
    @NotNull
    private Integer orderedQuantity;

    @JsonProperty("returnedQuantity")
    private Integer returnedQuantity;

    @NotNull
    private BookDTO book;

    @JsonIgnore
    @NotNull
    private LoanDTO loan;
}
