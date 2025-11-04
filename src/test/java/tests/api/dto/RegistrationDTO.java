package tests.api.dto;

public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String address;

    private String city;
    private String state;

    private String zipCode;
    private String phoneNumber;
    private String ssn;
    private String userName;
    private String password;
    private String passwordValidation;


    public RegistrationDTO(String firstName, String lastName, String address,
                               String city, String state, String zipCode, String phoneNumber, String ssn, String userName, String password, String passwordValidation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
        this.userName = userName;
        this.password = password;
        this.passwordValidation = passwordValidation;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }


    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }


    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPasswordValidation() { return passwordValidation; }
    public void setPasswordValidation(String passwordValidation) { this.passwordValidation = passwordValidation; }
}


