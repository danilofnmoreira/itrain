package com.itrain.auth.domain;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
@JsonInclude(value = Include.NON_ABSENT)
@SuppressWarnings(value = { "serial" })
@Entity
@Table(name = "`user`", uniqueConstraints = { @UniqueConstraint(name = "`uq_user_username`", columnNames = { "`username`" }) })
public class User implements UserDetails {

    @JsonIgnore
    @Builder.Default
    @Transient
    private boolean accountNonExpired = true;

    @JsonIgnore
    @Builder.Default
    @Transient
    private boolean accountNonLocked = true;

    @JsonIgnore
    @Builder.Default
    @Transient
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @Builder.Default
    @Transient
    private boolean enabled = true;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_user_id")
    @SequenceGenerator(name = "sq_user_id", sequenceName = "`sq_user_id`", allocationSize = 1)
    @EqualsAndHashCode.Include
    @Column(name = "`id`")
    private Long id;

    @NotBlank
    @Size(max = 500)
    @Column(name = "`username`", nullable = false, length = 500, updatable = false)
    private String username;

    @JsonIgnore
    @NotBlank
    @Size(max = 500)
    @Column(name = "`password`", nullable = false, length = 500)
    private String password;

    @JsonIgnore
    @NotBlank
    @Size(max = 255)
    @Column(name = "`roles`", nullable = false, length = 255)
    private String roles;

    @NotNull
    @PastOrPresent
    @Column(name = "`registered_at`", nullable = false, updatable = false)
    private ZonedDateTime registeredAt;

    @NotNull
    @PastOrPresent
    @Column(name = "`updated_at`", nullable = false)
    private ZonedDateTime updatedAt;

    @Size(max = 2500)
    @Column(name = "`profile_picture_url`", length = 2500)
    private String profilePictureUrl;

    public void addRoles(final Set<UserRole> roles) {

        final var moreRoles = String.join(",", roles.stream().map(UserRole::name).collect(Collectors.toSet()));

        final var currentRoles = Objects.requireNonNullElse(getRoles(), "");

        setRoles(currentRoles + "," + moreRoles);
    }

    @JsonIgnore
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return AuthorityUtils.commaSeparatedStringToAuthorityList(getRoles());
    }


}
