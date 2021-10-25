package uz.jurayev.newsboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jurayev.newsboard.data.News;
import uz.jurayev.newsboard.data.enums.StatusNews;
import uz.jurayev.newsboard.data.User;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findNewsByIdAndUser(Long postId, User user);

    List<News> findAllByUserAndNewsStatusOrderByCreatedDateDesc(User user, StatusNews statusNews);

    List<News> findAllByNewsStatus(StatusNews status);

}
