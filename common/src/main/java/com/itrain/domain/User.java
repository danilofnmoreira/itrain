package com.itrain.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "`email`", unique = true)
    private String email;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`username`")
    private String username;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`roles`")
    private String roles;

    public void setUserRoles(Set<UserRole> roles) {

        Objects.requireNonNull(roles);

        var strRoles = roles
            .stream()
            .map(role -> "ROLE_" + role.name())
            .collect(Collectors.toSet());

        setRoles(String.join(",", strRoles));
    }

    @Transient
    public Set<UserRole> getUserRoles() {

        if(getRoles() == null) {
            return Collections.emptySet();
        }

        var roleArray = getRoles().replace("ROLE_", "").split(",");

        var rolesSet = new HashSet<UserRole>();
        for(var strRole : roleArray) {
            rolesSet.add(UserRole.getByName(strRole));
        }
        return rolesSet;
    }

}
