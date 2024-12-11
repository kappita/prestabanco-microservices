DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS base_user CASCADE;
DROP TABLE IF EXISTS client_details CASCADE;
DROP TABLE IF EXISTS executive_details CASCADE;
DROP TABLE IF EXISTS loan_type CASCADE;
DROP TABLE IF EXISTS mortgage_loan CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS executive CASCADE;
DROP TABLE IF EXISTS document_type CASCADE;
DROP TABLE IF EXISTS document CASCADE;
DROP TABLE IF EXISTS mortgage_document CASCADE;
DROP TABLE IF EXISTS mortgage_loan_review CASCADE;
DROP TABLE IF EXISTS preapproved_mortgage_loan CASCADE;
DROP TABLE IF EXISTS loan_type_required_document CASCADE;
DROP TABLE IF EXISTS loan_status CASCADE;
DROP TABLE IF EXISTS mortgage_loan_pending_documentation CASCADE;
DROP TABLE IF EXISTS pending_documentation CASCADE;



CREATE TABLE loan_status (
    id TEXT PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL
);

INSERT INTO loan_status (id, name, description)
    VALUES ('E1', 'En Revisión Inicial', 'La solicitud ha sido recibida y está en proceso de verificación preliminar.'),
        ('E2', 'Pendiente de Documentación', 'La solicitud está en espera porque falta uno o más documentos importantes o se requiere información adicional del cliente.'),
        ('E3', 'En Evaluación', 'La solicitud ha pasado la revisión inicial y está siendo evaluada por un ejecutivo.'),
        ('E4', 'Pre-Aprobada', 'La solicitud ha sido evaluada y cumple con los criterios básicos del banco, por lo que ha sido pre-aprobada'),
        ('E5', 'En Aprobación Final', 'El cliente ha aceptado las condiciones propuestas, y la solicitud se encuentra en proceso de aprobación final.'),
        ('E6', 'Aprobada', 'La solicitud ha sido aprobada y está lista para el desembolso.'),
        ('E7', 'Rechazada', 'La solicitud ha sido evaluada y, tras el análisis, no cumple con los criterios establecidos por el banco.'),
        ('E8', 'Cancelada por el Cliente', 'El cliente ha decidido cancelar la solicitud antes de que esta sea aprobada.'),
        ('E9', 'En Desembolso', 'La solicitud ha sido aprobada y se está ejecutando el proceso de desembolso del monto aprobado');


CREATE TABLE mortgage_loan_status (
    mortgage_id BIGINT PRIMARY KEY NOT NULL,
    client_id BIGINT NOT NULL,
    loan_status_id TEXT NOT NULL,
    FOREIGN KEY (loan_status_id) REFERENCES loan_status(id)
);