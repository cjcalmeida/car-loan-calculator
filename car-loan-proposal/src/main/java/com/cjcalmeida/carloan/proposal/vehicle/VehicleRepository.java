package com.cjcalmeida.carloan.proposal.vehicle;

import com.cjcalmeida.carloan.proposal.domain.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface VehicleRepository extends CrudRepository<Vehicle, UUID> {

}
