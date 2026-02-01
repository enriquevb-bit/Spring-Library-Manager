package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.AuthorDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorService {

    Optional<AuthorDTO> getAuthorById(UUID uuid);

    List<AuthorDTO> getAllAuthors();

    AuthorDTO saveNewAuthor(AuthorDTO author);

    Optional<AuthorDTO> updateAuthorById(UUID authorId, AuthorDTO author);

    Boolean deleteAuthorById(UUID authorId);

    Optional<AuthorDTO> patchAuthorById(UUID authorId, AuthorDTO author);
}
