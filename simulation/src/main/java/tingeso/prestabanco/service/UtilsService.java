package tingeso.prestabanco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso.prestabanco.model.DocumentTypeModel;
import tingeso.prestabanco.model.LoanStatusModel;
import tingeso.prestabanco.model.LoanTypeModel;
import tingeso.prestabanco.model.RoleModel;

import java.util.List;

@Service
public class UtilsService {


    @Autowired
    private RestTemplate restTemplate;



    public List<LoanTypeModel> getLoanTypes(String token) {
        String url = "http://utils/loan_types";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<LoanTypeModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<LoanTypeModel>>() {}
        );

        return response.getBody();
    }

    public LoanTypeModel getLoanType(String token, Long loanTypeId) {
        String url = "http://utils/loan_type/" + loanTypeId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<LoanTypeModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                LoanTypeModel.class
        );
        return response.getBody();
    }



    public List<RoleModel> getRoles(String token) {
        String url = "http://utils/roles";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<RoleModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<RoleModel>>() {}
        );
        return response.getBody();
    }

    public RoleModel getRole(String token, Long role_id) {
        String url = "http://utils/roles/" + role_id.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<RoleModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                RoleModel.class
        );
        return response.getBody();
    }


    public List<DocumentTypeModel> getDocumentTypes(String token) {
        String url = "http://utils/document_type/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<DocumentTypeModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<DocumentTypeModel>>() {}
        );
        return response.getBody();
    }

    public LoanStatusModel getLoanStatus(String token, String loanStatusId) {
        String url = "http://utils/loan_status" + loanStatusId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<LoanStatusModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                LoanStatusModel.class
        );
        return response.getBody();
    }

    public List<String> getNationalities(String token) {
        String url = "http://utils/nationalities";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<String>>() {}
        );

        return response.getBody();
    }



}
