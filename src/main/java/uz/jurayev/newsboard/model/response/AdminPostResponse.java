package uz.jurayev.newsboard.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminPostResponse {

    private Long id;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private String username;
    private String email;
    private String newsStatus;
    private LocalDateTime approvedDate;
}
