package tingeso.prestabanco.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
public class ExecutiveModel extends UserModel {
    private String name;

    public ExecutiveModel(UserModel user, String name) {
        super(user);
        this.name = name;
    }

}
