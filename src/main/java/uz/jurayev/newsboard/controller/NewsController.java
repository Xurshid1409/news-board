package uz.jurayev.newsboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.jurayev.newsboard.model.MessageResponse;
import uz.jurayev.newsboard.model.request.NewsRequest;
import uz.jurayev.newsboard.model.response.PostResponse;
import uz.jurayev.newsboard.service.NewsService;
import uz.jurayev.newsboard.util.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news/api")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final ResponseErrorValidation responseErrorValidation;


//  Get all approved news controller

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllNews(){

        List<PostResponse> allNews = newsService.getAllApprovedNews()
                .stream()
                .map(p -> {
                    PostResponse postResponse = new PostResponse();
                    postResponse.setTitle(p.getTitle());
                    postResponse.setMessage(p.getMessage());
                    postResponse.setUsername(p.getUser().getUsername());
                    return postResponse;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(allNews);
    }

//  Get user all news

    @GetMapping("/user/news")
    public ResponseEntity<List<PostResponse>> getAllNewsForUser(Principal principal){

        List<PostResponse> userNews = newsService.getAllNewsForUser(principal)
                .stream()
                .map(p -> {
                    PostResponse postResponse = new PostResponse();
                    postResponse.setTitle(p.getTitle());
                    postResponse.setMessage(p.getMessage());
                    postResponse.setUsername(p.getUser().getUsername());
                    return postResponse;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(userNews);
    }

//  Update user own news

    @PutMapping("/{postId}")
    public ResponseEntity<Object> updateNews(@Valid @PathVariable String postId,
                                             @RequestBody NewsRequest newsRequest, Principal principal,
                                              BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)){
            return errors;
        }
        newsService.updateNews(Long.parseLong(postId), principal, newsRequest);
        return ResponseEntity.ok(new MessageResponse("Your news awaiting admin processing"));
    }

//  User publish news

    @PostMapping("/publish")
    public ResponseEntity<Object> publishNews(@Valid @RequestBody NewsRequest newsRequest, Principal principal,
                                              BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)){
            return errors;
        }
        newsService.publishNews(newsRequest, principal);
        return ResponseEntity.ok(new MessageResponse("Your news awaiting admin processing"));
    }
}
