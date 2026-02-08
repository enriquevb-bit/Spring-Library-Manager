package enriquevb.biblioteca.services;

import enriquevb.biblioteca.entities.Genre;
import enriquevb.biblioteca.mappers.GenreMapper;
import enriquevb.biblioteca.models.GenreDTO;
import enriquevb.biblioteca.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Primary
@Service
@RequiredArgsConstructor
public class GenreServiceJPA implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    private final static Integer DEFAULT_PAGE_NUMBER = 0;
    private final static Integer DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<GenreDTO> listGenres(String name, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Genre> genrePage;

        if (StringUtils.hasText(name)){
            genrePage = listGenresByName(name, pageRequest);
        }else {
            genrePage = genreRepository.findAll(pageRequest);
        }

        return genrePage.map(genreMapper::genreToGenreDto);
    }

    private Page<Genre> listGenresByName(String name, PageRequest pageRequest) {
        return genreRepository.getGenreByNameIgnoreCase("%" + name + "%", pageRequest);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryNumber;
        int querySize;

        if (pageNumber != null && pageNumber > 0){
            queryNumber = pageNumber - 1;
        }else{
            queryNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize != null && pageSize > 0){
            if (pageSize > 1000){
                querySize = 1000;
            }else {
                querySize = pageSize;
            }
        }else {
            querySize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by(Sort.Order.asc("name"));

        return PageRequest.of(queryNumber, querySize, sort);
    }

    @Override
    public Optional<GenreDTO> getGenreById(UUID id) {

        return Optional.ofNullable(genreMapper.genreToGenreDto(genreRepository.findById(id).orElse(null)));
    }

    @Override
    public GenreDTO saveNewGenre(GenreDTO genre) {

        return genreMapper.genreToGenreDto(genreRepository.save(genreMapper.genreDtoToGenre(genre)));
    }

    @Override
    public Optional<GenreDTO> updateGenreById(UUID genreId, GenreDTO genre) {

        AtomicReference<Optional<GenreDTO>> atomicReference = new AtomicReference<>();

        genreRepository.findById(genreId).ifPresentOrElse(updatedGenre -> {
            updatedGenre.setId(genre.getId());
            updatedGenre.setVersion(genre.getVersion());
            updatedGenre.setName(genre.getName());
            updatedGenre.setDescription(genre.getDescription());
            atomicReference.set(Optional.of(genreMapper.genreToGenreDto(updatedGenre)));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID genreId) {
        if (genreRepository.existsById(genreId)){
            genreRepository.deleteById(genreId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<GenreDTO> patchGenreById(UUID genreId, GenreDTO genre) {

        AtomicReference<Optional<GenreDTO>> atomicReference = new AtomicReference<>();

        genreRepository.findById(genreId).ifPresentOrElse(patchedGenre -> {
            if (StringUtils.hasText(genre.getName())){
                patchedGenre.setName(genre.getName());
            }
            if (StringUtils.hasText(genre.getDescription())){
                patchedGenre.setDescription(genre.getDescription());
            }
            atomicReference.set(Optional.of(genreMapper.genreToGenreDto(patchedGenre)));
            },
                () -> atomicReference.set(Optional.empty())
                );

        return atomicReference.get();
    }
}
