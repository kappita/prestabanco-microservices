package tingeso.prestabanco.service;


import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tingeso.prestabanco.dto.*;
import tingeso.prestabanco.model.*;

import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

@Service
public class MortgageEvaluationService {
    @Autowired
    TrackingService trackingService;

    @Autowired
    MortgageService mortgageService;

    public SimpleResponse setPendingDocumentation(Long mortgage_loan_id, ExecutiveModel executive, String token) {
        trackingService.setMissingDocuments(mortgage_loan_id, token);
        return new SimpleResponse("Mortgage loan updated");
    }

    public SimpleResponse setInEvaluation(Long mortgage_loan_id, ExecutiveModel executive, String token) {
        trackingService.setInEvaluation(mortgage_loan_id, token);
        return new SimpleResponse("Mortgage loan updated");
    }

    public SimpleResponse evaluateMortgage(Long mortgage_loan_id, CreditEvaluation evaluation, ExecutiveModel executive, String token) {
        MortgageLoanModel mortgage = mortgageService.getMortgageLoan(mortgage_loan_id, token);
        try {
            validateCredit(mortgage, evaluation.getCredit_validation());
        } catch (Exception e) {
            return setRejected(mortgage_loan_id, executive, token);
        }

        int saving_score = sumSavingsScore(mortgage, evaluation.getSaving_capacity());
        System.out.println(saving_score);
        if (saving_score > 4) {
            System.out.println("PREAPROBADO");
            return setPreApproved(mortgage_loan_id, executive, token);
        }
        if (saving_score > 2) {
            System.out.println("REQUIERE REVISION");
            return new SimpleResponse("Mortgage loan requires extra review");
        }

        System.out.println("REHCAZADO POR DESCARTE");
        return setRejected(mortgage_loan_id, executive, token);
    }

    public SimpleResponse setPreApproved(Long mortgage_loan_id, ExecutiveModel executive, String token) {
        trackingService.setPreApproved(mortgage_loan_id, token);
        return new SimpleResponse("Mortgage loan updated");

    }


    public SimpleResponse setApproved(Long mortgage_loan_id, ExecutiveModel executive, String token) {
        trackingService.setApproved(mortgage_loan_id, token);
        return new SimpleResponse("Mortgage loan updated");
    }

    public SimpleResponse setRejected(Long mortgage_loan_id, ExecutiveModel executive, String token) {
        trackingService.setRejected(mortgage_loan_id, token);
        return new SimpleResponse("Mortgage has been rejected");
    }


    public SimpleResponse setInOutgo(Long mortgage_loan_id, ExecutiveModel executive, String token) {
        trackingService.setInOutgo(mortgage_loan_id, token);
        return new SimpleResponse("Mortgage loan updated");
    }

    private void validateQuotaIncomeRelation(MortgageLoanModel mortgage, Long income) {
        Long monthly_quota = mortgage.getMonthlyQuota();
        float relation = (float) monthly_quota / income;
        if (relation > 0.35) {
            System.out.println("LA RELACION COUTA INCOME ES MUY BAJA");
            throw new IllegalArgumentException("Quota exceeded");
        }
    }

    private void validateCreditHistory(Boolean has_acceptable_history) {
        if (!has_acceptable_history) {
            System.out.println("NO TIENE CREDITO");
            throw new IllegalArgumentException("Credit history not valid");
        }
    }

    private void validateFinancialStability(Boolean has_financial_stability) {
        if (!has_financial_stability) {
            System.out.println("NO TIENE ESTABILIDAD");
            throw new IllegalArgumentException("Financial stability not valid");
        }
    }

    private void validateDebtIncomeRelation(MortgageLoanModel mortgage, Long income, Long monthly_debt) {
        Long monthly_quota = mortgage.getMonthlyQuota();
        float relation = (float) (monthly_quota + monthly_debt) / income;
        if (relation > 0.5) {
            System.out.println("MUCHA DEUDA");
            throw new IllegalArgumentException("Debt exceeded");
        }
    }

    private void validateMaxFinance(MortgageLoanModel mortgage_loan, Long property_value) {
        float max_finance = mortgage_loan.getLoan_type().getMax_financed_percentage() * property_value;
        if (mortgage_loan.getFinanced_amount() > max_finance) {
            System.out.println("ES DEMASIADA FINANCIACION");
            throw new IllegalArgumentException("Financed amount exceeded");
        }
    }

    private void validateMaxAge(MortgageLoanModel mortgage_loan, Date validated_birthdate) {
        int client_birth_year = validated_birthdate.toLocalDate().getYear();
        int current_year = LocalDate.now().getYear();
        int client_years = current_year - client_birth_year;
        int payment_term = mortgage_loan.getPayment_term();

        if ((client_years + payment_term) > 70) {
            System.out.println("MUY VIEJO");
            throw new IllegalArgumentException("Client wont pay before jubilation");
        }
    }

    private void validateCredit(MortgageLoanModel mortgage_loan, CreditValidation credit_validation) {
        validateQuotaIncomeRelation(mortgage_loan, credit_validation.getClient_income());
        validateCreditHistory(credit_validation.getHas_acceptable_credit_history());
        validateFinancialStability(credit_validation.getHas_financial_stability());
        validateDebtIncomeRelation(mortgage_loan, credit_validation.getClient_income(), credit_validation.getMonthly_debt());
        validateMaxFinance(mortgage_loan, credit_validation.getProperty_value());
        validateMaxAge(mortgage_loan, mortgage_loan.getClient().getBirth_date());
    }

    private int sumSavingsScore(MortgageLoanModel mortgage, SavingCapacity saving_capacity) {
        int total = 0;
        total += (checkMinimumBalance(mortgage,
                                      saving_capacity.getClient_balance()) ? 1 : 0);
        total += (saving_capacity.isHas_consistent_savings() ? 1 : 0);
        total += (saving_capacity.isHas_periodic_savings() ? 1 : 0);
        total += (checkBalanceLongevityRelation(mortgage,
                                                saving_capacity.getClient_balance(),
                                                saving_capacity.getSavings_account_longevity()) ? 1 : 0);
        total += (saving_capacity.is_financially_stable() ? 1 : 0);
        return total;
    }

    private boolean checkMinimumBalance(MortgageLoanModel mortgage_loan, Long balance) {
        float relation = (float) balance / mortgage_loan.getFinanced_amount();
        return relation > 0.1;
    }

    private boolean checkBalanceLongevityRelation(MortgageLoanModel mortgage_loan, Long balance, int longevity) {
        float relation = (float) balance / mortgage_loan.getFinanced_amount();
        if (longevity < 2) {
            return relation > 0.2;
        } else {
            return relation > 0.1;
        }
    }
}





