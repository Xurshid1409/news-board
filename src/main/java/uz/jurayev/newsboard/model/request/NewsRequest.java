package uz.jurayev.newsboard.model.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NewsRequest {

    @Size(max = 500)
    private String title;
    @Size(max = 500)
    private String message;
}
