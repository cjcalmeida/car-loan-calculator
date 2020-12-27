package com.cjcalmeida.carloan.proposal.simulation;

import com.cjcalmeida.carloan.proposal.domain.Simulation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SimulationRepository extends CrudRepository<Simulation, Integer> {

}
