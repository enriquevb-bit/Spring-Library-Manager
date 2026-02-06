package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.config.SpringSecConfig;
import enriquevb.biblioteca.models.BookDTO;
import enriquevb.biblioteca.services.BookService;
import enriquevb.biblioteca.services.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(SpringSecConfig.class)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookService bookService;

    BookServiceImpl bookServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BookDTO> beerArgumentCaptor;

    public static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRequestPostProcessor =
            jwt().jwt(jwt -> {
                jwt.claims(claims -> {
                            claims.put("scope", "openid");
                            claims.put("scope", "profile");
                        })
                        .subject("oidc-client")
                        .notBefore(Instant.now().minusSeconds(5));
            });

    @BeforeEach
    void setUp() {
        bookServiceImpl = new BookServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        BookDTO bookDTO = bookServiceImpl.listBooks(null, null,1, 50).getContent().get(0);

        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("title", "New Title");

        mockMvc.perform(patch(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .with(jwtRequestPostProcessor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookMap)))
                .andExpect(status().isNoContent());

        verify(bookService).patchBookById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(bookDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(bookMap.get("title")).isEqualTo(beerArgumentCaptor.getValue().getTitle());
    }

    @Test
    void testDeleteBook() throws Exception {
        BookDTO bookDTO = bookServiceImpl.listBooks(null, null, 1, 50).getContent().get(0);

        given(bookService.deleteById(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService).deleteById(uuidArgumentCaptor.capture());

        assertThat(bookDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDTO bookDTO = bookServiceImpl.listBooks(null, null, 1, 50).getContent().get(0);

        given(bookService.updateBookById(any(),any())).willReturn(Optional.of(bookDTO));

        mockMvc.perform(put(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isNoContent());

        verify(bookService).updateBookById(any(UUID.class), any(BookDTO.class));
    }

    @Test
    void testUpdateBookBlankTitle() throws Exception {

        BookDTO bookDTO = bookServiceImpl.listBooks(null, null, 1, 50).getContent().get(0);
        bookDTO.setTitle("");

        mockMvc.perform(put(BookController.BOOK_PATH_ID, bookDTO.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testCreateNewBook() throws Exception {
        BookDTO bookDTO = bookServiceImpl.listBooks(null, null, 1, 50).getContent().get(0);

        given(bookService.saveNewBook(any(BookDTO.class))).willReturn(bookServiceImpl.listBooks(null, null, 1, 50).getContent().get(1));

        mockMvc.perform(post(BookController.BOOK_PATH)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testCreateBookNullContent() throws Exception {

        BookDTO bookDTO = BookDTO.builder().build();

        MvcResult result = mockMvc.perform(post(BookController.BOOK_PATH)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void testListBeers() throws Exception {

        given(bookService.listBooks(any(), any(), any(), any())).willReturn(bookServiceImpl.listBooks(null, null, 1, 50));

        mockMvc.perform(get(BookController.BOOK_PATH)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    void getBookByIdNotFound() throws Exception {

        given(bookService.getBookById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BookController.BOOK_PATH_ID, UUID.randomUUID())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBookById() throws Exception {
        BookDTO book = bookServiceImpl.listBooks(null, null, 1, 50).getContent().get(0);

        given(bookService.getBookById(book.getId())).willReturn(Optional.of(book));

        mockMvc.perform(get(BookController.BOOK_PATH_ID, book.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(book.getId().toString())))
                .andExpect(jsonPath("$.title", is(book.getTitle())));
    }
}