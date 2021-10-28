package uz.jurayev.newsboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.jurayev.newsboard.data.News;
import uz.jurayev.newsboard.data.enums.NewsStatus;
import uz.jurayev.newsboard.data.User;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findNewsByIdAndUser(Long newsId, User user);

    List<News> findAllByUserAndNewsStatusOrderByCreatedDateDesc(User user, NewsStatus statusNews);

    List<News> findAllByNewsStatus(NewsStatus status);

    Optional<News> findNewsByIdAndNewsStatus(Long newsId, NewsStatus statusNews);

    @Query(value = "select * from news n " +
            "inner join " +
            "users u on u.id = n.user_id " +
            "order by n.created_date desc", nativeQuery = true)
    List<News> findAllNewsAndSortedByCreatedDateDesc();

}
