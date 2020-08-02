package com.itrain.mapper;

import com.itrain.domain.Contact;
import com.itrain.payload.api.v1.request.signup.SignUpRequest;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactMapper {

    public static Contact createFrom(SignUpRequest request) {

        if(StringUtils.isBlank(request.getPhone())) {
            return Contact.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build(); 
        }

        return Contact.builder()
            .email(request.getEmail())
            .phone(request.getPhone())
            .whatsapp(request.getWhatsapp())
            .name(request.getName())
            .build();
    }

}