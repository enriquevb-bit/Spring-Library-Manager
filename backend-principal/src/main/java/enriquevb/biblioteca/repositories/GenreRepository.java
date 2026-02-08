package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Page<Genre> getGenreByNameIgnoreCase(String name, Pageable pageable);
}
