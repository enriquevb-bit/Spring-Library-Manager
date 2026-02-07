package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.mappers.MemberMapper;
import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.repositories.MemberRepository;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MemberControllerIT {

    @Autowired
    MemberController memberController;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberMapper memberMapper;

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
    void testListMembersDefaultPagination() throws Exception {
        mockMvc.perform(get(MemberController.MEMBER_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").exists());
    }

    @Test
    void testListMembersWithPagination() throws Exception {
        mockMvc.perform(get(MemberController.MEMBER_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("name", "María García")
                        .queryParam("email", "maria.garcia@test.com")
                        .queryParam("pageNumber", "1")
                        .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListMembersByNameAndEmail() throws Exception {
        mockMvc.perform(get(MemberController.MEMBER_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("name", "María García")
                        .queryParam("email", "maria.garcia@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListMembersByEmail() throws Exception {
        mockMvc.perform(get(MemberController.MEMBER_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("email", "maria.garcia@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testListMembersByName() throws Exception {
        mockMvc.perform(get(MemberController.MEMBER_PATH)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .queryParam("name", "María García"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void testPatchMember() throws Exception {
        Member member = memberRepository.findAll().get(0);

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("name", "New name sadggggggggggaaaaaaaaaaaaaaaaaaaaaaaaaaaaaafd");

        mockMvc.perform(patch(MemberController.MEMBER_PATH_ID, member.getId())
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberMap)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> memberController.deleteMemberById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteByIdFound() {
        Member member = memberRepository.findAll().get(0);

        ResponseEntity responseEntity = memberController.deleteMemberById(member.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(memberRepository.findById(member.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> memberController.updateMemberById(UUID.randomUUID(), MemberDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateExistingMember() {
        MemberDTO memberDTO = memberMapper.memberToMemberDto(memberRepository.findAll().get(0));
        memberDTO.setName("New name");
        memberRepository.save(memberMapper.memberDtoToMember(memberDTO));

        ResponseEntity responseEntity = memberController.updateMemberById(memberDTO.getId(), memberDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(memberRepository.findById(memberDTO.getId()).get().getName()).isEqualTo("New name");
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewMember() {
        MemberDTO memberDTO = MemberDTO.builder()
                .name("Name")
                .build();

        ResponseEntity responseEntity = memberController.handlePost(memberDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] idLocation = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedId = UUID.fromString(idLocation[4]);

        Member member = memberRepository.findById(savedId).get();

        assertThat(member).isNotNull();
    }

    @Test
    void testMemberIdNotFound() {
        assertThrows(NotFoundException.class, () -> memberController.getMemberById(UUID.randomUUID()));
    }

    @Test
    void testGetMemberById() {
        MemberDTO memberDTO = memberController.listMembers(null, null, 1, 50).getContent().get(0);

        assertThat(memberController.getMemberById(memberDTO.getId())).isNotNull();
    }

    @Test
    void testListMembers() {
        Page<MemberDTO> members = memberController.listMembers(null,null,1,50);

        assertThat(members.getContent().size()).isGreaterThan(0);

    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        memberRepository.deleteAll();
        Page<MemberDTO> emptyList = memberController.listMembers(null, null, 1, 50);

        assertThat(emptyList.getContent().size()).isEqualTo(0);
    }
}