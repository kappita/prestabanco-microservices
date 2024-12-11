package tingeso.prestabanco.dto;


import lombok.Data;

@Data
public class MortgageStatusRequest {
    private Long client_id;
    private Long mortgage_id;
}
