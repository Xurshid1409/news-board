package uz.jurayev.newsboard.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.jurayev.newsboard.security.SecurityConstant;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login")){
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(SecurityConstant.HEADER_STRING);
            if (authorizationHeader !=null && authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)){
                try {
                    String token = authorizationHeader.replace(SecurityConstant.TOKEN_PREFIX, "");
                    Claims body = Jwts.parserBuilder()
                            .setSigningKey(Keys.hmacShaKeyFor(SecurityConstant.SECRET.getBytes()))
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
//                    String userId = body.getSubject();

                    String username = body.getSubject();
                    List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("roles");

                    Set<SimpleGrantedAuthority> grantedAuthorities = authorities
                            .stream().map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                            .collect(Collectors.toSet());

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, grantedAuthorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception ex){
                    log.error("Error login in {}", ex.getMessage());
                    response.setHeader("error", ex.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    Map<String, Object> error = new HashMap<>();
                    error.put("error_message", ex.getMessage());
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
