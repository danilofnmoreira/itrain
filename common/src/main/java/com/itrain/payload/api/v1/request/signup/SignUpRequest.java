package com.itrain.payload.api.v1.request.signup;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itrain.payload.api.v1.UserCredentials;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = SnakeCaseStrategy.class)
public class SignUpRequest {

    @Valid
    private UserCredentials credentials;

    @ApiModelProperty(example = "email@email.com")
    @Email
    @Size(max = 500)
    private String email;

    @ApiModelProperty(example = "fulano")
    @Size(max = 500)
    private String name;

    @ApiModelProperty(example = "55 11 94100-0172")
    @Size(max = 30)
    private String phone;

    @ApiModelProperty(example = "true")
    private boolean whatsapp;

    @NotNull
    private UserType userType;

    public enum UserType {
        CLIENT, GYM, PERSONAL_TRAINER
    }

}