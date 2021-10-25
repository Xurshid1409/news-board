package uz.jurayev.newsboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NewsNotFoundException extends RuntimeException{

    public NewsNotFoundException(String message) {
        super(message);
    }
}
