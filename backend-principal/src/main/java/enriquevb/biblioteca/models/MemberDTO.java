package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enriquevb.biblioteca.entities.Loan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonDeserialize(builder = MemberDTO.MemberDTOBuilder.class)
@Builder
@Data
public class MemberDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("name")
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty("email")
    @NotNull
    @NotBlank
    private String email;

    @JsonProperty("memberState")
    @NotNull
    private MemberState memberState;

    @JsonProperty("registerDate")
    private LocalDateTime registerDate;

    @Builder.Default
    @JsonProperty("loans")
    private Set<Loan> loans = new HashSet<>();
}
