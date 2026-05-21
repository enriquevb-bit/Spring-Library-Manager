package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.config.SpringSecConfig;
import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.LoanState;
import enriquevb.biblioteca.services.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
@Import(SpringSecConfig.class)
@ExtendWith(MockitoExtension.class)
class LoanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    LoanService loanService;

    @Test
    void activateLoanByIdReturns204() throws Exception {
        UUID loanId = UUID.randomUUID();
        given(loanService.activateLoan(loanId)).willReturn(LoanDTO.builder().id(loanId).loanState(LoanState.ACTIVE).build());

        mockMvc.perform(patch(LoanController.LOAN_PATH_ID + "/activate", loanId)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(loanService).activateLoan(loanId);
    }

    @Test
    void activateLoanWhenNotReservedReturns400() throws Exception {
        UUID loanId = UUID.randomUUID();
        willThrow(new LoanNotReservedException()).given(loanService).activateLoan(loanId);

        mockMvc.perform(patch(LoanController.LOAN_PATH_ID + "/activate", loanId)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void activateLoanUnknownLoanReturns404() throws Exception {
        UUID loanId = UUID.randomUUID();
        willThrow(new NotFoundException()).given(loanService).activateLoan(loanId);

        mockMvc.perform(patch(LoanController.LOAN_PATH_ID + "/activate", loanId)
                        .with(BookControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void activateLoanAsAnonymousIsRejected() throws Exception {
        mockMvc.perform(patch(LoanController.LOAN_PATH_ID + "/activate", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
