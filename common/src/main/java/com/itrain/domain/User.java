package com.itrain.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itrain.payload.converter.LocalDateTimeToStringConverter;
import com.itrain.payload.converter.StringToLocalDatetimeConverter;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", shape = Shape.STRING)
    @JsonDeserialize(converter = StringToLocalDatetimeConverter.class)
    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    @Column(name = "`registered_at`", nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", shape = Shape.STRING)
    @JsonDeserialize(converter = StringToLocalDatetimeConverter.class)
    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    @Column(name = "`updated_at`", nullable = false)
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private PersonalTrainer personalTrainer;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private Gym gym;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private Client client;

    @Size(max = 2500)
    @Column(name = "`profile_picture_url`", length = 2500)
    private String profilePictureUrl;

    public void setRoles(String roles) {

        this.roles = roles;
    }

    public void setRoles(Set<UserRole> roles) {

        var strRoles = roles.stream().map(UserRole::name).collect(Collectors.toSet());

        setRoles(String.join(",", strRoles));
    }

    @Transient
    @JsonInclude(value = Include.NON_NULL)
    @JsonProperty(value = "roles")
    public Set<UserRole> getUserRoles() {

        return getAuthorities().stream().map(authority -> UserRole.getByName(authority.getAuthority()))
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return AuthorityUtils.commaSeparatedStringToAuthorityList(getRoles());
    }

    public void addClient(Client client) {

        this.client = client;
        this.client.setUser(this);
    }

    public void addGym(Gym gym) {

        this.gym = gym;
        this.gym.setUser(this);
    }

    public void addPersonalTrainer(PersonalTrainer personalTrainer) {

        this.personalTrainer = personalTrainer;
        this.personalTrainer.setUser(this);
    }

}
