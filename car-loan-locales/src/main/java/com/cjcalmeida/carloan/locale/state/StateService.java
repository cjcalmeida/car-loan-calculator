package com.cjcalmeida.carloan.locale.state;

import com.cjcalmeida.carloan.locale.domains.State;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    private final StateClient stateClient;

    public StateService(StateClient stateClient) {
        this.stateClient = stateClient;
    }

    public List<State> listAllStates() {
        return Collections.unmodifiableList(stateClient.listAllStates());
    }

    public Optional<State> getState(String abbreviation) {
        return stateClient.getState(abbreviation);
    }
}
