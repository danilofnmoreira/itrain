package com.itrain.client.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.client.domain.Address;
import com.itrain.client.repository.AddressRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final ClientService clientService;
    private final AddressRepository addressRepository;

    @Transactional
    public Set<Address> add(final Long clientId, final Set<Address> addresses) {

        final var client = clientService.findById(clientId);

        addresses.forEach(a -> {

            a.setId(null);

            a.addClient(client);

        });

        addressRepository.saveAll(addresses);

        clientService.save(client);

        return addresses;
    }

    @Transactional
    public Set<Address> edit(final Long clientId, final Set<Address> addresses) {

        final var client = clientService.findById(clientId);

        final var currentAddresses = client.getAddresses();

        addresses.retainAll(currentAddresses);

        if (addresses.isEmpty()) {

            return Collections.emptySet();
        }

        addresses.forEach(a -> currentAddresses
            .stream()
            .filter(a::equals)
            .forEach(ca -> ca.fillFrom(a)));

        clientService.save(client);

        return addresses;

    }

    @Transactional
	public Set<Address> delete(final Long clientId, final Set<Long> addressIds) {

        final var client = clientService.findById(clientId);

        final var currentAddresses = client.getAddresses();

        final var toDelete = currentAddresses
            .stream()
            .filter(c -> addressIds.contains(c.getId()))
            .collect(Collectors.toSet());

        if (toDelete.isEmpty()) {

            return Collections.emptySet();
        }

        addressRepository.deleteAll(toDelete);

        currentAddresses.removeAll(toDelete);

        clientService.save(client);

        return toDelete;
	}

	public Set<Address> getAll(Long clientId) {

        final var client = clientService.findById(clientId);

        return client.getAddresses();
	}

}
