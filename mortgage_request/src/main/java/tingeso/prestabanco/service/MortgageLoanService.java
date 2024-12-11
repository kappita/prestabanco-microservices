package tingeso.prestabanco.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tingeso.prestabanco.dto.*;
import tingeso.prestabanco.model.*;
import tingeso.prestabanco.repository.*;

import java.util.*;

@Service
public class MortgageLoanService {
    @Autowired
    MortgageLoanRepository mortgageLoanRepository;

    @Autowired
    PreApprovedMortgageLoanRepository preApprovedRepository;

    @Autowired
    UtilsService utilsService;

    @Autowired
    AuthService authService;

    @Autowired
    TrackingService trackingService;

    public List<MortgageLoanModel> getAllClientMortgageLoans(ClientModel client) {
        List<MortgageLoanModel> mortgages = mortgageLoanRepository.findAllByClientId(client.getId());
        return mortgages;
    }


    public MortgageLoanModel getMortgageLoan(Long mortgage_loan_id, UserModel user) {
        MortgageLoanModel mortgage = getMortgageLoan(mortgage_loan_id);
        if (user.getRole().getName().equals("CLIENT")) {
            if (!mortgage.getClientId().equals(user.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
        return mortgage;
    }


    public MortgageLoanModel receiveMortgage(MortgageLoanRequest req, String token) {
        LoanTypeModel loan_type = utilsService.getLoanType(token, req.getLoan_type_id());
        ClientModel client = authService.getClient(token);
        MortgageLoanModel mortgage_loan = new MortgageLoanModel(req,
                                                               client.getId(),
                                                               loan_type);
        MortgageLoanModel new_mortgage = mortgageLoanRepository.save(mortgage_loan);
        MortgageStatusRequest mortgage_status_request = new MortgageStatusRequest();
        mortgage_status_request.setMortgage_id(new_mortgage.getId());
        mortgage_status_request.setClient_id(client.getId());
        trackingService.createMortgageStatus(mortgage_status_request, token);
        return new_mortgage;
    }

    public SimpleResponse addDocuments(Long mortgage_loan_id, MultipartFile[] docs, String token) {
        MortgageLoanModel mortgage_loan = getMortgageLoan(mortgage_loan_id);
        ClientModel client = authService.getClient(token);
        validateMortgageOwnership(mortgage_loan, client);
        if (docs.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must send at least one new document");
        }
        trackingService.updateDocuments(mortgage_loan_id, token);

        mortgageLoanRepository.save(mortgage_loan);
        return new SimpleResponse("Documents added successfully");
    }

    public SimpleResponse preApproveMortgage(Long mortgage_loan_id, ExecutiveModel executive) {
        MortgageLoanModel original = getMortgageLoan(mortgage_loan_id);
        PreApprovedMortgageLoanModel mortgage = new PreApprovedMortgageLoanModel(original);
        preApprovedRepository.save(mortgage);
        return new SimpleResponse("PreApproved Mortgage successfully");
    }

    private MortgageLoanModel getMortgageLoan(Long id) {
        Optional<MortgageLoanModel> mortgage = mortgageLoanRepository.findById(id);
        if (mortgage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The mortgage loan requested does not exist");
        }
        return mortgage.get();
    }

    private void validateMortgageOwnership(MortgageLoanModel mortgage_loan, ClientModel client) {
        Long mortgage_owner_id = mortgage_loan.getClientId();
        if (!mortgage_owner_id.equals(client.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this mortgage loan");
        }
    }

}





