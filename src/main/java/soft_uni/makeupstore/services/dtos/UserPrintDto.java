package soft_uni.makeupstore.services.dtos;

public class UserPrintDto implements BaseDTO{
    private String fullName;
    private String email;
    private String password;

    public UserPrintDto() {
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("Full name - %s%nEmail - %s%nPassword - %s", fullName, email, password);
    }
}
