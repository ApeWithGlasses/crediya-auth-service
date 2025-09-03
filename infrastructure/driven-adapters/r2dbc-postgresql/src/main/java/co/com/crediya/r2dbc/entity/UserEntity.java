package co.com.crediya.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("usuarios")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column("id")
    private Long id;

    @Column("identificacion")
    private String documentNumber;

    @Column("tipo_identificacion")
    private String documentType;

    @Column("nombre")
    private String name;

    @Column("apellido")
    private String lastName;

    @Column("fecha_nacimiento")
    private LocalDate dateOfBirth;

    @Column("direccion")
    private String address;

    @Column("telefono")
    private String phone;

    @Column("correo_electronico")
    private String emailAddress;

    @Column("salario_base")
    private BigDecimal baseSalary;
}
