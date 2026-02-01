package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
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
}
