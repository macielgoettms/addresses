package com.macielgoettms.addresses.application;

import com.macielgoettms.addresses.domain.Address;
import com.macielgoettms.addresses.domain.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @PostMapping
    public ResponseEntity<?> createAddress(@Valid @RequestBody AddressRequest content) {
        Address address = addressMapper.createAddress(content);
        address = addressService.createAddress(address);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(address.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable UUID id, @Valid @RequestBody AddressRequest content) {
        Optional<Address> address = addressService.findAddressById(id);

        if (!address.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        addressMapper.updateAddress(content, address.get());
        addressService.updateAddress(address.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable UUID id) {
        Optional<Address> address = addressService.findAddressById(id);
        if (!address.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        addressService.deleteAddress(address.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findAddressById(@PathVariable UUID id) {
        return addressService
                .findAddressById(id)
                .map(address -> ResponseEntity.ok(addressMapper.createAddressResponse(address)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAllAddresses() {
        List<AddressResponse> responseList = addressService
                .findAllAddress()
                .stream()
                .map(addressMapper::createAddressResponse)
                .collect(Collectors.toList());
        if (responseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AddressResponse>> findAddressesByStreetName(@RequestParam(value = "streetName") String streetName) {
        List<AddressResponse> responseList = addressService
                .findAddressesByStreetName(streetName)
                .stream()
                .map(addressMapper::createAddressResponse)
                .collect(Collectors.toList());
        if (responseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseList);
    }

}
