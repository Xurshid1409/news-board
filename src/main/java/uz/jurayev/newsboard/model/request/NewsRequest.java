package uz.jurayev.newsboard.model.request;

import lombok.Data;

@Data
public class NewsRequest {

    private String title;
    private String message;
}
