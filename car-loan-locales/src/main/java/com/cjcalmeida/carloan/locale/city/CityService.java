package com.cjcalmeida.carloan.locale.city;

import com.cjcalmeida.carloan.locale.domains.City;
import com.cjcalmeida.carloan.locale.domains.State;
import com.cjcalmeida.carloan.locale.exceptions.StateNotFoundException;
import com.cjcalmeida.carloan.locale.state.StateService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityClient cityClient;
    private final StateService stateService;

    public CityService(CityClient cityClient, StateService stateService) {
        this.cityClient = cityClient;
        this.stateService = stateService;
    }

    public List<City> listCitiesByStateId(String stateAbbreviation) throws StateNotFoundException {
        Optional<State> state = stateService.getState(stateAbbreviation);
        State foundState = state.orElseThrow(StateNotFoundException::new);
        return  Collections.unmodifiableList(cityClient.getCitiesByState(foundState));
    }

}
