package tingeso.prestabanco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.UserModel;
import tingeso.prestabanco.util.JwtUtil;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/me")
    public ResponseEntity<UserModel> getMe(@RequestHeader("Authorization") String authorization) {
        Optional<UserModel> user = jwtUtil.validateUser(authorization);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user.get());
    }
}
