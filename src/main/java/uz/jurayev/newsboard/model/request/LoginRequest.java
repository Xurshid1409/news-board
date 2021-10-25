package uz.jurayev.newsboard.model.request;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotEmpty(message = "Username can't be empty")
    private String username;
    @NotEmpty(message = "password can't be empty")
    @Size(min = 8)
    private String password;
}
