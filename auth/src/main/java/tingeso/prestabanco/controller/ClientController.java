package tingeso.prestabanco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.prestabanco.dto.LoginRequest;
import tingeso.prestabanco.dto.LoginResponse;
import tingeso.prestabanco.dto.RegisterResponse;
import tingeso.prestabanco.model.ClientModel;

import tingeso.prestabanco.service.ClientService;
import tingeso.prestabanco.util.JwtUtil;

import java.util.List;
import java.util.Optional;

@RestController()
@CrossOrigin("*")
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;


    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<LoginResponse> res = clientService.login(loginRequest);
        if (res.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res.get());
    }

    @GetMapping("/me")
    public ResponseEntity<ClientModel> getMe(@RequestHeader("Authorization") String authorization) {
        Optional<ClientModel> client = jwtUtil.validateClient(authorization);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(client.get());
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody ClientModel client) {
        System.out.println("hola endpoint");
        Optional<RegisterResponse> res = clientService.register(client);
        if (res.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res.get());
    }
}



