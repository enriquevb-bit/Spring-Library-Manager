package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonDeserialize(builder = BookDTO.BookDTOBuilder.class)
@Builder
@Data
public class BookDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("isbn")
    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(?:97[89][- ]?(?:\\d[- ]?){9}\\d|(?:\\d[- ]?){9}[\\dXx])$",
            message = "El ISBN debe ser un ISBN-10 o ISBN-13 válido"
    )
    private String isbn;

    @JsonProperty("title")
    @NotNull
    @NotBlank
    private String title;

    @JsonProperty("availableCopies")
    @NotNull
    private Integer availableCopies;

    @JsonProperty("price")
    @NotNull
    private BigDecimal price;

    @JsonProperty("publishedDate")
    private LocalDate publishedDate;

    @JsonProperty("lastModifiedDate")
    private LocalDateTime lastModifiedDate;

    @Builder.Default
    @JsonProperty("authors")
    private Set<AuthorDTO> authors = new HashSet<>();

    @Builder.Default
    @JsonProperty("genres")
    private Set<GenreDTO> genres = new HashSet<>();
}
