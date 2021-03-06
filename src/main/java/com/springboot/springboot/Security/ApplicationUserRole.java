package com.springboot.springboot.Security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            ApplicationUserPermission.STUDENT_WRITE, ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.COURSE_READ, ApplicationUserPermission.COURSE_WRITE)),
    ADMIN1(Sets.newHashSet(
            ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> grantedAuthorities() {
        Set<GrantedAuthority> grantedAuthorities =
                getPermissions().stream().map(permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
                        .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
