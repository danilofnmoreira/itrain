package com.itrain.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
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
@Table(name = "`personal_trainer`")
public class PersonalTrainer implements Serializable {

    @MapsId
    @EqualsAndHashCode.Include
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "`id`", foreignKey = @ForeignKey(name = "`fk_personal_trainer_id`"))
    private User user;

    @Id
    private Long id;

    @JsonInclude(value = Include.NON_NULL)
    @CollectionTable(name = "`personal_trainer_contact`", foreignKey = @ForeignKey(name = "`fk_personal_trainer_contact_gym_id`"))
    @ElementCollection
    private Set<Contact> contacts;

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

    @Size(max = 400)
    @Column(name = "`instagram`", length = 400)
    private String instagram;

    @JsonInclude(value = Include.NON_NULL)
    @CollectionTable(name = "`personal_trainer_gallery_picture`", foreignKey = @ForeignKey(name = "`fk_personal_trainer_gallery_picture_personal_trainer_id`"))
    @ElementCollection
    @Column(name = "`gallery_picture_url`", length = 2500, nullable = false)
    private Set<@Size(max = 2500) String> galleryPicturesUrls;

    @JsonInclude(value = Include.NON_NULL)
    @CollectionTable(name = "`personal_trainer_acting_city`", foreignKey = @ForeignKey(name = "`fk_personal_trainer_acting_city_personal_trainer_id`"))
    @ElementCollection
    @Column(name = "`acting_city`", length = 500, nullable = false)
    private Set<@Size(max = 500) String> actingCities;

    @Size(max = 2000)
    @Column(name = "`biography`", length = 2000)
    private String biography;

    @JsonInclude(value = Include.NON_NULL)
    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "`personal_trainer_sport`", foreignKey = @ForeignKey(name = "`fk_personal_trainer_sport_personal_trainer_id`"), inverseForeignKey = @ForeignKey(name = "`fk_personal_trainer_sport_sport_id`"), inverseJoinColumns = { @JoinColumn(name = "`sport_id`") })
    private Set<Sport> sports;

    public void addUser(User user) {

        this.user = user;
        this.user.setPersonalTrainer(this);
    }

}