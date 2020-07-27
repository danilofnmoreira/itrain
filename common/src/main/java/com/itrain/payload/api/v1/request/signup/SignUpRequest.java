package com.itrain.payload.api.v1.request.signup;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    @Size(max = 310)
    private String email;

    @ApiModelProperty(example = "fulano")
    @NotBlank
    @Size(max = 500)
    private String name;

}