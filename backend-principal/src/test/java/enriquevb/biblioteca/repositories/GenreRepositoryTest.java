package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.bootstrap.BootstrapData;
import enriquevb.biblioteca.entities.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BootstrapData.class)
class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    @Test
    void testSaveGenre() {
        Genre savedGenre = genreRepository.save(Genre.builder()
                .name("Science Fiction")
                .description("Fiction based on scientific discoveries or advanced technology")
                .build());

        genreRepository.flush();

        assertThat(savedGenre).isNotNull();
        assertThat(savedGenre.getId()).isNotNull();
    }
}
