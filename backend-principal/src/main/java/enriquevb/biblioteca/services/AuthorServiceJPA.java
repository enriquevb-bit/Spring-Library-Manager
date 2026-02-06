package enriquevb.biblioteca.services;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.mappers.AuthorMapper;
import enriquevb.biblioteca.models.AuthorDTO;
import enriquevb.biblioteca.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class AuthorServiceJPA implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Optional<AuthorDTO> getAuthorById(UUID uuid) {
        return Optional.ofNullable(authorMapper
                .authorToAuthorDto(authorRepository.findById(uuid).orElse(null)));
    }

    @Override
    public Page<AuthorDTO> listAuthors(String fullName, String nationality, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Author> authorPage;

        if (StringUtils.hasText(fullName) && !StringUtils.hasText(nationality)) {
            authorPage = listAuthorsByName(fullName, pageRequest);
        } else if (!StringUtils.hasText(fullName) && StringUtils.hasText(nationality)) {
            authorPage = listAuthorByNationality(nationality, pageRequest);
        } else if (StringUtils.hasText(fullName) && StringUtils.hasText(nationality)) {
            authorPage = listAuthorsByNameAndNationality(fullName, nationality, pageRequest);
        } else {
            authorPage = authorRepository.findAll(pageRequest);
        }

        return authorPage.map(authorMapper::authorToAuthorDto);
    }

    private Page<Author> listAuthorsByNameAndNationality(String fullName, String nationality, PageRequest pageRequest) {
        return authorRepository.findAllByFullNameIsLikeIgnoreCaseAndNationality("%"+fullName+"%",nationality,pageRequest);
    }

    private Page<Author> listAuthorByNationality(String nationality, PageRequest pageRequest) {
        return authorRepository.findAllByNationality(nationality, pageRequest);
    }

    private Page<Author> listAuthorsByName(String fullName, PageRequest pageRequest) {
        return authorRepository.findAllByFullNameIsLikeIgnoreCase("%"+fullName+"%", pageRequest);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("fullName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);

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
