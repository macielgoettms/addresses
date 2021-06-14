package com.macielgoettms.addresses.domain;

public interface AddressFinder {

    AddressGeocoding findGeocodingByDescription(String addressDescription);
}
