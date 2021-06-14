package com.macielgoettms.addresses.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressGeocoding implements Serializable {

    private Double latitude;
    private Double longitude;
}
