package co.com.crediya.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class User {
    private String id;
    private String name;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private String phone;
    private String emailAddress;
    private BigDecimal baseSalary;
}
