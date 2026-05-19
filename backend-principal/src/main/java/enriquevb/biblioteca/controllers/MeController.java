package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.entities.Member;
import enriquevb.biblioteca.mappers.MemberMapper;
import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.LoanState;
import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.models.RequestedLoanItems;
import enriquevb.biblioteca.repositories.MemberRepository;
import enriquevb.biblioteca.services.LoanService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
public class MeController {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final LoanService loanService;

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public MemberDTO getCurrentMember(@AuthenticationPrincipal Jwt jwt) {
        Member member = findMemberFromJwt(jwt);
        return memberMapper.memberToMemberDto(member);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/loan")
    public Page<LoanDTO> getCurrentMemberLoans(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) LoanState loanState,
            @RequestParam(required = false) @Parameter(description = "Page number, starting at 1") Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        Member member = findMemberFromJwt(jwt);
        return loanService.listLoansByMember(member.getId(), loanState, pageNumber, pageSize);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/loan")
    public ResponseEntity reserveLoan(@AuthenticationPrincipal Jwt jwt,
                                      @RequestBody List<RequestedLoanItems<UUID, Integer>> items) {
        Member member = findMemberFromJwt(jwt);
        LoanDTO loanDTO = loanService.createLoan(member.getId(), items);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", LoanController.LOAN_PATH + "/" + loanDTO.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    private Member findMemberFromJwt(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        return memberRepository.findFirstByEmail(email)
                .orElseThrow(NotFoundException::new);
    }
}
