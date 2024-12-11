package tingeso.prestabanco.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.ExecutiveModel;
import tingeso.prestabanco.model.UserModel;
import tingeso.prestabanco.repository.ClientRepository;
import tingeso.prestabanco.repository.ExecutiveRepository;
import tingeso.prestabanco.repository.UserRepository;
import tingeso.prestabanco.service.AuthService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Autowired
    SecretKey secretKey;

    @Autowired
    AuthService authService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Boolean validateToken(String token) {
        try{
            Jws<Claims> jws = Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return !jws.getPayload().getExpiration().before(new Date());
        }catch(ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch(MalformedJwtException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Optional<ClientModel> validateClient(String header) {
        String[] parts = header.split(" ");
        if (parts.length != 2) {
            return Optional.empty();
        }

        if (!validateToken(parts[1])) {
            return Optional.empty();
        }
        ClientModel client = authService.getClient(parts[1]);

        return Optional.of(client);
    }


    public Optional<ExecutiveModel> validateExecutive(String header) {
        String[] parts = header.split(" ");
        if (parts.length != 2) {
            return Optional.empty();
        }
        if (!validateToken(parts[1])) {
            return Optional.empty();
        }
        ExecutiveModel executive = authService.getExecutive(parts[1]);
        return Optional.of(executive);
    }

    public Optional<UserModel> validateUser(String header) {
        String[] parts = header.split(" ");
        if (parts.length != 2) {
            return Optional.empty();
        }
        if (!validateToken(parts[1])) {
            return Optional.empty();
        }

        UserModel user = authService.getUser(parts[1]);
        return Optional.of(user);
    }



}
