package tingeso.prestabanco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso.prestabanco.dto.MortgageStatusRequest;
import tingeso.prestabanco.dto.SimpleResponse;
import tingeso.prestabanco.model.LoanStatusModel;

@Service
public class TrackingService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String base_url = "http://tracking";

    public SimpleResponse createMortgageStatus(MortgageStatusRequest req, String token) {
        String url = base_url + "/tracking/create_mortgage_status";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<MortgageStatusRequest> entity = new HttpEntity<>(req, headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                SimpleResponse.class
        );

        return response.getBody();
    }

    public LoanStatusModel getMortgageStatus(Long mortgage_id, String token) {
        String url = base_url + "/tracking/" + mortgage_id + "/mortgage_status";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<LoanStatusModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                LoanStatusModel.class
        );
        return response.getBody();
    }


    public SimpleResponse setMissingDocuments(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_missing_documents";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse updateDocuments(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/update_documents";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse setInEvaluation(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_in_evaluation";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse setPreApproved(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_pre_approved";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse setFinalApproval(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_final_approval";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse setApproved(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_approved";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse setRejected(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_rejected";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse setCancelledByClient(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_cancelled_by_client";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }

    public SimpleResponse setInOutgo(Long id, String token) {
        String url = base_url + "/tracking/" + id + "/set_in_outgo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SimpleResponse.class
        );
        return response.getBody();
    }
}
