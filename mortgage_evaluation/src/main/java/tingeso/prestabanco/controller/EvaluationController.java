package tingeso.prestabanco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tingeso.prestabanco.dto.*;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.ExecutiveModel;
import tingeso.prestabanco.model.MortgageLoanModel;
import tingeso.prestabanco.model.UserModel;
import tingeso.prestabanco.service.MortgageEvaluationService;
import tingeso.prestabanco.util.JwtUtil;
import tingeso.prestabanco.util.TokenExtractor;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/mortgage_loan")
public class EvaluationController {
    @Autowired
    private MortgageEvaluationService mortgageService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/{id}/set_pending_documentation")
    public ResponseEntity<SimpleResponse> setPendingDocumentation(@PathVariable Long id, @RequestBody PendingDocumentationRequest req, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageService.setPendingDocumentation(id, executive.get(), TokenExtractor.extractToken(authorization)));
    }

    @PostMapping("/{id}/set_in_evaluation")
    public ResponseEntity<SimpleResponse> setInEvaluation(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageService.setInEvaluation(id, executive.get(), TokenExtractor.extractToken(authorization)));
    }

    @PostMapping("/{id}/evaluate_mortgage")
    public ResponseEntity<SimpleResponse> evaluateMortgage(@PathVariable Long id, @RequestBody CreditEvaluation credit_evaluation, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageService.evaluateMortgage(id, credit_evaluation, executive.get(), TokenExtractor.extractToken(authorization)));
    }

    @PostMapping("/{id}/set_approved")
    public ResponseEntity<SimpleResponse> setApproved(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageService.setApproved(id, executive.get(), TokenExtractor.extractToken(authorization)));
    }


    @PostMapping("/{id}/set_outgo")
    public ResponseEntity<SimpleResponse> setOutgo(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageService.setInOutgo(id, executive.get(), TokenExtractor.extractToken(authorization)));
    }



}