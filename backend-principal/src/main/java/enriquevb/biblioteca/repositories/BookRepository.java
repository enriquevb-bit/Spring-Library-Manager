package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Page<Book> findAllByTitleIsLikeIgnoreCase(String title, Pageable pageable);

    Page<Book> findAllByIsbn(String isbn, Pageable pageable);

    Page<Book> findAllByTitleIsLikeIgnoreCaseAndIsbn(String title, String isbn, Pageable pageable);
}
