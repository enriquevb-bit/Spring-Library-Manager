package enriquevb.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
}
