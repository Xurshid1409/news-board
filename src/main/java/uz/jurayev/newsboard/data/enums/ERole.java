package uz.jurayev.newsboard.data.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static uz.jurayev.newsboard.data.enums.Permission.*;

public enum ERole {

    ROLE_ADMIN(Sets.newHashSet(NEWS_READ, NEWS_WRITE, USER_READ, USER_WRITE)),
    ROLE_USER(Sets.newHashSet(USER_READ, USER_WRITE));

    private final Set<Permission> permissions;

    ERole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}

