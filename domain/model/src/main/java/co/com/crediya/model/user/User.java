package co.com.crediya.model.user;

import co.com.crediya.model.user.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String documentNumber;
    private DocumentType documentType;
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String emailAddress;
    private BigDecimal baseSalary;
}
