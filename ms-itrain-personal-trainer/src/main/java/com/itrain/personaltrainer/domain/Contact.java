package com.itrain.personaltrainer.domain;

import java.util.HashSet;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = SnakeCaseStrategy.class)
@JsonInclude(value = Include.NON_ABSENT)
@Entity(name = "personal-trainer-contact-entity")
@Table(name = "`personal_trainer_contact`")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_personal_trainer_contact_id")
    @SequenceGenerator(name = "sq_personal_trainer_contact_id", sequenceName = "`sq_personal_trainer_contact_id`", allocationSize = 1)
    @Column(name = "`id`")
    private Long id;

    @Size(max = 500)
    @Column(name = "`name`", length = 500)
    private String name;

    @Email
    @Size(max = 500)
    @Column(name = "`email`", length = 500)
    private String email;

    @Size(max = 50)
    @Column(name = "`phone`", length = 50)
    private String phone;

    @JsonProperty(value = "is_whatsapp")
    @Column(name = "`is_whatsapp`")
    private Boolean whatsapp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "`personal_trainer_id`", foreignKey = @ForeignKey(name = "`fk_personal_trainer_contact_personal_trainer_id`"), nullable = false, updatable = false)
    private PersonalTrainer personalTrainer;

    public void fillFrom(final Contact contact) {

        setId(contact.getId());
        setName(contact.getName());
        setEmail(contact.getEmail());
        setPhone(contact.getPhone());
        setWhatsapp(contact.getWhatsapp());
    }

    @Transient
    public boolean isWhatsapp() {

        return BooleanUtils.toBoolean(whatsapp);
    }

    public void addPersonalTrainer(final PersonalTrainer personalTrainer) {

        this.setPersonalTrainer(personalTrainer);
        final var personalTrainerContacts = Objects.requireNonNullElse(this.getPersonalTrainer().getContacts(), new HashSet<Contact>());
        personalTrainerContacts.add(this);
        this.getPersonalTrainer().setContacts(personalTrainerContacts);
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
        final Contact other = (Contact) obj;
        if (id == null) {
            return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}