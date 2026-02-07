package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.config.SpringSecConfig;
import enriquevb.biblioteca.entities.Book;
import enriquevb.biblioteca.mappers.BookMapper;
import enriquevb.biblioteca.models.BookDTO;
import enriquevb.biblioteca.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerIT {

    @Autowired
    BookController bookController;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testListBooksDefaultPagination() throws Exception {
        mockMvc.perform(get(BookController.BOOK_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").exists());
    }

    @Test
    void testListBooksWithPagination() throws Exception {
        mockMvc.perform(get(BookController.BOOK_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("title", "The Hunger Games")
                        .queryParam("isbn", "978-0321125217")
                        .queryParam("pageNumber", "1")
                        .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListBooksByTitleAndIsbn() throws Exception {
        mockMvc.perform(get(BookController.BOOK_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("title", "The Hunger Games")
                        .queryParam("isbn", "978-0321125217"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListBooksByIsbn() throws Exception {
        mockMvc.perform(get(BookController.BOOK_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("isbn", "978-0132350884"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListBooksByTitle() throws Exception {
        mockMvc.perform(get(BookController.BOOK_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("title", "The Hunger Games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testPatchBookBadTitle() throws Exception {
        Book book = bookRepository.findAll().get(0);

        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("title", "New title 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        mockMvc.perform(patch(BookController.BOOK_PATH_ID, book.getId())
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookMap)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> bookController.deleteById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteByIdFound() {
        Book book = bookRepository.findAll().get(0);

        ResponseEntity responseEntity = bookController.deleteById(book.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(bookRepository.findById(book.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> bookController.updateById(UUID.randomUUID(), BookDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateExistingBook() {
        BookDTO bookDTO = bookMapper.bookToBookDto(bookRepository.findAll().get(0));
        bookDTO.setTitle("New title");
        bookRepository.save(bookMapper.bookDtoToBook(bookDTO));

        ResponseEntity responseEntity = bookController.updateById(bookDTO.getId(), bookDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(bookRepository.findById(bookDTO.getId()).get().getTitle()).isEqualTo("New title");
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewBook() {
        BookDTO bookDTO = BookDTO.builder()
                .title("Title")
                .build();

        ResponseEntity responseEntity = bookController.handlePost(bookDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] idLocation = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedId = UUID.fromString(idLocation[4]);

        Book book = bookRepository.findById(savedId).get();

        assertThat(book).isNotNull();
    }

    @Test
    void testBookIdNotFound() {
        assertThrows(NotFoundException.class, () -> bookController.getBookById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        BookDTO bookDTO = bookController.listBooks(null, null, 1, 50).getContent().get(0);

        assertThat(bookController.getBookById(bookDTO.getId())).isNotNull();
    }

    @Test
    void testListBooks() {
        Page<BookDTO> books = bookController.listBooks(null, null, 1, 50);

        assertThat(books.getContent().size()).isGreaterThan(0);

    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        bookRepository.deleteAll();
        Page<BookDTO> emptyList = bookController.listBooks(null, null, 1, 50);

        assertThat(emptyList.getContent().size()).isEqualTo(0);
    }
}