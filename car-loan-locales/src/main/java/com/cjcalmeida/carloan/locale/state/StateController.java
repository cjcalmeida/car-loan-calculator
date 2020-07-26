package com.cjcalmeida.carloan.locale.state;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/locales/states")
class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }


    @GetMapping
    @Operation(summary = "List all available states")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available states", content = {
                    @Content(array = @ArraySchema(uniqueItems = true, minItems = 0, schema =
                    @Schema(implementation = StateResource.class)))
            })
    })
    public ResponseEntity<List<StateResource>> listAllStates(){
        return ResponseEntity.ok(
                stateService.listAllStates().stream()
                        .map(state -> new StateResource(state.getAbbreviation()))
                        .collect(Collectors.toList())
        );
    }

    static class StateResource {
        public String abbreviation;

        public StateResource(String abbreviation) {
            this.abbreviation = abbreviation;
        }
    }
}
