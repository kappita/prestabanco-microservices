package tingeso.prestabanco.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class DocumentModel {

    private Long id;
    private String name;
//    @ManyToOne
//    @JoinColumn(name = "document_type_id")
//    private DocumentTypeModel type;
    private byte[] data;
}
