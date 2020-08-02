package com.itrain.payload.api.v1;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itrain.domain.Address;
import com.itrain.domain.Contact;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = SnakeCaseStrategy.class)
public class RegistrationData {

    private Set<Contact> contacts;

    private Set<Address> addresses;

    private MultipartFile profilePicture;

    @Size(max = 400)
    private String instagram;

    private Set<MultipartFile> galleryPictures;

    private Set<@Size(max = 500) String> actingCities;

    @Size(max = 2000)
    private String biography;

    private Set<@NotBlank @Size(max = 400) String> sports;

}