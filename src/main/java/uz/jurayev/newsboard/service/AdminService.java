package uz.jurayev.newsboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jurayev.newsboard.data.News;
import uz.jurayev.newsboard.repo.NewsRepository;
import uz.jurayev.newsboard.repo.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<News> getAllNews(){
        return newsRepository.findAll();
    }

    public void approvedNews(Principal principal){

    }
}
