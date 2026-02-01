package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BootstrapData.class)
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void testSaveAuthor() {
        Author savedAuthor = authorRepository.save(Author.builder()
                .fullName("Gabriel García Márquez")
                .nationality("Colombian")
                .birthDate(LocalDate.of(1927, 3, 6))
                .build());

        authorRepository.flush();

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }
}
