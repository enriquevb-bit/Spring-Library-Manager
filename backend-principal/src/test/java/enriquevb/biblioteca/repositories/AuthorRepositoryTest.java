package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(BootstrapData.class)
@TestPropertySource(properties = "spring.sql.init.mode=never")
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void getListByFullNameAndCountry() {
        Page<Author> testList = authorRepository.findAllByFullNameIsLikeIgnoreCaseAndCountry("%a%", "American", null);

        assertThat(testList.getContent().size()).isEqualTo(2);

    }

    @Test
    void getListByCountry() {
        Page<Author> testList = authorRepository.findAllByCountry("American", null);

        assertThat(testList.getContent().size()).isEqualTo(2);
    }

    @Test
    void getListByFullName() {
        Page<Author> list = authorRepository.findAllByFullNameIsLikeIgnoreCase("%a%", null);

        assertThat(list.getContent().size()).isEqualTo(3);

    }

    @Test
    void testSaveAuthor() {
        Author savedAuthor = authorRepository.save(Author.builder()
                .fullName("Gabriel García Márquez")
                .country("Colombian")
                .birthDate(LocalDate.of(1927, 3, 6))
                .build());

        authorRepository.flush();

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }
}
