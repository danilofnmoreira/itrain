package com.itrain.student.domain;

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
@Entity
@Table(name = "`student`")
public class Student {

    @JsonIgnore
    @Id
    @Column(name = "`id`")
    private Long id;

    @JsonInclude(value = Include.NON_NULL)
    @OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Contact> contacts;

    @JsonInclude(value = Include.NON_NULL)
    @OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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

        contacts.forEach(c -> c.setStudent(this));

        final var studentContacts = Objects.requireNonNullElse(this.getContacts(), new HashSet<Contact>());
        studentContacts.addAll(contacts);

        this.setContacts(studentContacts);

    }

    public void addAddresses(final Set<Address> addresses) {

        addresses.forEach(a -> a.setStudent(this));

        final var studentAddresses = Objects.requireNonNullElse(this.getAddresses(), new HashSet<Address>());
        studentAddresses.addAll(addresses);

        this.setAddresses(studentAddresses);

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
        final Student other = (Student) obj;
        if (id == null) {
            return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}