package com.cjcalmeida.carloan.proposal.location;

import com.cjcalmeida.carloan.proposal.domain.Location;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public Optional<Location> findLocation(String city, String state) {
        return  repository.findByCityAndState(city, state);
    }
}
