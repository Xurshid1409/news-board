package uz.jurayev.newsboard.model.request;

import lombok.Data;
import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 8)
    private String password;

    @Pattern(regexp = "([a-zA-Z0-9_.-])+@([a-zA-Z0-p_.-])+\\.com", message = "Email should match pattern abcd@abcd.com")
    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;
}
