package com.cjcalmeida.carloan.proposal.vehicle;

import com.cjcalmeida.carloan.proposal.domain.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public void saveVehicle(Vehicle vehicle) {
        log.trace("Saving data to DB");
        Optional<Vehicle> data = repository.findById(vehicle.getId());
        var vehicleDomain = data.orElse(vehicle);
        vehicleDomain.setMarketValue(vehicle.getMarketValue());
        repository.save(vehicleDomain);
        log.trace("Data saved");
    }

    public Optional<Vehicle> getVehicle(UUID vehicleId) {
        log.trace("Geting data from DB");
        return repository.findById(vehicleId);
    }

}
