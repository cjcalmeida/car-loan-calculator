package com.cjcalmeida.carloan.proposal.location;

import com.cjcalmeida.carloan.proposal.domain.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface LocationRepository extends CrudRepository<Location, Integer> {

    Optional<Location> findByCityAndState(String city, String state);
}
