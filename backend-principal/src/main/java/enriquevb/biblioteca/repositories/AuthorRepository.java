package enriquevb.biblioteca.repositories;

import enriquevb.biblioteca.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Page<Author> findAllByFullNameIsLikeIgnoreCase(String fullName, Pageable pageable);

    Page<Author> findAllByNationality(String nationality, Pageable pageable);

    Page<Author> findAllByFullNameIsLikeIgnoreCaseAndNationality(String fullName, String nationality, Pageable pageable);
}
