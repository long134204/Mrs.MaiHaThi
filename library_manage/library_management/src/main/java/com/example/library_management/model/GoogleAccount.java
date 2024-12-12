package com.example.library_management.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GoogleAccount {
    private String id;
    private String name;
    private String email;
    private String firstName;
    private String givenName;
    private String familyName;
    private String picture;
    private Boolean verifiedEmail;

    @Override
    public String toString() {
        return "GoogleAccount{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", picture='" + picture + '\'' +
                ", verifiedEmail=" + verifiedEmail +
                '}';
    }
}
