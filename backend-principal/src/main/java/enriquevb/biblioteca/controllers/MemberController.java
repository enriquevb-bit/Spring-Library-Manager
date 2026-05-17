package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.LoanState;
import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.services.LoanService;
import enriquevb.biblioteca.services.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class MemberController {

    public static final String MEMBER_PATH = "/api/v1/member";
    public static final String MEMBER_PATH_ID = MEMBER_PATH + "/{memberId}";

    private final MemberService memberService;
    private final LoanService loanService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(MEMBER_PATH_ID)
    public ResponseEntity patchMemberById(@PathVariable("memberId") UUID memberId,
                                          @RequestBody MemberDTO member) {

        memberService.patchMemberById(memberId, member);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(MEMBER_PATH_ID)
    public ResponseEntity deleteMemberById(@PathVariable("memberId") UUID memberId) {

        if (!memberService.deleteMemberById(memberId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(MEMBER_PATH_ID)
    public ResponseEntity updateMemberById(@PathVariable("memberId") UUID memberId,
                                           @RequestBody MemberDTO member) {

        if (memberService.updateMemberById(memberId, member).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(MEMBER_PATH)
    public ResponseEntity handlePost(@RequestBody MemberDTO member) {
        MemberDTO savedMember = memberService.saveNewMember(member);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", MEMBER_PATH + "/" + savedMember.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(MEMBER_PATH)
    public Page<MemberDTO> listMembers(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(required = false) @Parameter(description = "Page number, starting at 1") Integer pageNumber,
                                          @RequestParam(required = false) Integer pageSize) {
        return memberService.listMembers(name, email, pageNumber, pageSize);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = MEMBER_PATH_ID)
    public MemberDTO getMemberById(@PathVariable("memberId") UUID id) {
        return memberService.getMemberById(id).orElseThrow(NotFoundException::new);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(MEMBER_PATH_ID + "/loan")
    public Page<LoanDTO> listMemberLoans(@PathVariable("memberId") UUID memberId,
                                         @RequestParam(required = false) LoanState loanState,
                                         @RequestParam(required = false) @Parameter(description = "Page number, starting at 1") Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize) {
        return loanService.listLoansByMember(memberId, loanState, pageNumber, pageSize);
    }
}
