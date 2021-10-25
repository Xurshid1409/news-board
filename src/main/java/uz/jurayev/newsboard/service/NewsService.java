package uz.jurayev.newsboard.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.jurayev.newsboard.data.News;
import uz.jurayev.newsboard.data.enums.StatusNews;
import uz.jurayev.newsboard.data.User;
import uz.jurayev.newsboard.exception.NewsNotFoundException;
import uz.jurayev.newsboard.model.request.NewsRequest;
import uz.jurayev.newsboard.repo.NewsRepository;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    public static final Logger LOG = LoggerFactory.getLogger(NewsService.class);
    private final UserService userService;
    private final NewsRepository newsRepository;

//  Get all news with status approved
    public List<News> getAllApprovedNews(){
        return newsRepository.findAllByNewsStatus(StatusNews.APPROVED);
    }

    //User Publish news
    public void publishNews(NewsRequest newsRequest, Principal principal){
        User user = userService.getCurrentUser(principal);
        News news = new News();
        news.setUser(user);
        news.setMessage(newsRequest.getMessage());
        news.setTitle(newsRequest.getTitle());
        news.setNewsStatus(StatusNews.NEW);
        news.setCreatedDate(LocalDateTime.now());
        LOG.info("Saving news for user: {}", user.getEmail());
        newsRepository.save(news);
    }

    //Get user news by id
    public News getNewsById(Long postId, Principal principal){
        User user = userService.getCurrentUser(principal);
        return newsRepository.findNewsByIdAndUser(postId, user)
                .orElseThrow(() -> new NewsNotFoundException(String.format("News can't be found %s", user.getUsername())));
    }

    //Get user all news
    public List<News> getAllNewsForUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        return newsRepository.findAllByUserAndNewsStatusOrderByCreatedDateDesc(user, StatusNews.APPROVED);
    }

    //User update own news
    public void updateNews(Long postId, Principal principal, NewsRequest newsRequest){
        User user = userService.getCurrentUser(principal);
        News newsByIdAndUser = newsRepository.findNewsByIdAndUser(postId, user)
                .orElseThrow(() -> new NewsNotFoundException(String.format("News can't be found %s", user.getUsername())));
        newsByIdAndUser.setMessage(newsRequest.getMessage());
        newsByIdAndUser.setNewsStatus(StatusNews.NEW);
        newsRepository.save(newsByIdAndUser);
    }
}
