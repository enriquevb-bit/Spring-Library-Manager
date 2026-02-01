package enriquevb.biblioteca.services;

import enriquevb.biblioteca.mappers.AuthorMapper;
import enriquevb.biblioteca.models.AuthorDTO;
import enriquevb.biblioteca.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class AuthorServiceJPA implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Optional<AuthorDTO> getAuthorById(UUID uuid) {
        return Optional.ofNullable(authorMapper
                .authorToAuthorDto(authorRepository.findById(uuid).orElse(null)));
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO saveNewAuthor(AuthorDTO author) {
        return authorMapper.authorToAuthorDto(authorRepository
                .save(authorMapper.authorDtoToAuthor(author)));
    }

    @Override
    public Optional<AuthorDTO> updateAuthorById(UUID authorId, AuthorDTO author) {
        AtomicReference<Optional<AuthorDTO>> atomicReference = new AtomicReference<>();

        authorRepository.findById(authorId).ifPresentOrElse(foundAuthor -> {
            foundAuthor.setFullName(author.getFullName());
            foundAuthor.setNationality(author.getNationality());
            foundAuthor.setBirthDate(author.getBirthDate());
            atomicReference.set(Optional.of(authorMapper
                    .authorToAuthorDto(authorRepository.save(foundAuthor))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteAuthorById(UUID authorId) {
        if (authorRepository.existsById(authorId)) {
            authorRepository.deleteById(authorId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<AuthorDTO> patchAuthorById(UUID authorId, AuthorDTO author) {
        AtomicReference<Optional<AuthorDTO>> atomicReference = new AtomicReference<>();

        authorRepository.findById(authorId).ifPresentOrElse(foundAuthor -> {
            if (StringUtils.hasText(author.getFullName())) {
                foundAuthor.setFullName(author.getFullName());
            }
            if (StringUtils.hasText(author.getNationality())) {
                foundAuthor.setNationality(author.getNationality());
            }
            if (author.getBirthDate() != null) {
                foundAuthor.setBirthDate(author.getBirthDate());
            }
            atomicReference.set(Optional.of(authorMapper
                    .authorToAuthorDto(authorRepository.save(foundAuthor))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
