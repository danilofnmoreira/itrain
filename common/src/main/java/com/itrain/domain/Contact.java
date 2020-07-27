package com.itrain.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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
@Embeddable
public class Contact implements Serializable {

    @Size(max = 500)
    @Column(name = "`name`", length = 500)
    private String name;

    @Email
    @Size(max = 500)
    @Column(name = "`email`", length = 500)
    private String email;

    @Size(max = 30)
    @Column(name = "`phone`", length = 30)
    private String phone;

    @Column(name = "`is_whatsapp`")
    private boolean whatsapp;

}