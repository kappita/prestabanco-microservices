package tingeso.prestabanco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanTypeModel {
    private Long id;
    private String name;
    private Integer max_term;
    private Float min_interest_rate;
    private Float max_interest_rate;
    private Float max_financed_percentage;
    private List<DocumentTypeModel> required_documents;
}
