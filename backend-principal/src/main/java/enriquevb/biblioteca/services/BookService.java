package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.BookDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BookService {

    Page<BookDTO> listBooks(String title, String isbn, Integer pageNumber, Integer pageSize);

    Optional<BookDTO> getBookById(UUID id);

    BookDTO saveNewBook(BookDTO book);

    Optional<BookDTO> updateBookById(UUID bookId, BookDTO book);

    Boolean deleteById(UUID bookId);

    Optional<BookDTO> patchBookById(UUID bookId, BookDTO book);
}
