package com.itrain.client.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itrain.common.converter.LocalDateTimeToStringConverter;
import com.itrain.common.converter.StringToLocalDatetimeConverter;

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
@Table(name = "`client`")
public class Client {

    @JsonIgnore
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "`id`")
    private Long id;

    @JsonInclude(value = Include.NON_NULL)
    @OneToMany(mappedBy = "client", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Contact> contacts;

    @JsonInclude(value = Include.NON_NULL)
    @OneToMany(mappedBy = "client", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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

    public void addContacts(final Set<Contact> contacts) {

        contacts.forEach(c -> c.setClient(this));

        final var clientContacts = Objects.requireNonNullElse(this.getContacts(), new HashSet<Contact>());
        clientContacts.addAll(contacts);

        this.setContacts(clientContacts);

    }

    public void addAddresses(final Set<Address> addresses) {

        addresses.forEach(a -> a.setClient(this));

        final var clientAddresses = Objects.requireNonNullElse(this.getAddresses(), new HashSet<Address>());
        clientAddresses.addAll(addresses);

        this.setAddresses(clientAddresses);

    }

}