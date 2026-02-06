package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@JsonDeserialize(builder = LoanLineDTO.LoanLineDTOBuilder.class)
@Builder
@Data
public class LoanLineDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("loanQuantity")
    @NotNull
    private Integer loanQuantity;
}
