package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.models.AuthorDTO;
import enriquevb.biblioteca.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    public static final String AUTHOR_PATH = "/api/v1/author";
    public static final String AUTHOR_PATH_ID = AUTHOR_PATH + "/{authorId}";

    private final AuthorService authorService;

    @PatchMapping(AUTHOR_PATH_ID)
    public ResponseEntity patchAuthorById(@PathVariable("authorId") UUID authorId,
                                          @RequestBody AuthorDTO author) {

        authorService.patchAuthorById(authorId, author);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(AUTHOR_PATH_ID)
    public ResponseEntity deleteAuthorById(@PathVariable("authorId") UUID authorId) {

        if (!authorService.deleteAuthorById(authorId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(AUTHOR_PATH_ID)
    public ResponseEntity updateAuthorById(@PathVariable("authorId") UUID authorId,
                                           @RequestBody AuthorDTO author) {

        if (authorService.updateAuthorById(authorId, author).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(AUTHOR_PATH)
    public ResponseEntity handlePost(@RequestBody AuthorDTO author) {
        AuthorDTO savedAuthor = authorService.saveNewAuthor(author);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", AUTHOR_PATH + "/" + savedAuthor.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(AUTHOR_PATH)
    public Page<AuthorDTO> listAuthors(@RequestParam(required = false) String fullName,
                                       @RequestParam(required = false) String nationality,
                                       @RequestParam(required = false) Integer pageNumber,
                                       @RequestParam(required = false) Integer pageSize) {
        return authorService.listAuthors(fullName, nationality, pageNumber, pageSize);
    }

    @GetMapping(value = AUTHOR_PATH_ID)
    public AuthorDTO getAuthorById(@PathVariable("authorId") UUID id) {
        return authorService.getAuthorById(id).orElseThrow(NotFoundException::new);
    }
}
