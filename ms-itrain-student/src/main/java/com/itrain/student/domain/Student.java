package com.itrain.student.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
@Entity(name = "student-entity")
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