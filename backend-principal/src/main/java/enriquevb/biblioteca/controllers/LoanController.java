package enriquevb.biblioteca.controllers;

import enriquevb.biblioteca.models.LoanDTO;
import enriquevb.biblioteca.models.LoanState;
import enriquevb.biblioteca.models.RequestedLoanItems;
import enriquevb.biblioteca.services.LoanService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class LoanController {

    public static final String LOAN_PATH = "/api/v1/loan";
    public static final String LOAN_PATH_ID = LOAN_PATH + "/{loanId}";

    private final LoanService loanService;

    @PatchMapping(LOAN_PATH_ID)
    public ResponseEntity patchLoanById(@PathVariable("loanId") UUID loanId,
                                        @RequestBody LoanDTO loan) {

        loanService.patchLoanById(loanId, loan);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(LOAN_PATH_ID + "/return")
    public ResponseEntity returnLoan(@PathVariable("loanId") UUID loanId) {

        loanService.returnLoan(loanId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(LOAN_PATH_ID)
    public ResponseEntity deleteLoanById(@PathVariable("loanId") UUID loanId) {

        if (!loanService.deleteLoanById(loanId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(LOAN_PATH_ID)
    public ResponseEntity updateLoanById(@PathVariable("loanId") UUID loanId,
                                         @RequestBody LoanDTO loan) {

        if (loanService.updateLoanById(loanId, loan).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(LOAN_PATH)
    public ResponseEntity handlePost(@RequestBody LoanDTO loan) {
        LoanDTO savedLoan = loanService.saveNewLoan(loan);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", LOAN_PATH + "/" + savedLoan.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PostMapping(MemberController.MEMBER_PATH_ID + "/loan")
    public ResponseEntity createNewLoan(@PathVariable("memberId") UUID memberId,
                                        @RequestBody List<RequestedLoanItems<UUID, Integer>> items){

        LoanDTO loanDTO = loanService.createLoan(memberId, items);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", LOAN_PATH + "/" + loanDTO.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(LOAN_PATH)
    public Page<LoanDTO> listLoans(@RequestParam(required = false) LoanState loanState,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize) {
        return loanService.listLoans(loanState, pageNumber, pageSize);
    }

    @GetMapping(value = LOAN_PATH_ID)
    public LoanDTO getLoanById(@PathVariable("loanId") UUID loanId) {
        return loanService.getLoanById(loanId).orElseThrow(NotFoundException::new);
    }
}