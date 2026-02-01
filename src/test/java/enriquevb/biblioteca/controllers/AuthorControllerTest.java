package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.models.AuthorDTO;
import enriquevb.biblioteca.services.AuthorService;
import enriquevb.biblioteca.services.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthorService authorService;

    AuthorServiceImpl authorServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<AuthorDTO> authorArgumentCaptor;

    @BeforeEach
    void setUp() {
        authorServiceImpl = new AuthorServiceImpl();
    }

    @Test
    void testPatchAuthor() throws Exception {
        AuthorDTO authorDTO = authorServiceImpl.listAuthors(null, null, 1, 50).getContent().get(0);

        Map<String, Object> authorMap = new HashMap<>();
        authorMap.put("fullName", "New Author Name");

        mockMvc.perform(patch(AuthorController.AUTHOR_PATH_ID, authorDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorMap)))
                .andExpect(status().isNoContent());

        verify(authorService).patchAuthorById(uuidArgumentCaptor.capture(), authorArgumentCaptor.capture());

        assertThat(authorDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(authorMap.get("fullName")).isEqualTo(authorArgumentCaptor.getValue().getFullName());
    }

    @Test
    void testDeleteAuthor() throws Exception {
        AuthorDTO authorDTO = authorServiceImpl.listAuthors(null, null, 1, 50).getContent().get(0);

        given(authorService.deleteAuthorById(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(AuthorController.AUTHOR_PATH_ID, authorDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(authorService).deleteAuthorById(uuidArgumentCaptor.capture());

        assertThat(authorDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testDeleteAuthorNotFound() throws Exception {
        given(authorService.deleteAuthorById(any(UUID.class))).willReturn(false);

        mockMvc.perform(delete(AuthorController.AUTHOR_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAuthor() throws Exception {
        AuthorDTO authorDTO = authorServiceImpl.listAuthors(null, null, 1, 50).getContent().get(0);

        given(authorService.updateAuthorById(any(), any())).willReturn(Optional.of(authorDTO));

        mockMvc.perform(put(AuthorController.AUTHOR_PATH_ID, authorDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isNoContent());

        verify(authorService).updateAuthorById(any(UUID.class), any(AuthorDTO.class));
    }

    @Test
    void testUpdateAuthorNotFound() throws Exception {
        AuthorDTO authorDTO = authorServiceImpl.listAuthors(null, null, 1, 50).getContent().get(0);

        given(authorService.updateAuthorById(any(), any())).willReturn(Optional.empty());

        mockMvc.perform(put(AuthorController.AUTHOR_PATH_ID, authorDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateNewAuthor() throws Exception {
        AuthorDTO authorDTO = authorServiceImpl.listAuthors(null, null, 1, 50).getContent().get(0);

        given(authorService.saveNewAuthor(any(AuthorDTO.class))).willReturn(authorServiceImpl.listAuthors(null, null, 1, 50).getContent().get(1));

        mockMvc.perform(post(AuthorController.AUTHOR_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListAuthors() throws Exception {

        given(authorService.listAuthors(any(),any(), any(), any())).willReturn(authorServiceImpl.listAuthors(null, null, 1, 50));

        mockMvc.perform(get(AuthorController.AUTHOR_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    void getAuthorByIdNotFound() throws Exception {

        given(authorService.getAuthorById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(AuthorController.AUTHOR_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAuthorById() throws Exception {
        AuthorDTO author = authorServiceImpl.listAuthors(null, null, 1, 50).getContent().get(0);

        given(authorService.getAuthorById(author.getId())).willReturn(Optional.of(author));

        mockMvc.perform(get(AuthorController.AUTHOR_PATH_ID, author.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(author.getId().toString())))
                .andExpect(jsonPath("$.fullName", is(author.getFullName())));
    }
}
