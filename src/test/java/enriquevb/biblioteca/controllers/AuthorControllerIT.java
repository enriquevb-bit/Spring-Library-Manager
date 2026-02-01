package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.entities.Author;
import enriquevb.biblioteca.mappers.AuthorMapper;
import enriquevb.biblioteca.models.AuthorDTO;
import enriquevb.biblioteca.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
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
class AuthorControllerIT {

    @Autowired
    AuthorController authorController;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testListAuthorsDefaultPagination() throws Exception {
        mockMvc.perform(get(AuthorController.AUTHOR_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").exists());
    }

    @Test
    void testListAuthorsWithPagination() throws Exception {
        mockMvc.perform(get(AuthorController.AUTHOR_PATH)
                        .queryParam("fullName", "John Ronald Reuel Tolkien")
                        .queryParam("nationality", "British")
                        .queryParam("pageNumber", "1")
                        .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListAuthorsByNameAndNationality() throws Exception {
        mockMvc.perform(get(AuthorController.AUTHOR_PATH)
                        .queryParam("fullName", "John Ronald Reuel Tolkien")
                        .queryParam("nationality", "British"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListAuthorsByNationality() throws Exception {
        mockMvc.perform(get(AuthorController.AUTHOR_PATH)
                        .queryParam("nationality", "British"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListAuthorsByName() throws Exception {
        mockMvc.perform(get(AuthorController.AUTHOR_PATH)
                        .queryParam("fullName", "John Ronald Reuel Tolkien"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testPatchAuthorBadName() throws Exception {
        Author author = authorRepository.findAll().get(0);

        Map<String, Object> authorMap = new HashMap<>();
        authorMap.put("fullName", "New name 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        mockMvc.perform(patch(AuthorController.AUTHOR_PATH_ID, author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorMap)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> authorController.deleteAuthorById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteByIdFound() {
        Author author = authorRepository.findAll().get(0);

        ResponseEntity responseEntity = authorController.deleteAuthorById(author.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(authorRepository.findById(author.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> authorController.updateAuthorById(UUID.randomUUID(), AuthorDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateExistingAuthor() {
        AuthorDTO authorDTO = authorMapper.authorToAuthorDto(authorRepository.findAll().get(0));
        authorDTO.setFullName("New name");
        authorRepository.save(authorMapper.authorDtoToAuthor(authorDTO));

        ResponseEntity responseEntity = authorController.updateAuthorById(authorDTO.getId(), authorDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(authorRepository.findById(authorDTO.getId()).get().getFullName()).isEqualTo("New name");
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewAuthor() {
        AuthorDTO authorDTO = AuthorDTO.builder()
                .fullName("Name")
                .build();

        ResponseEntity responseEntity = authorController.handlePost(authorDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] idLocation = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedId = UUID.fromString(idLocation[4]);

        Author author = authorRepository.findById(savedId).get();

        assertThat(author).isNotNull();
    }

    @Test
    void testAuthorIdNotFound() {
        assertThrows(NotFoundException.class, () -> authorController.getAuthorById(UUID.randomUUID()));
    }

    @Test
    void testGetAuthorById() {
        AuthorDTO authorDTO = authorController.listAuthors(null, null, 1, 50).getContent().get(0);

        assertThat(authorController.getAuthorById(authorDTO.getId())).isNotNull();
    }

    @Test
    void testListAuthors() {
        Page<AuthorDTO> authors = authorController.listAuthors(null,null,1,50);

        assertThat(authors.getContent().size()).isGreaterThan(0);

    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        authorRepository.deleteAll();
        Page<AuthorDTO> emptyList = authorController.listAuthors(null, null, 1, 50);

        assertThat(emptyList.getContent().size()).isEqualTo(0);
    }
}