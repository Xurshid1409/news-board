package uz.jurayev.newsboard.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.jurayev.newsboard.data.News;
import uz.jurayev.newsboard.data.enums.NewsStatus;
import uz.jurayev.newsboard.exception.NewsNotFoundException;
import uz.jurayev.newsboard.repo.NewsRepository;
import uz.jurayev.newsboard.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    public static final Logger LOG = LoggerFactory.getLogger(AdminService.class);

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<News> getAllNews(){
        return newsRepository.findAllNewsAndSortedByCreatedDateDesc();
    }

    public void approvedNews(Long newsId){
        News newNews = newsRepository.findNewsByIdAndNewsStatus(newsId, NewsStatus.NEW)
                .orElseThrow(() -> new NewsNotFoundException("News not found"));
        newNews.setNewsStatus(NewsStatus.APPROVED);
        newNews.setApprovedDate(LocalDateTime.now());
        newsRepository.save(newNews);
    }

    public void refusedNews(Long newsId){
        News newNews = newsRepository.findNewsByIdAndNewsStatus(newsId, NewsStatus.NEW)
                .orElseThrow(() -> new NewsNotFoundException("News not found"));
        newNews.setNewsStatus(NewsStatus.REFUSED);
        newNews.setApprovedDate(LocalDateTime.now());
        newsRepository.save(newNews);
    }
}
