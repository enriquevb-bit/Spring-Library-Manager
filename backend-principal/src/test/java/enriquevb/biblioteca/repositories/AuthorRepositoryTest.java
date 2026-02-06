package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(BootstrapData.class)
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void getListByFullNameAndNationality() {
        Page<Author> testList = authorRepository.findAllByFullNameIsLikeIgnoreCaseAndNationality("%a%", "American", null);

        assertThat(testList.getContent().size()).isEqualTo(2);

    }

    @Test
    void getListByNationality() {
        Page<Author> testList = authorRepository.findAllByNationality("American", null);

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
                .nationality("Colombian")
                .birthDate(LocalDate.of(1927, 3, 6))
                .build());

        authorRepository.flush();

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }
}
