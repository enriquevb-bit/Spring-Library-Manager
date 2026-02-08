package enriquevb.biblioteca.services;

import enriquevb.biblioteca.models.BookDTO;
import enriquevb.biblioteca.models.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GenreServiceImpl implements GenreService {

    Map<UUID, GenreDTO> genreMap;

    public GenreServiceImpl() {
        this.genreMap = new HashMap<>();

        GenreDTO genre1 = GenreDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Fantasy")
                .description("Worlds of magic, mythical creatures, and epic quests")
                .build();

        GenreDTO genre2 = GenreDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Science Fiction")
                .description("Futuristic technology, space exploration, and speculative science")
                .build();

        GenreDTO genre3 = GenreDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Dystopian")
                .description("Oppressive societies, survival, and rebellion against authoritarian regimes")
                .build();

        genreMap.put(genre1.getId(), genre1);
        genreMap.put(genre2.getId(), genre2);
        genreMap.put(genre3.getId(), genre3);
    }

    @Override
    public Page<GenreDTO> listGenres(String name, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(genreMap.values()));
    }

    @Override
    public Optional<GenreDTO> getGenreById(UUID id) {

        return Optional.ofNullable(genreMap.get(id));
    }

    @Override
    public GenreDTO saveNewGenre(GenreDTO genre) {

        GenreDTO savedGenre = GenreDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name(genre.getName())
                .description(genre.getDescription())
                .build();

        genreMap.put(savedGenre.getId(), savedGenre);

        return savedGenre;
    }

    @Override
    public Optional<GenreDTO> updateGenreById(UUID genreId, GenreDTO genre) {

        GenreDTO updatedGenre = genreMap.get(genreId);

        if (updatedGenre != null) {
            updatedGenre.setId(genre.getId());
            updatedGenre.setVersion(genre.getVersion());
            updatedGenre.setName(genre.getName());
            updatedGenre.setDescription(genre.getDescription());
            return Optional.of(updatedGenre);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleteById(UUID genreId) {

        if (genreMap.containsKey(genreId)){
            genreMap.remove(genreId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<GenreDTO> patchGenreById(UUID genreId, GenreDTO genre) {

        GenreDTO patchedGenre = genreMap.get(genreId);

        if (patchedGenre != null){
            if (StringUtils.hasText(genre.getName())){
                patchedGenre.setName(genre.getName());
            }
            if (StringUtils.hasText(genre.getDescription())){
                patchedGenre.setDescription(genre.getDescription());
            }
            return Optional.of(patchedGenre);
        }

        return Optional.empty();
    }
}
