package com.itrain.client.domain;

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
@Entity
@Table(name = "`client_contact`")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_client_contact_id")
    @SequenceGenerator(name = "sq_client_contact_id", sequenceName = "`sq_client_contact_id`", allocationSize = 1)
    @EqualsAndHashCode.Include
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
    @JoinColumn(name = "`client_id`", foreignKey = @ForeignKey(name = "`fk_client_contact_client_id`"))
    private Client client;

    @Transient
    public boolean isWhatsapp() {

        return BooleanUtils.toBoolean(whatsapp);
    }

    public void addClient(final Client client) {

        this.setClient(client);
        final var clientContacts = Objects.requireNonNullElse(this.getClient().getContacts(), new HashSet<Contact>());
        clientContacts.add(this);
        this.getClient().setContacts(clientContacts);
    }

}