package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.models.MemberDTO;
import enriquevb.biblioteca.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class MemberController {

    public static final String MEMBER_PATH = "/api/v1/member";
    public static final String MEMBER_PATH_ID = MEMBER_PATH + "/{memberId}";

    private final MemberService memberService;

    @PatchMapping(MEMBER_PATH_ID)
    public ResponseEntity patchMemberById(@PathVariable("memberId") UUID memberId,
                                          @RequestBody MemberDTO member) {

        memberService.patchMemberById(memberId, member);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(MEMBER_PATH_ID)
    public ResponseEntity deleteMemberById(@PathVariable("memberId") UUID memberId) {

        if (!memberService.deleteMemberById(memberId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(MEMBER_PATH_ID)
    public ResponseEntity updateMemberById(@PathVariable("memberId") UUID memberId,
                                           @RequestBody MemberDTO member) {

        if (memberService.updateMemberById(memberId, member).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(MEMBER_PATH)
    public ResponseEntity handlePost(@RequestBody MemberDTO member) {
        MemberDTO savedMember = memberService.saveNewMember(member);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", MEMBER_PATH + "/" + savedMember.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(MEMBER_PATH)
    public Page<MemberDTO> listMembers(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(required = false) Integer pageNumber,
                                          @RequestParam(required = false) Integer pageSize) {
        return memberService.listMembers(name, email, pageNumber, pageSize);
    }

    @GetMapping(value = MEMBER_PATH_ID)
    public MemberDTO getMemberById(@PathVariable("memberId") UUID id) {
        return memberService.getMemberById(id).orElseThrow(NotFoundException::new);
    }
}
