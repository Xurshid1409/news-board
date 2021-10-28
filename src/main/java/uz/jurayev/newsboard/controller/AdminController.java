package uz.jurayev.newsboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jurayev.newsboard.model.MessageResponse;
import uz.jurayev.newsboard.model.response.AdminPostResponse;
import uz.jurayev.newsboard.service.AdminService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/management/api")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public List<AdminPostResponse> getAllNews(){
        return adminService.getAllNews()
                .stream()
                .map(a -> {
                    AdminPostResponse adminPostResponse = new AdminPostResponse();
                    adminPostResponse.setId(a.getId());
                    adminPostResponse.setCreationDate(a.getCreatedDate());
                    adminPostResponse.setTitle(a.getTitle());
                    adminPostResponse.setMessage(a.getMessage());
                    adminPostResponse.setNewsStatus(a.getNewsStatus().name());
                    adminPostResponse.setUsername(a.getUser().getUsername());
                    adminPostResponse.setEmail(a.getUser().getEmail());
                    adminPostResponse.setApprovedDate(a.getApprovedDate());
                    return adminPostResponse;

                }).collect(Collectors.toList());
    }

    @PatchMapping(value = "/approved/{newsId}")
    public ResponseEntity<Object> approvedNews(@PathVariable String newsId){

            adminService.approvedNews(Long.parseLong(newsId));
        return ResponseEntity.ok(new MessageResponse("News successfully approved"));
    }

    @PatchMapping("/refused/{newsId}")
    public ResponseEntity<Object> refusedNews(@PathVariable String newsId) {

        adminService.refusedNews(Long.parseLong(newsId));
        return ResponseEntity.ok(new MessageResponse("News successfully refused"));
    }

}
