package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
