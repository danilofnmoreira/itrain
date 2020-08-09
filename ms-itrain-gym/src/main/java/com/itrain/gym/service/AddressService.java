package com.itrain.gym.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.gym.domain.Address;
import com.itrain.gym.repository.AddressRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "gym-address-service")
@RequiredArgsConstructor
public class AddressService {

    private final GymService gymService;
    private final AddressRepository addressRepository;

    @Transactional
    public Set<Address> add(final Long gymId, final Set<Address> addresses) {

        final var gym = gymService.findById(gymId);

        addresses.forEach(a -> {

            a.setId(null);

            a.addGym(gym);

        });

        addressRepository.saveAll(addresses);

        gymService.save(gym);

        return addresses;
    }

    @Transactional
    public Set<Address> edit(final Long gymId, final Set<Address> addresses) {

        final var gym = gymService.findById(gymId);

        final var currentAddresses = gym.getAddresses();

        addresses.retainAll(currentAddresses);

        if (addresses.isEmpty()) {

            return Collections.emptySet();
        }

        addresses.forEach(a -> currentAddresses
            .stream()
            .filter(a::equals)
            .forEach(ca -> ca.fillFrom(a)));

        gymService.save(gym);

        return addresses;

    }

    @Transactional
	public Set<Address> delete(final Long gymId, final Set<Long> addressIds) {

        final var gym = gymService.findById(gymId);

        final var currentAddresses = gym.getAddresses();

        final var toDelete = currentAddresses
            .stream()
            .filter(c -> addressIds.contains(c.getId()))
            .collect(Collectors.toSet());

        if (toDelete.isEmpty()) {

            return Collections.emptySet();
        }

        addressRepository.deleteAll(toDelete);

        currentAddresses.removeAll(toDelete);

        gymService.save(gym);

        return toDelete;
	}

	public Set<Address> getAll(Long gymId) {

        final var gym = gymService.findById(gymId);

        return gym.getAddresses();
	}

}
