package com.itrain.personaltrainer.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(value = { AuditingEntityListener.class })
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = SnakeCaseStrategy.class)
@JsonInclude(value = Include.NON_ABSENT)
@Entity(name = "personal-trainer-entity")
@Table(name = "`personal_trainer`")
public class PersonalTrainer {

    @JsonIgnore
    @Id
    @Column(name = "`id`")
    private Long id;

    @JsonInclude(value = Include.NON_NULL)
    @OneToMany(mappedBy = "personalTrainer", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Contact> contacts;

    @JsonInclude(value = Include.NON_NULL)
    @OneToMany(mappedBy = "personalTrainer", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Address> addresses;

    @NotNull
    @PastOrPresent
    @Column(name = "`registered_at`", nullable = false, updatable = false)
    @JsonIgnore
    @CreatedDate
    private LocalDateTime registeredAt;

    @NotNull
    @PastOrPresent
    @Column(name = "`updated_at`", nullable = false)
    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @JsonInclude(value = Include.NON_NULL)
    @CollectionTable(name = "`personal_trainer_gallery_picture`", foreignKey = @ForeignKey(name = "`fk_personal_trainer_gallery_picture_personal_trainer_id`"))
    @ElementCollection
    @Column(name = "`gallery_picture_url`", length = 2500, nullable = false)
    private Set<@NotBlank @Size(max = 2500) String> galleryPicturesUrls;

    @Size(max = 400)
    @Column(name = "`instagram`", length = 400)
    private String instagram;

    @Size(max = 2000)
    @Column(name = "`biography`", length = 2000)
    private String biography;

    @JsonIgnore
    @Size(max = 1000)
    @Column(name = "`sports`", length = 1000)
    private String sports;

    public void addContacts(final Set<Contact> contacts) {

        contacts.forEach(c -> c.setPersonalTrainer(this));

        final var personalTrainerContacts = Objects.requireNonNullElse(this.getContacts(), new HashSet<Contact>());
        personalTrainerContacts.addAll(contacts);

        this.setContacts(personalTrainerContacts);

    }

    public void addAddresses(final Set<Address> addresses) {

        addresses.forEach(a -> a.setPersonalTrainer(this));

        final var personalTrainerAddresses = Objects.requireNonNullElse(this.getAddresses(), new HashSet<Address>());
        personalTrainerAddresses.addAll(addresses);

        this.setAddresses(personalTrainerAddresses);

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PersonalTrainer other = (PersonalTrainer) obj;
        if (id == null) {
            return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @JsonProperty(value = "sports")
    @Transient
    public Set<Long> getParsedSports() {

        final var currentSports = StringUtils.defaultString(getSports());

        return StringUtils.isBlank(currentSports) ?
            new HashSet<>() :
            Arrays
                .asList(currentSports.split(","))
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toCollection(HashSet<Long>::new));
    }

    public void addParsedSports(final Set<Long> sportsId) {

        if(sportsId == null || sportsId.isEmpty()) {
            return;
        }

        final var strSet = sportsId
            .stream()
            .map(String::valueOf)
            .collect(Collectors.toSet());

        final var joined = String.join(",", strSet);

        final var currentSports = StringUtils.defaultString(getSports());

        setSports(StringUtils.isBlank(currentSports) ? currentSports.concat(joined) : currentSports.concat("," + joined));
    }

}