package tingeso.prestabanco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso.prestabanco.dto.MortgageStatusRequest;
import tingeso.prestabanco.dto.SimpleResponse;
import tingeso.prestabanco.model.MortgageLoanModel;
import tingeso.prestabanco.model.RoleModel;

import java.util.List;

@Service
public class MortgageService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String base_url = "http://mortgage";

    public List<MortgageLoanModel> getAllClientMortgageLoans(String token) {
        String url = base_url + "/mortgage_loans";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<MortgageLoanModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<MortgageLoanModel>>() {}
        );

        return response.getBody();
    }

    public MortgageLoanModel getMortgageLoan(Long mortgage_id, String token) {
        String url = base_url + "/mortgage_loans/" + mortgage_id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MortgageLoanModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                MortgageLoanModel.class
        );
        return response.getBody();
    }

    public SimpleResponse preApproveMortgageLoan(Long mortgage_id, String token) {
        String url = base_url + "/mortgage_loans/" + mortgage_id + "/pre_approve";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }



}
