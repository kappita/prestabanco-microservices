package tingeso.prestabanco.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientModel extends UserModel {
    private String name;
    private String last_name;
    private Date birth_date;
    private String gender;
    private String nationality;
    private String address;
    private String phone_number;

    public ClientModel(UserModel user, String name, String last_name, Date birth_date, String gender, String nationality, String address, String phone_number) {
        super(user);
        this.name = name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.gender = gender;
        this.nationality = nationality;
        this.address = address;
        this.phone_number = phone_number;
    }



}
