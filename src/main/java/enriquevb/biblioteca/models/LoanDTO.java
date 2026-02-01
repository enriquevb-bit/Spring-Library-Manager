package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonDeserialize(builder = LoanDTO.LoanDTOBuilder.class)
@Builder
@Data
public class LoanDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("loanState")
    @NotNull
    private LoanState loanState;

    @JsonProperty("loanDate")
    private LocalDateTime loanDate;

    @JsonProperty("expiringDate")
    private LocalDateTime expiringDate;

    @JsonProperty("dueDate")
    private LocalDateTime dueDate;
}
