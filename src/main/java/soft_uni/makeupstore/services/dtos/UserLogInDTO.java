package soft_uni.makeupstore.services.dtos;

public class UserLogInDTO implements BaseDTO{

    private String email;
    private String password;

    public UserLogInDTO() {
    }

    public UserLogInDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
