package com.cjcalmeida.carloan.vehicle.model;

import com.cjcalmeida.carloan.vehicle.domains.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vehicles/brands/{brandId}/models")
class ModelController {

    private ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    @Operation(hidden = true)
    public ResponseEntity create(@PathVariable("brandId") UUID brandId, @RequestBody ModelResource resource){
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(hidden = true)
    public ResponseEntity delete(@PathVariable("brandId") UUID brandId, @PathVariable("id") UUID id){
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PatchMapping("/{id}")
    @Operation(hidden = true)
    public ResponseEntity update(@PathVariable("brandId") UUID brandId, @PathVariable("id") UUID id,
                                 @RequestBody ModelResource resource){
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


    @GetMapping
    @Operation(summary = "List models by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available models", content = {
                    @Content(array = @ArraySchema(uniqueItems = true, minItems = 0, schema =
                    @Schema(implementation = ModelController.ModelResource.class)))
            })
    })
    public ResponseEntity<List<ModelController.ModelResource>> listModelsByBrand(@PathVariable("brandId") UUID brandId){
        return ResponseEntity.ok(
                modelService.list(brandId).stream()
                        .map(this::toResource)
                        .collect(Collectors.toList())
        );
    }

    private ModelController.ModelResource toResource(Model brand) {
        var resource = new ModelResource();
        resource.id = Optional.of(brand.getPublicId());
        resource.name = Optional.of(brand.getName());
        resource.imageUrl = Optional.ofNullable(brand.getImage());
        return resource;
    }

    static class ModelResource {
        public Optional<UUID> id;
        public Optional<String> name;
        public Optional<String> imageUrl;
    }
}
