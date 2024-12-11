package tingeso.prestabanco.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tingeso.prestabanco.dto.MortgageStatusRequest;
import tingeso.prestabanco.dto.SimpleResponse;
import tingeso.prestabanco.model.ClientModel;
import tingeso.prestabanco.model.ExecutiveModel;
import tingeso.prestabanco.model.LoanStatusModel;
import tingeso.prestabanco.model.MortgageLoanStatusModel;
import tingeso.prestabanco.repository.LoanStatusRepository;
import tingeso.prestabanco.repository.MortgageLoanStatusRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class TrackingService {
    @Autowired
    MortgageLoanStatusRepository mortgageStatusRepository;

    @Autowired
    LoanStatusRepository loanStatusRepository;

    public SimpleResponse createMortgageStatus(MortgageStatusRequest req) {
        MortgageLoanStatusModel mortgage = new MortgageLoanStatusModel();
        mortgage.setMortgage_id(req.getMortgage_id());
        mortgage.setClientId(req.getClient_id());
        LoanStatusModel status = getLoanStatus("E1");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully created mortgage status");
    }

    public SimpleResponse setMissingDocuments(Long mortgage_id, ExecutiveModel executive) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        LoanStatusModel status = getLoanStatus("E2");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse updateDocuments(Long mortgage_id, ClientModel client) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        verifyOwnership(mortgage, client);
        Boolean is_on_E1 = mortgage.getLoan_status().getId().equals("E1");
        Boolean is_on_E2 = mortgage.getLoan_status().getId().equals("E2");

        if (!(is_on_E1 || is_on_E2)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can not add documents to a request in evaluation of further steps");
        }

        LoanStatusModel status = getLoanStatus("E1");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse setInEvaluation(Long mortgage_id, ExecutiveModel executive) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        if (!mortgage.getLoan_status().getId().equals("E1")) {
            throw new RuntimeException("Mortgage can not be put in evaluation yet");
        }
        LoanStatusModel status = getLoanStatus("E3");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse setPreApproved(Long mortgage_id, ExecutiveModel executive) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        if (!mortgage.getLoan_status().getId().equals("E3")) {
            throw new RuntimeException("Mortgage can not be put in approved yet");
        }
        LoanStatusModel status = getLoanStatus("E4");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse setFinalApproval(Long mortgage_id, ClientModel client) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        verifyOwnership(mortgage, client);
        if (!mortgage.getLoan_status().getId().equals("E4")) {
            throw new RuntimeException("Mortgage can not be put in final approval yet");
        }
        LoanStatusModel status = getLoanStatus("E5");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse setApproved(Long mortgage_id, ExecutiveModel executive) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        if (!mortgage.getLoan_status().getId().equals("E5")) {
            throw new RuntimeException("Mortgage can not be put in approved yet");
        }
        LoanStatusModel status = getLoanStatus("E6");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse setRejected(Long mortgage_id, ExecutiveModel executive) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        if (!mortgage.getLoan_status().getId().equals("E3")) {
            throw new RuntimeException("Mortgage can not be put in rejected yet");
        }
        LoanStatusModel status = getLoanStatus("E7");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse setCancelledByClient(Long mortgage_id, ClientModel client) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        verifyOwnership(mortgage, client);
        LoanStatusModel status = getLoanStatus("E8");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }

    public SimpleResponse setInOutgo(Long mortgage_id, ExecutiveModel executive) {
        MortgageLoanStatusModel mortgage = getMortgageLoanStatus(mortgage_id);
        if (!mortgage.getLoan_status().getId().equals("E6")) {
            throw new RuntimeException("Mortgage can not be put in outgo yet");
        }
        LoanStatusModel status = getLoanStatus("E9");
        mortgage.setLoan_status(status);
        mortgageStatusRepository.save(mortgage);
        return new SimpleResponse("Successfully updated mortgage status");
    }



    private LoanStatusModel getLoanStatus(String id) {
        Optional<LoanStatusModel> status = loanStatusRepository.findById(id);
        if (status.isEmpty()) {
            throw new RuntimeException("Loan status not found");
        }
        return status.get();
    }

    private MortgageLoanStatusModel getMortgageLoanStatus(Long mortgage_id) {
        Optional<MortgageLoanStatusModel> mortgage = mortgageStatusRepository.findByMortgage_id(mortgage_id);
        if (mortgage.isEmpty()) {
            throw new RuntimeException("Mortgage not found");
        }
        return mortgage.get();
    }

    private void verifyOwnership(MortgageLoanStatusModel mortgage, ClientModel client) {
        Long client_id = client.getId();
        Long mortgage_owner_id = mortgage.getClientId();
        if (!Objects.equals(mortgage_owner_id, client_id)) {
            throw new RuntimeException("This is not the owner of the mortgage");
        }
    }
}
