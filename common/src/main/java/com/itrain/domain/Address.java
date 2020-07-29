package com.itrain.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

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
@SuppressWarnings(value = { "serial" })
@Embeddable
public class Address implements Serializable {

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

}