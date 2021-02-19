package am.gitc.spring_exp.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.DEVELOPER_READ)),
    ADMIN(Set.of(Permission.DEVELOPER_READ,Permission.DEVELOPER_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
