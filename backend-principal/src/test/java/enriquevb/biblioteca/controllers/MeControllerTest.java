package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.config.SpringSecConfig;
import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.mappers.MemberMapper;
import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.LoanState;
import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.models.MemberState;
import enriquevb.biblioteca.models.RequestedLoanItems;
import enriquevb.biblioteca.repositories.MemberRepository;
import enriquevb.biblioteca.services.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeController.class)
@Import(SpringSecConfig.class)
@ExtendWith(MockitoExtension.class)
class MeControllerTest {

    private static final String MEMBER_EMAIL = "member@example.com";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MemberRepository memberRepository;

    @MockitoBean
    MemberMapper memberMapper;

    @MockitoBean
    LoanService loanService;

    static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor memberJwt =
            jwt()
                    .jwt(jwt -> jwt.claims(claims -> {
                                claims.put("scope", "openid");
                                claims.put("email", MEMBER_EMAIL);
                                claims.put("authorities", List.of("ROLE_MEMBER"));
                            })
                            .subject("oidc-client")
                            .notBefore(Instant.now().minusSeconds(5)))
                    .authorities(new SimpleGrantedAuthority("ROLE_MEMBER"));

    private Member member(UUID id) {
        return Member.builder()
                .id(id)
                .name("Test Member")
                .email(MEMBER_EMAIL)
                .memberState(MemberState.ACTIVE)
                .build();
    }

    @Test
    void getCurrentMemberLoanByIdOk() throws Exception {
        UUID memberId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        LoanDTO loan = LoanDTO.builder()
                .id(loanId)
                .member(MemberDTO.builder().id(memberId).name("Test Member").email(MEMBER_EMAIL).memberState(MemberState.ACTIVE).build())
                .build();

        given(memberRepository.findFirstByEmail(MEMBER_EMAIL)).willReturn(Optional.of(member(memberId)));
        given(loanService.getLoanById(loanId)).willReturn(Optional.of(loan));

        mockMvc.perform(get("/api/v1/me/loan/{loanId}", loanId)
                        .with(memberJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(loanId.toString())))
                .andExpect(jsonPath("$.member.id", is(memberId.toString())));
    }

    @Test
    void getCurrentMemberLoanByIdForeignLoanReturns404() throws Exception {
        UUID memberId = UUID.randomUUID();
        UUID otherMemberId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        LoanDTO foreignLoan = LoanDTO.builder()
                .id(loanId)
                .member(MemberDTO.builder().id(otherMemberId).name("Other").email("other@example.com").memberState(MemberState.ACTIVE).build())
                .build();

        given(memberRepository.findFirstByEmail(MEMBER_EMAIL)).willReturn(Optional.of(member(memberId)));
        given(loanService.getLoanById(loanId)).willReturn(Optional.of(foreignLoan));

        mockMvc.perform(get("/api/v1/me/loan/{loanId}", loanId)
                        .with(memberJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentMemberLoanByIdUnknownLoanReturns404() throws Exception {
        UUID memberId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        given(memberRepository.findFirstByEmail(MEMBER_EMAIL)).willReturn(Optional.of(member(memberId)));
        given(loanService.getLoanById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/me/loan/{loanId}", loanId)
                        .with(memberJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentMemberLoanByIdAnonymousReturns401() throws Exception {
        mockMvc.perform(get("/api/v1/me/loan/{loanId}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCurrentMemberLoanByIdAdminReturns403() throws Exception {
        mockMvc.perform(get("/api/v1/me/loan/{loanId}", UUID.randomUUID())
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void reserveLoanCallsReserveServiceNotCreate() throws Exception {
        UUID memberId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        LoanDTO reserved = LoanDTO.builder()
                .id(loanId)
                .loanState(LoanState.RESERVED)
                .build();

        given(memberRepository.findFirstByEmail(MEMBER_EMAIL)).willReturn(Optional.of(member(memberId)));
        given(loanService.reserveLoan(eq(memberId), anyList())).willReturn(reserved);

        List<RequestedLoanItems<UUID, Integer>> items = List.of(new RequestedLoanItems<>(bookId, 1));

        mockMvc.perform(post("/api/v1/me/loan")
                        .with(memberJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(items)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/loan/" + loanId));

        verify(loanService).reserveLoan(eq(memberId), anyList());
        verify(loanService, never()).createLoan(any(UUID.class), anyList());
    }
}
