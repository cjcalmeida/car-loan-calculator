package com.cjcalmeida.carloan.locale.city;

import com.cjcalmeida.carloan.locale.domains.City;
import com.cjcalmeida.carloan.locale.domains.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class CityClient {

    private final RestTemplate apiClient;
    private final Logger log = LoggerFactory.getLogger(CityClient.class);

    public CityClient(RestTemplate apiClient) {
        this.apiClient = apiClient;
    }

    @Cacheable(cacheNames = "cities_by_state_abbr", key = "#state.abbreviation.toLowerCase()")
    public List<City> getCitiesByState(State state) {
        log.debug("Calling IBGE API");
        Optional<CityResource[]> cities = Optional.ofNullable(apiClient
                .getForObject("/v1/localidades/estados/{stateId}/microrregioes", CityResource[].class, state.getId()));

        return Arrays.stream(cities.orElse(new CityResource[0]))
                .map(cityResource -> new City(cityResource.id, cityResource.nome))
                .collect(Collectors.toList());
    }

    static class CityResource {
        public Integer id;
        public String nome;
    }



}
