package uz.jurayev.newsboard.security;

public class SecurityConstant {

    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final String SECRET = "secretKeyGenerat3443fgj789ggdf3434a343478dlkfldfk9e48394894389fsdath874gfd";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final Long EXPIRATION_TIME = 600_000L;
}
