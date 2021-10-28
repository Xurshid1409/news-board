package uz.jurayev.newsboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jurayev.newsboard.data.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findUserById(Long id);
}
