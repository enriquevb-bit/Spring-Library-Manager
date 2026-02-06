package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@JsonDeserialize(builder = GenreDTO.GenreDTOBuilder.class)
@Builder
@Data
public class GenreDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("name")
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty("description")
    private String description;
}
