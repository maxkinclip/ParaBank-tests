package tests.api.dto;

public class ContactDTO {
    private String name;
    private String email;
    private String phone;
    private String message;


    public ContactDTO(String name, String email, String phone,String message){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }
    public String name() { return name; }
    public void setName(String name) { this.name = name; }

    public String email() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String phone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String message() { return message; }
    public void writeMessage(String message) { this.message = message; }


}
