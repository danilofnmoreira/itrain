package com.itrain.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itrain.payload.converter.LocalDateTimeToStringConverter;
import com.itrain.payload.converter.StringToLocalDatetimeConverter;

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
@Table(name = "`gym`")
public class Gym implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "`id`", foreignKey = @ForeignKey(name = "`fk_gym_id`"))
    private User user;

    @JsonInclude(value = Include.NON_NULL)
    @CollectionTable(name = "`gym_contact`", foreignKey = @ForeignKey(name = "`fk_gym_contact_gym_id`"))
    @ElementCollection
    private Set<Contact> contacts;

    @JsonInclude(value = Include.NON_NULL)
    @CollectionTable(name = "`gym_address`", foreignKey = @ForeignKey(name = "`fk_gym_address_gym_id`"))
    @ElementCollection
    private Set<Address> addresses;

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

    @JsonInclude(value = Include.NON_NULL)
    @CollectionTable(name = "`gym_gallery_picture`", foreignKey = @ForeignKey(name = "`fk_gym_gallery_picture_gym_id`"))
    @ElementCollection
    @Size(max = 400)
    @Column(name = "gallery_picture_url", length = 400, nullable = false)
    private Set<String> galleryPicturesUrls;

    @Size(max = 400)
    @Column(name = "`instagram`", length = 400)
    private String instagram;

    @Size(max = 1000)
    @Column(name = "`biography`", length = 1000)
    private String biography;

    @JsonInclude(value = Include.NON_NULL)
    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "`gym_sport`", foreignKey = @ForeignKey(name = "`fk_gym_sport_gym_id`"), inverseForeignKey = @ForeignKey(name = "`fk_gym_sport_sport_id`"), inverseJoinColumns = { @JoinColumn(name = "`sport_id`") })
    private Set<Sport> sports;

    public void setUser(User user) {

        this.user = user;
        this.user.setGym(this);
    }

}