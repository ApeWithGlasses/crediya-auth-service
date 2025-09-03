package co.com.crediya.api.dto;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserRequest {
    @NotNull(message = "El número de documento es requerido")
    @NotBlank(message = "El número de documento no puede estar vacío")
    @Pattern(regexp = "^\\d+$", message = "El número de documento solo debe contener números")
    @Schema(example = "123456789", description = "Número de documento del usuario", required = true)
    private String documentNumber;

    @NotNull(message = "El tipo de documento es requerido")
    @NotBlank(message = "El tipo de documento no puede estar vacío")
    @Pattern(regexp = "^(CC|CE|TI|PA)$", message = "El tipo de documento debe ser CC, CE, TI o PA")
    @Schema(example = "CC", description = "Tipo de documento del usuario (CC, CE, TI, PA)", required = true)
    private String documentType;

    @NotNull(message = "El nombre es requerido")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(example = "Juan", description = "Nombre del usuario", required = true)
    private String name;

    @NotNull(message = "El apellido es requerido")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Schema(example = "Pérez", description = "Apellido del usuario", required = true)
    private String lastName;

    @NotNull(message = "La fecha de nacimiento es requerida")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @Schema(example = "1990-01-01", description = "Fecha de nacimiento del usuario (YYYY-MM-DD)", required = true)
    private LocalDate dateOfBirth;

    @NotNull(message = "La dirección es requerida")
    @NotBlank(message = "La dirección no puede estar vacía")
    @Schema(example = "Calle 123 #45-67", description = "Dirección del usuario", required = true)
    private String address;

    @NotNull(message = "El teléfono es requerido")
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Schema(example = "3001234567", description = "Número de teléfono del usuario", required = true)
    private String phone;

    @NotNull(message = "El correo electrónico es requerido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    @Schema(example = "email@example.com", description = "Correo electrónico del usuario", required = true)
    private String emailAddress;

    @NotNull(message = "El salario base es requerido")
    @DecimalMin(value = "0.0", message = "El salario base no puede ser menor a 0")
    @DecimalMax(value = "15000000.0", message = "El salario base no puede ser mayor a 15.000.000")
    @Schema(example = "2500000.00", description = "Salario base del usuario", required = true)
    private BigDecimal baseSalary;

    public User toDomain() {
        return User.builder()
                .documentNumber(this.documentNumber)
                .documentType(DocumentType.valueOf(this.documentType))
                .name(this.name)
                .lastName(this.lastName)
                .dateOfBirth(this.dateOfBirth)
                .address(this.address)
                .phone(this.phone)
                .emailAddress(this.emailAddress)
                .baseSalary(this.baseSalary)
                .build();
    }
}