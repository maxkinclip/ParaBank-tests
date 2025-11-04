package tests.api.dto;

public class ContactDTO {
    private String fullName;
    private String email;
    private String phone;
    private String message;


    public ContactDTO(String name, String email, String phone,String message){
        this.fullName = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }
    public String getFullName() { return fullName; }
    public void setName(String name) { this.fullName = name; }

    public String email() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String phone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String message() { return message; }
    public void writeMessage(String message) { this.message = message; }


}
