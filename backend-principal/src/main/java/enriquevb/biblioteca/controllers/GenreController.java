package enriquevb.biblioteca.controllers;


import enriquevb.biblioteca.models.BookDTO;
import enriquevb.biblioteca.models.GenreDTO;
import enriquevb.biblioteca.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private static final String GENRE_PATH = "/api/v1/genre";
    private static final String GENRE_PATH_ID = GENRE_PATH + "/{genreId}";

    private final GenreService genreService;

    @PatchMapping(GENRE_PATH_ID)
    public ResponseEntity patchBookById(@PathVariable("genreId") UUID id, @RequestBody GenreDTO genreDTO){

        genreService.patchGenreById(id, genreDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(GENRE_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("genreId") UUID id){

        if(! genreService.deleteById(id)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(GENRE_PATH_ID)
    public ResponseEntity updateById(@PathVariable("genreId") UUID id, @Validated @RequestBody GenreDTO genre){

        if( genreService.updateGenreById(id, genre).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(GENRE_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody GenreDTO genreDTO){

        GenreDTO savedGenre = genreService.saveNewGenre(genreDTO);

        HttpHeaders header = new HttpHeaders();
        header.add("Location", GENRE_PATH + "/" + savedGenre.getId().toString());

        return new ResponseEntity(header, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<GenreDTO> listAllGenres(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) Integer pageNumber,
                                     @RequestParam(required = false) Integer pageSize){

        return genreService.listGenres(name, pageNumber, pageSize);
    }

    @GetMapping(GENRE_PATH_ID)
    public GenreDTO getGenreById(@PathVariable("genreId")UUID id){

        return genreService.getGenreById(id).orElseThrow(NotFoundException::new);
    }
}
