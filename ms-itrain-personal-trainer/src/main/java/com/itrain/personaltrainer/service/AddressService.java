package com.itrain.personaltrainer.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.personaltrainer.domain.Address;
import com.itrain.personaltrainer.repository.AddressRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "personal-trainer-address-service")
@RequiredArgsConstructor
public class AddressService {

    private final PersonalTrainerService personalTrainerService;
    private final AddressRepository addressRepository;

    @Transactional
    public Set<Address> add(final Long personalTrainerId, final Set<Address> addresses) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        addresses.forEach(a -> {

            a.setId(null);

            a.addPersonalTrainer(personalTrainer);

        });

        addressRepository.saveAll(addresses);

        personalTrainerService.save(personalTrainer);

        return addresses;
    }

    @Transactional
    public Set<Address> edit(final Long personalTrainerId, final Set<Address> addresses) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        final var currentAddresses = personalTrainer.getAddresses();

        addresses.retainAll(currentAddresses);

        if (addresses.isEmpty()) {

            return Collections.emptySet();
        }

        addresses.forEach(a -> currentAddresses
            .stream()
            .filter(a::equals)
            .forEach(ca -> ca.fillFrom(a)));

        personalTrainerService.save(personalTrainer);

        return addresses;

    }

    @Transactional
	public Set<Address> delete(final Long personalTrainerId, final Set<Long> addressIds) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        final var currentAddresses = personalTrainer.getAddresses();

        final var toDelete = currentAddresses
            .stream()
            .filter(c -> addressIds.contains(c.getId()))
            .collect(Collectors.toSet());

        if (toDelete.isEmpty()) {

            return Collections.emptySet();
        }

        addressRepository.deleteAll(toDelete);

        currentAddresses.removeAll(toDelete);

        personalTrainerService.save(personalTrainer);

        return toDelete;
	}

	public Set<Address> getAll(Long personalTrainerId) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        return personalTrainer.getAddresses();
	}

}
