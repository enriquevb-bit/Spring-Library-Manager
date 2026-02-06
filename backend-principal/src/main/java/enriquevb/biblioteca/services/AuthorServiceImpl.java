package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    Map<UUID, AuthorDTO> authorMap;

    public AuthorServiceImpl() {
        this.authorMap = new HashMap<>();

        AuthorDTO author1 = AuthorDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .fullName("John Ronald Reuel Tolkien")
                .birthDate(LocalDate.of(1892, 1, 3))
                .nationality("British")
                .build();

        AuthorDTO author2 = AuthorDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .fullName("George Raymond Richard Martin")
                .birthDate(LocalDate.of(1948, 9, 20))
                .nationality("American")
                .build();

        AuthorDTO author3 = AuthorDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .fullName("Suzanne Collins")
                .birthDate(LocalDate.of(1962, 8, 10))
                .nationality("American")
                .build();

        authorMap.put(author1.getId(), author1);
        authorMap.put(author2.getId(), author2);
        authorMap.put(author3.getId(), author3);
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(UUID uuid) {
        return Optional.ofNullable(authorMap.get(uuid));
    }

    @Override
    public Page<AuthorDTO> listAuthors(String fullName, String nationality, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(authorMap.values()));
    }

    @Override
    public AuthorDTO saveNewAuthor(AuthorDTO author) {
        AuthorDTO savedAuthor = AuthorDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .fullName(author.getFullName())
                .nationality(author.getNationality())
                .birthDate(author.getBirthDate())
                .build();

        authorMap.put(savedAuthor.getId(), savedAuthor);
        return savedAuthor;
    }

    @Override
    public Optional<AuthorDTO> updateAuthorById(UUID authorId, AuthorDTO author) {
        AuthorDTO existing = authorMap.get(authorId);

        if (existing != null) {
            existing.setFullName(author.getFullName());
            existing.setNationality(author.getNationality());
            existing.setBirthDate(author.getBirthDate());
            return Optional.of(existing);
        }

        return Optional.empty();
    }

    @Override
    public Boolean deleteAuthorById(UUID authorId) {
        if (authorMap.containsKey(authorId)) {
            authorMap.remove(authorId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<AuthorDTO> patchAuthorById(UUID authorId, AuthorDTO author) {
        AuthorDTO existing = authorMap.get(authorId);

        if (existing != null) {
            if (StringUtils.hasText(author.getFullName())) {
                existing.setFullName(author.getFullName());
            }
            if (StringUtils.hasText(author.getNationality())) {
                existing.setNationality(author.getNationality());
            }
            if (author.getBirthDate() != null) {
                existing.setBirthDate(author.getBirthDate());
            }
            return Optional.of(existing);
        }

        return Optional.empty();
    }
}
