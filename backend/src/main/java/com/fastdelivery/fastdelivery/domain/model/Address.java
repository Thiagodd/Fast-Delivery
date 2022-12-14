package com.fastdelivery.fastdelivery.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {

    @Column(name = "address_zip_code")
    private String ZipCode;

    @Column(name = "address_public_place")
    private String PublicPlace;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String Complement;

    @Column(name = "address_district")
    private String district;

    @Column(name = "address_city")
    private String city;
}
