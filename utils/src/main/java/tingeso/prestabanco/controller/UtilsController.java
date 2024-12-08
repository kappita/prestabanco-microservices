package tingeso.prestabanco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.prestabanco.model.*;
import tingeso.prestabanco.repository.DocumentTypeRepository;
import tingeso.prestabanco.repository.LoanStatusRepository;
import tingeso.prestabanco.repository.LoanTypeRepository;
import tingeso.prestabanco.repository.RoleRepository;
import tingeso.prestabanco.util.JwtUtil;
import tingeso.prestabanco.util.Nationalities;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/utils")
public class UtilsController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;


    @GetMapping("/loan_types")
    public ResponseEntity<List<LoanTypeModel>> getLoanTypes(@RequestHeader("Authorization") String authorization) {
        Boolean tokenValid = jwtUtil.validateToken(authorization);
        if (!tokenValid) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(loanTypeRepository.findAll());
    }

    @GetMapping("/loan_types/{id}")
    public ResponseEntity<LoanTypeModel> getLoanTypeById(@RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        Boolean tokenValid = jwtUtil.validateToken(authorization);
        if (!tokenValid) {
            return ResponseEntity.badRequest().build();
        }

        Optional<LoanTypeModel> loanTypes = loanTypeRepository.findById(id);
        if (loanTypes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanTypes.get());

    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleModel>> getRoles(@RequestHeader("Authorization") String authorization) {
        Boolean tokenValid = jwtUtil.validateToken(authorization);
        if (!tokenValid) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleModel> getRole(@RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        Boolean tokenValid = jwtUtil.validateToken(authorization);
        if (!tokenValid) {
            return ResponseEntity.badRequest().build();
        }
        Optional<RoleModel> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role.get());
    }

    @GetMapping("/document_types")
    public ResponseEntity<List<DocumentTypeModel>> getDocumentTypes(@RequestHeader("Authorization") String authorization) {
        Boolean tokenValid = jwtUtil.validateToken(authorization);
        if (!tokenValid) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(documentTypeRepository.findAll());
    }

    @GetMapping("/loan_status/{id}")
    public ResponseEntity<LoanStatusModel> getLoanStatus(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
        Boolean tokenValid = jwtUtil.validateToken(authorization);
        if (!tokenValid) {
            return ResponseEntity.badRequest().build();
        }
        Optional<LoanStatusModel> loanStatusModel = loanStatusRepository.findById(id);
        if (loanStatusModel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loanStatusModel.get());
    }

    @GetMapping("/nationalities")
    public ResponseEntity<List<String>> getNationalities(@RequestHeader("Authorization") String authorization) {
        Boolean tokenValid = jwtUtil.validateToken(authorization);
        if (!tokenValid) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Nationalities.NATIONALITIES);
    }
}
