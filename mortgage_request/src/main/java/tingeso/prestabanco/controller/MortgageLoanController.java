package tingeso.prestabanco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tingeso.prestabanco.dto.*;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.ExecutiveModel;
import tingeso.prestabanco.model.MortgageLoanModel;
import tingeso.prestabanco.model.UserModel;
import tingeso.prestabanco.service.MortgageLoanService;
import tingeso.prestabanco.util.JwtUtil;
import tingeso.prestabanco.util.TokenExtractor;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/mortgage_loans")
public class MortgageLoanController {
    @Autowired
    private MortgageLoanService mortgageService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("")
    public ResponseEntity<List<MortgageLoanModel>> getAllClientMortgageLoans(@RequestHeader("Authorization") String authorization) {
         Optional<ClientModel> client = jwtUtil.validateClient(authorization);
         if (client.isEmpty()) {
             return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(mortgageService.getAllClientMortgageLoans(client.get()));
    }

    @PostMapping("")
    public ResponseEntity<MortgageLoanModel> postMortgageLoan(@RequestBody MortgageLoanRequest req, @RequestHeader("Authorization") String authorization) {
        Optional<ClientModel> client = jwtUtil.validateClient(authorization);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageService.receiveMortgage(req, TokenExtractor.extractToken(authorization)));
    }


    @GetMapping("/{id}")
    public ResponseEntity<? extends MortgageLoanModel> getMortgageLoan(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<UserModel> user = jwtUtil.validateUser(authorization);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(mortgageService.getMortgageLoan(id, user.get()));
    }
    @PostMapping("/{id}/add_documents")
    public ResponseEntity<SimpleResponse> addDocuments(@PathVariable Long id, @RequestParam("files") MultipartFile[] files, @RequestHeader("Authorization") String authorization) {
        Optional<ClientModel> client = jwtUtil.validateClient(authorization);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String token = TokenExtractor.extractToken(authorization);
        return ResponseEntity.ok(mortgageService.addDocuments(id, files, token));
    }

    @PostMapping("/{id}/pre_approve")
    public ResponseEntity<SimpleResponse> preApprove(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageService.preApproveMortgage(id, executive.get()));
    }
}
