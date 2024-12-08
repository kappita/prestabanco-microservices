package tingeso.prestabanco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.ExecutiveModel;
import tingeso.prestabanco.model.UserModel;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    public ClientModel getClient(String token) {
        String url = "http://auth/clients/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the GET request with headers
        ResponseEntity<ClientModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ClientModel.class
        );
        return response.getBody();
    }

    public ExecutiveModel getExecutive(String token) {
        String url = "http://auth/executives/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ExecutiveModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ExecutiveModel.class
        );
        return response.getBody();
    }

    public UserModel getUser(String token) {
        String url = "http://auth/users/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserModel.class
        );
        return response.getBody();
    }
}
