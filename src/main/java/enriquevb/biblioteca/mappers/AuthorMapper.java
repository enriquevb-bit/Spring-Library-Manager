package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.models.AuthorDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {

    Author authorDtoToAuthor(AuthorDTO authorDTO);

    AuthorDTO authorToAuthorDto(Author author);
}
