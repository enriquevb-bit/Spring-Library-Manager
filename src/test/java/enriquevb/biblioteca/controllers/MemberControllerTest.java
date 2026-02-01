package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.services.MemberService;
import enriquevb.biblioteca.services.MemberServiceImpl;
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

@WebMvcTest(MemberController.class)
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MemberService memberService;

    MemberServiceImpl memberServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<MemberDTO> memberArgumentCaptor;

    @BeforeEach
    void setUp() {
        memberServiceImpl = new MemberServiceImpl();
    }

    @Test
    void testPatchMember() throws Exception {
        MemberDTO memberDTO = memberServiceImpl.getAllMembers().get(0);

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("name", "New Member Name");

        mockMvc.perform(patch(MemberController.MEMBER_PATH_ID, memberDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberMap)))
                .andExpect(status().isNoContent());

        verify(memberService).patchMemberById(uuidArgumentCaptor.capture(), memberArgumentCaptor.capture());

        assertThat(memberDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(memberMap.get("name")).isEqualTo(memberArgumentCaptor.getValue().getName());
    }

    @Test
    void testDeleteMember() throws Exception {
        MemberDTO memberDTO = memberServiceImpl.getAllMembers().get(0);

        given(memberService.deleteMemberById(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(MemberController.MEMBER_PATH_ID, memberDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(memberService).deleteMemberById(uuidArgumentCaptor.capture());

        assertThat(memberDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testDeleteMemberNotFound() throws Exception {
        given(memberService.deleteMemberById(any(UUID.class))).willReturn(false);

        mockMvc.perform(delete(MemberController.MEMBER_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateMember() throws Exception {
        MemberDTO memberDTO = memberServiceImpl.getAllMembers().get(0);

        given(memberService.updateMemberById(any(), any())).willReturn(Optional.of(memberDTO));

        mockMvc.perform(put(MemberController.MEMBER_PATH_ID, memberDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isNoContent());

        verify(memberService).updateMemberById(any(UUID.class), any(MemberDTO.class));
    }

    @Test
    void testUpdateMemberNotFound() throws Exception {
        MemberDTO memberDTO = memberServiceImpl.getAllMembers().get(0);

        given(memberService.updateMemberById(any(), any())).willReturn(Optional.empty());

        mockMvc.perform(put(MemberController.MEMBER_PATH_ID, memberDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateNewMember() throws Exception {
        MemberDTO memberDTO = memberServiceImpl.getAllMembers().get(0);

        given(memberService.saveNewMember(any(MemberDTO.class))).willReturn(memberServiceImpl.getAllMembers().get(1));

        mockMvc.perform(post(MemberController.MEMBER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListAllMembers() throws Exception {

        given(memberService.getAllMembers()).willReturn(memberServiceImpl.getAllMembers());

        mockMvc.perform(get(MemberController.MEMBER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getMemberByIdNotFound() throws Exception {

        given(memberService.getMemberById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(MemberController.MEMBER_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMemberById() throws Exception {
        MemberDTO member = memberServiceImpl.getAllMembers().get(0);

        given(memberService.getMemberById(member.getId())).willReturn(Optional.of(member));

        mockMvc.perform(get(MemberController.MEMBER_PATH_ID, member.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(member.getId().toString())))
                .andExpect(jsonPath("$.name", is(member.getName())));
    }
}
