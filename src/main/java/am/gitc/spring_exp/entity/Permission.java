package am.gitc.spring_exp.entity;

public enum Permission {
    DEVELOPER_READ("developers:read"),
    DEVELOPER_WRITE("developers:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
