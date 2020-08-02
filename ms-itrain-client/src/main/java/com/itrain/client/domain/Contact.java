package com.itrain.client.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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
@Embeddable
public class Contact {

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

    @Transient
    public boolean isWhatsapp() {

        return BooleanUtils.toBoolean(whatsapp);
    }

}