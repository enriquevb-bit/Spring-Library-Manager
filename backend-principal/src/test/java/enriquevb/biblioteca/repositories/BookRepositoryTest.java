package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Book;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BootstrapData.class)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testTitleTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            bookRepository.save(Book.builder()
                    .title("AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA")
                    .price(new BigDecimal(1))
                    .isbn("1231231")
                    .availableCopies(3)
                    .build());

            bookRepository.flush();
        });
    }

    @Test
    void getListByTitleAndIsbn() {
        Page<Book> testList = bookRepository.findAllByTitleIsLikeIgnoreCaseAndIsbn("%a%", "978-0321125217", null);

        assertThat(testList.getContent().size()).isEqualTo(1);

    }

    @Test
    void getListByIsbn() {
        Page<Book> testList = bookRepository.findAllByIsbn("978-0321125217", null);

        assertThat(testList.getContent().size()).isEqualTo(1);
    }

    @Test
    void getListByTitle() {
        Page<Book> list = bookRepository.findAllByTitleIsLikeIgnoreCase("%a%", null);

        assertThat(list.getContent().size()).isEqualTo(2);

    }

    @Test
    void testSaveBook() {
        Book testBook = bookRepository.save(Book.builder()
                        .title("eoaflsd")
                        .price(new BigDecimal(1))
                        .isbn("1231231")
                        .availableCopies(3)
                .build());

        bookRepository.flush();

        assertThat(testBook.getTitle()).isNotNull();
        assertThat(testBook.getId()).isNotNull();
    }
}