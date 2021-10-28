package uz.jurayev.newsboard.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.jurayev.newsboard.data.User;
import uz.jurayev.newsboard.repo.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        return SecurityUser.fromUser(user);

//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        user.getRoles().forEach(eRole -> {
//            authorities.add(new SimpleGrantedAuthority(eRole.name()));
//        });
//
//        return new  org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                authorities);
    }

    public User loadUserById(Long id){
        return userRepository.findUserById(id).orElse(null);
    }
}
