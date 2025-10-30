package tests.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequestModel {

    private String userName;
    private String login;
    private String phoneNumber;
    private String password;
    private String passwordValidation;}
