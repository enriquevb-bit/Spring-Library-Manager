package enriquevb.biblioteca.mappers;

import enriquevb.biblioteca.entities.Book;
import enriquevb.biblioteca.models.BookDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {

    Book bookDtoToBook(BookDTO bookDTO);

    BookDTO bookToBookDto(Book book);
}
