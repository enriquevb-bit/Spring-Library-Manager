package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.models.BookDTO;
import enriquevb.biblioteca.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {

    public static final String BOOK_PATH = "/api/v1/book";
    public static final String BOOK_PATH_ID = BOOK_PATH + "/{bookId}";

    private final BookService bookService;

    @PatchMapping(BOOK_PATH_ID)
    public ResponseEntity patchBookById(@PathVariable("bookId") UUID bookId, @RequestBody BookDTO book){

        bookService.patchBookById(bookId, book);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BOOK_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("bookId") UUID bookId){

        if(! bookService.deleteById(bookId)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BOOK_PATH_ID)
    public ResponseEntity updateById(@PathVariable("bookId") UUID bookId, @Validated @RequestBody BookDTO book){

        if( bookService.updateBookById(bookId, book).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BOOK_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BookDTO book){

        BookDTO savedBook = bookService.saveNewBook(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BOOK_PATH + "/" + savedBook.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = BOOK_PATH)
    public Page<BookDTO> listBooks(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) String isbn,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize){
        return bookService.listBooks(title, isbn, pageNumber, pageSize);
    }

    @GetMapping(value = BOOK_PATH_ID)
    public BookDTO getBookById(@PathVariable("bookId") UUID bookId){

        log.debug("Get Book by Id - in controller");

        return bookService.getBookById(bookId).orElseThrow(NotFoundException::new);
    }
}
