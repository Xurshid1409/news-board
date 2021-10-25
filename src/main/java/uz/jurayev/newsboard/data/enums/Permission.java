package uz.jurayev.newsboard.data.enums;

public enum Permission {

    USER_READ("read"),
    USER_WRITE("write"),
    NEWS_READ("read"),
    NEWS_WRITE("write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
