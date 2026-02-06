package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.AuthorDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface AuthorService {

    Optional<AuthorDTO> getAuthorById(UUID uuid);

    Page<AuthorDTO> listAuthors(String fullName, String nationality, Integer pageNumber, Integer pageSize);

    AuthorDTO saveNewAuthor(AuthorDTO author);

    Optional<AuthorDTO> updateAuthorById(UUID authorId, AuthorDTO author);

    Boolean deleteAuthorById(UUID authorId);

    Optional<AuthorDTO> patchAuthorById(UUID authorId, AuthorDTO author);
}
