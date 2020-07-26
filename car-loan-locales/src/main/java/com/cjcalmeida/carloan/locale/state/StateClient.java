package com.cjcalmeida.carloan.locale.state;

import com.cjcalmeida.carloan.locale.domains.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class StateClient  {

    private final RestTemplate apiClient;
    private final Logger log = LoggerFactory.getLogger(StateClient.class);

    public StateClient(RestTemplate apiClient) {
        this.apiClient = apiClient;
    }

    @Cacheable(cacheNames = "states", key = "'all'")
    public List<State> listAllStates(){
        log.debug("Calling IBGE Api");
        Optional<StateIBGEResource[]> states = Optional.ofNullable(apiClient
                .getForObject("/v1/localidades/estados", StateIBGEResource[].class));

        return Arrays.stream(states.orElse(new StateIBGEResource[0]))
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<State> getState(String abbreviation){
        log.debug("Calling IBGE API and extracting State by Id");
        return listAllStates().stream()
                .filter(resource -> abbreviation.equalsIgnoreCase(resource.getAbbreviation()))
                .findFirst();
    }

    private State toDomain(StateIBGEResource resource){
        return new State(resource.id, resource.sigla);
    }

    static class StateIBGEResource {
        public Integer id;
        public String sigla;
    }
}
