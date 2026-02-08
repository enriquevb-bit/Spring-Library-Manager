package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.GenreDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface GenreService {

    Page<GenreDTO> listGenres(String name, Integer pageNumber, Integer pageSize);

    Optional<GenreDTO> getGenreById(UUID id);

    GenreDTO saveNewGenre(GenreDTO genre);

    Optional<GenreDTO> updateGenreById(UUID genreId, GenreDTO genre);

    Boolean deleteById(UUID genreId);

    Optional<GenreDTO> patchGenreById(UUID genreId, GenreDTO genre);
}
