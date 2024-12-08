package tingeso.prestabanco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tingeso.prestabanco.dto.MortgageSimulationRequest;
import tingeso.prestabanco.dto.SimulationResponse;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.UserModel;
import tingeso.prestabanco.service.MortgageLoanSimulationService;
import tingeso.prestabanco.util.JwtUtil;
import tingeso.prestabanco.util.TokenExtractor;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/simulator")
public class MortgageSimulationController {
    @Autowired
    MortgageLoanSimulationService mortgageLoanSimulationService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<SimulationResponse> simulate(@RequestBody MortgageSimulationRequest req, @RequestHeader("Authorization") String authorization) {
        Optional<ClientModel> client = jwtUtil.validateClient(authorization);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mortgageLoanSimulationService.simulate(req, client.get()));
    }
}
