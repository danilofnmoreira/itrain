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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
@Table(name = "`client_address`")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_client_address_id")
    @SequenceGenerator(name = "sq_client_address_id", sequenceName = "`sq_client_address_id`", allocationSize = 1)
    @Column(name = "`id`")
    private Long id;

    @Size(max = 50)
    @Column(name = "`zip_code`", length = 50)
    private String zipCode;

    @Size(max = 300)
    @Column(name = "`public_place`", length = 300)
    private String publicPlace;

    @Size(max = 300)
    @Column(name = "`complement`", length = 300)
    private String complement;

    @Size(max = 300)
    @Column(name = "`district`", length = 300)
    private String district;

    @Size(max = 300)
    @Column(name = "`city`", length = 300)
    private String city;

    @Size(max = 300)
    @Column(name = "`federal_unit`", length = 300)
    private String federalUnit;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "`client_id`", foreignKey = @ForeignKey(name = "`fk_client_address_client_id`"), nullable = false, updatable = false)
    private Client client;

    public void fillFrom(final Address address) {

        setId(address.getId());
        setZipCode(address.getZipCode());
        setPublicPlace(address.getPublicPlace());
        setComplement(address.getComplement());
        setDistrict(address.getDistrict());
        setCity(address.getCity());
        setFederalUnit(address.getFederalUnit());
    }

    public void addClient(final Client client) {

        this.setClient(client);
        final var clientAddresses = Objects.requireNonNullElse(this.getClient().getAddresses(), new HashSet<Address>());
        clientAddresses.add(this);
        this.getClient().setAddresses(clientAddresses);
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
        final Address other = (Address) obj;
        if (id == null) {
            return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}