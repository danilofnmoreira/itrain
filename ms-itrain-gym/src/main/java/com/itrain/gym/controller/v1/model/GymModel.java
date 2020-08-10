package com.itrain.gym.controller.v1.model;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
public class GymModel {

    @JsonInclude(value = Include.NON_NULL)
    private Set<@NotNull ContactModel> contacts;

    @JsonInclude(value = Include.NON_NULL)
    private Set<@NotNull AddressModel> addresses;

    @Size(max = 400)
    private String instagram;

    @Size(max = 2000)
    private String biography;

    private Set<@NotNull @Positive Long> sports;

}