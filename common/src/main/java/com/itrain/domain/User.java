package com.itrain.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = SnakeCaseStrategy.class)
@JsonInclude(value = Include.NON_EMPTY)
@Entity
@Table(name = "`user`")
@SuppressWarnings(value = { "serial" })
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(max = 400)
    @Column(name = "`username`", nullable = false, length = 400, updatable = false, unique = true)
    private String username;

    @JsonIgnore
    @NotBlank
    @Size(max = 20)
    @Column(name = "`password`", nullable = false, length = 20)
    private String password;

    @NotBlank
    @Column(name = "`roles`", nullable = false)
    private String roles;

    public void setRoles(String roles) {

        this.roles = roles;
    }

    public void setRoles(Set<UserRole> roles) {

        var strRoles = roles
            .stream()
            .map(role -> "ROLE_" + role.name())
            .collect(Collectors.toSet());

        setRoles(String.join(",", strRoles));
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(getRoles());
    }

    @Transient private boolean accountNonExpired;
    @Transient private boolean accountNonLocked;
    @Transient private boolean credentialsNonExpired;
    @Transient private boolean enabled;

}
