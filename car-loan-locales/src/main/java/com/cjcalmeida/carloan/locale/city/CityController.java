package com.cjcalmeida.carloan.locale.city;

import com.cjcalmeida.carloan.locale.domains.City;
import com.cjcalmeida.carloan.locale.exceptions.StateNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/locales/states/{state_id}")
class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @Operation(summary = "List cities by state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cities", content = {
                    @Content(array = @ArraySchema(uniqueItems = true, minItems = 0, schema =
                        @Schema(implementation = CityResource.class)))
            })
    })
    public ResponseEntity<List<CityResource>> listCitiesByState(@PathVariable("state_id") String stateId) throws StateNotFoundException {
        return ResponseEntity.ok(
                cityService.listCitiesByStateId(stateId).stream()
                    .map(this::toResource)
                    .collect(Collectors.toList())
        );
    }

    private CityResource toResource(City city) {
        CityResource resource = new CityResource();
        resource.id = city.getId();
        resource.name = city.getName();
        return resource;
    }

    static class CityResource {
        public Integer id;
        public String name;
    }
}
