package dto;

public class LoginDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String uname) {
        username = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String paswrd) {
        password = paswrd;
    }

    @Override
    public String toString() {
        return "username: " + username + " password: " + password;
    }
}
