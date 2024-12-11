package tingeso.prestabanco.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MortgageLoanReviewModel extends MortgageLoanModel {

    private ExecutiveModel review_requester;
    private boolean has_been_reviewed = false;
    private boolean has_been_approved = false;

    public MortgageLoanReviewModel(MortgageLoanModel mortgage, ExecutiveModel executive) {
        super(mortgage);
        this.review_requester = executive;
    }

}
