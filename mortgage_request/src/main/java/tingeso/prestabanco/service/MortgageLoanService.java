package tingeso.prestabanco.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tingeso.prestabanco.dto.*;
import tingeso.prestabanco.model.*;
import tingeso.prestabanco.repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@Service
public class MortgageLoanService {
    @Autowired
    MortgageLoanRepository mortgageLoanRepository;

    @Autowired
    MortgageLoanReviewRepository mortgageLoanReviewRepository;

    @Autowired
    PreApprovedMortgageLoanRepository preApprovedRepository;

    @Autowired
    UtilsService utilsService;

    @Autowired
    AuthService authService;


    public MortgageLoanModel getMortgageLoan(Long mortgage_loan_id, UserModel user) {
        MortgageLoanModel mortgage = getMortgageLoan(mortgage_loan_id);
        if (user.getRole().getName().equals("CLIENT")) {
            if (!mortgage.getClient_id().equals(user.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
        return mortgage;
    }


    public MortgageLoanModel receiveMortgage(MortgageLoanRequest req, String token) {
        LoanTypeModel loan_type = utilsService.getLoanType(token, req.getLoan_type_id());
        ClientModel client = authService.getClient(token);

        LoanStatusModel loan_status = utilsService.getLoanStatus(token, "E1");

        MortgageLoanModel mortgage_loan = new MortgageLoanModel(req,
                                                               client.getId(),
                                                               loan_status,
                                                               loan_type);

        mortgageLoanRepository.save(mortgage_loan);

        return mortgage_loan;
    }

    public SimpleResponse addDocuments(Long mortgage_loan_id, MultipartFile[] docs, String token) {
        MortgageLoanModel mortgage_loan = getMortgageLoan(mortgage_loan_id);
        ClientModel client = authService.getClient(token);
        validateMortgageOwnership(mortgage_loan, client);
        if (docs.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must send at least one new document");
        }
        Boolean is_on_E1 = mortgage_loan.getStatus().getId().equals("E1");
        Boolean is_on_E2 = mortgage_loan.getStatus().getId().equals("E2");

        if (!(is_on_E1 || is_on_E2)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can not add documents to a request in evaluation of further steps");
        }

        // Update the mortgage_loan status so it can be reviewed again
        LoanStatusModel loan_status = utilsService.getLoanStatus(token, "E1");
        mortgage_loan.addDocuments(docs);

        // TODO: set status on microservice
        mortgageLoanRepository.save(mortgage_loan);
        return new SimpleResponse("Documents added successfully");
    }

    private MortgageLoanModel getMortgageLoan(Long id) {
        Optional<MortgageLoanModel> mortgage = mortgageLoanRepository.findById(id);
        if (mortgage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The mortgage loan requested does not exist");
        }
        return mortgage.get();
    }

    private void validateMortgageOwnership(MortgageLoanModel mortgage_loan, ClientModel client) {
        Long mortgage_owner_id = mortgage_loan.getClient_id();
        if (!mortgage_owner_id.equals(client.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this mortgage loan");
        }
    }


}





