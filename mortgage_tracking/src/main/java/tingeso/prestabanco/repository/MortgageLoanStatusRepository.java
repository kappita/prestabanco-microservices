package tingeso.prestabanco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tingeso.prestabanco.model.MortgageLoanStatusModel;

import java.util.List;
import java.util.Optional;

public interface MortgageLoanStatusRepository extends JpaRepository<MortgageLoanStatusModel, Long> {
    Optional<MortgageLoanStatusModel> findByMortgage_id(Long id);
    List<MortgageLoanStatusModel> findAllByClient_id(Long clientId);
}
