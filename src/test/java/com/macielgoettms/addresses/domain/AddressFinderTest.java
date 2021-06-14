package com.macielgoettms.addresses.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static com.macielgoettms.addresses.domain.AddressFinderTestFixture.newMockedAddressGeocoding;

public class AddressFinderTest {

    private  AddressFinder subject;

    @BeforeEach
    public void setUp() {
        subject = Mockito.mock(AddressFinder.class);

    }

    @Test
    public void shouldReturnGeocodingByDescription() {
        AddressGeocoding geocoding = newMockedAddressGeocoding();

        when(subject.findGeocodingByDescription(anyString())).thenReturn(geocoding);

        AddressGeocoding geocodingByDescription = subject.findGeocodingByDescription(anyString());

        Assertions.assertNotNull(geocodingByDescription);
        Assertions.assertEquals(geocoding.getLatitude(), geocodingByDescription.getLatitude());
        Assertions.assertEquals(geocoding.getLongitude(), geocodingByDescription.getLongitude());

        verify(subject).findGeocodingByDescription(anyString());
        verifyNoMoreInteractions(subject);
    }
}
