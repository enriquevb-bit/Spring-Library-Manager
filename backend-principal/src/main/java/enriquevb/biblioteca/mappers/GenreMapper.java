package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Genre;
import enriquevb.biblioteca.models.GenreDTO;
import org.mapstruct.Mapper;

@Mapper
public interface GenreMapper {

    Genre genreDtoToGenre(GenreDTO genreDTO);

    GenreDTO genreToGenreDto(Genre genre);
}
