package uz.jurayev.newsboard.model.response;

import lombok.Data;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String message;
    private String username;
}
