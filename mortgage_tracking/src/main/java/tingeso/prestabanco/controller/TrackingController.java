package tingeso.prestabanco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.prestabanco.dto.MortgageStatusRequest;
import tingeso.prestabanco.dto.SimpleResponse;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.ExecutiveModel;
import tingeso.prestabanco.service.TrackingService;
import tingeso.prestabanco.util.JwtUtil;
import tingeso.prestabanco.util.TokenExtractor;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/tracking")
public class TrackingController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TrackingService trackingService;

    @PostMapping("/create_mortgage_status")
    public ResponseEntity<SimpleResponse> createMortgage(@RequestBody MortgageStatusRequest req, @RequestHeader("Authorization") String authorization) {
        String token = TokenExtractor.extractToken(authorization);
        Boolean isValid = jwtUtil.validateToken(token);
        if (!isValid) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(trackingService.createMortgageStatus(req));
    }

    @PutMapping("/{id}/set_missing_documents")
    public ResponseEntity<SimpleResponse> setMissingDocuments(@RequestParam Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.setMissingDocuments(id, executive.get()));
    }

    @PutMapping("/{id}/update_documents")
    public ResponseEntity<SimpleResponse> updateDocuments(@RequestParam Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ClientModel> client = jwtUtil.validateClient(authorization);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.updateDocuments(id, client.get()));
    }

    @PutMapping("/{id}/set_in_evaluation")
    public ResponseEntity<SimpleResponse> setInEvaluation(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.setInEvaluation(id, executive.get()));
    }

    @PutMapping("/{id}/set_pre_approved")
    public ResponseEntity<SimpleResponse> setPreApproved(@RequestParam Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.setPreApproved(id, executive.get()));
    }

    @PutMapping("/{id}/set_final_approval")
    public ResponseEntity<SimpleResponse> setFinalApproval(@RequestParam Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ClientModel> client = jwtUtil.validateClient(authorization);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.setFinalApproval(id, client.get()));
    }

    @PutMapping("/{id}/set_approved")
    public ResponseEntity<SimpleResponse> setApproved(@RequestParam Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.setApproved(id, executive.get()));
    }

    @PutMapping("/{id}/set_rejected")
    public ResponseEntity<SimpleResponse> setRejected(@RequestParam Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(trackingService.setRejected(id, executive.get()));
    }

    @PutMapping("/{id}/set_cancelled_by_client")
    public ResponseEntity<SimpleResponse> setCancelledByClient(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ClientModel> client = jwtUtil.validateClient(authorization);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.setCancelledByClient(id, client.get()));
    }

    @PutMapping("/{id}/set_in_outgo")
    public ResponseEntity<SimpleResponse> setInOutgo(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        Optional<ExecutiveModel> executive = jwtUtil.validateExecutive(authorization);
        if (executive.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(trackingService.setInOutgo(id, executive.get()));
    }

}
