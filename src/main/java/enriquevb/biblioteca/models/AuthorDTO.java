package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize(builder = AuthorDTO.AuthorDTOBuilder.class)
@Builder
@Data
public class AuthorDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("fullName")
    @NotNull
    @NotBlank
    private String fullName;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("birthDate")
    private LocalDate birthDate;
}
