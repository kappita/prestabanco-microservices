package tingeso.prestabanco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "mortgage_loan_status")
@AllArgsConstructor
@NoArgsConstructor
public class MortgageLoanStatusModel {
    @Id
    private Long mortgage_id;
    @Column(name="client_id")
    private Long clientId;

    @JoinColumn(name="loan_status_id")
    @ManyToOne
    private LoanStatusModel loan_status;
}
