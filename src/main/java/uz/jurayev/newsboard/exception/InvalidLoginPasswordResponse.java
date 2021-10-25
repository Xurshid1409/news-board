package uz.jurayev.newsboard.exception;

import lombok.Getter;

@Getter
public class InvalidLoginPasswordResponse {

    private String username;
    private String password;

    public InvalidLoginPasswordResponse() {
        this.username = "invalid username";
        this.password = "invalid password";
    }
}
