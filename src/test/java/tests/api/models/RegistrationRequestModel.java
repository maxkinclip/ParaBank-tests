package tests.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequestModel {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private String ssnNumber;
    private String userName;
    private String password;
    private String passwordValidation;
}
