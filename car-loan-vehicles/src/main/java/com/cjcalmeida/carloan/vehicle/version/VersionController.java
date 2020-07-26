package com.cjcalmeida.carloan.vehicle.version;

import com.cjcalmeida.carloan.vehicle.domains.Version;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vehicles/brands/{brandId}/models/{modelId}/versions")
class VersionController {

    private VersionService versionService;

    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @PostMapping
    @Operation(hidden = true)
    public ResponseEntity create(@PathVariable("brandId") UUID brandId, @PathVariable("modelId") UUID modelId,
                                 @RequestBody VersionResource resource) {
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(hidden = true)
    public ResponseEntity delete(@PathVariable("brandId") UUID brandId, @PathVariable("modelId") UUID modelId,
                                 @PathVariable("id") UUID id) {
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PatchMapping("/{id}")
    @Operation(hidden = true)
    public ResponseEntity update(@PathVariable("brandId") UUID brandId, @PathVariable("modelId") UUID modelId,
                                 @PathVariable("id") UUID id, @RequestBody VersionResource resource) {
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


    @GetMapping
    @Operation(summary = "List versions by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available versions", content = {
                    @Content(array = @ArraySchema(uniqueItems = true, minItems = 0, schema =
                    @Schema(implementation = VersionResource.class)))
            })
    })
    public ResponseEntity<List<VersionResource>> listVersionsByBrand(@PathVariable("brandId") UUID brandId,
                                                                     @PathVariable("modelId") UUID modelId) {
        return ResponseEntity.ok(
                versionService.list(modelId).stream()
                        .map(this::toResource)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detailed vehicle information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All vehicle informations", content = {
                    @Content(array = @ArraySchema(uniqueItems = true, minItems = 0, schema =
                    @Schema(implementation = VehicleResource.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Vehicle not found", content = {
                    @Content(array = @ArraySchema(uniqueItems = true, minItems = 0, schema =
                    @Schema(implementation = VehicleResource.class)))
            })
    })
    public ResponseEntity getVehicleDetails(@PathVariable("brandId") UUID brandId, @PathVariable("modelId") UUID modelId,
                                            @PathVariable("id") UUID id) {
        var version = versionService.get(id);
        if(!version.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(toVehicleResource(version.get()));
    }

    private VersionResource toResource(Version version) {
        var resource = new VersionResource();
        resource.id = Optional.of(version.getPublicId());
        resource.name = Optional.of(version.getName());
        resource.value = Optional.of(version.getValue());
        return resource;
    }

    private VehicleResource toVehicleResource(Version version) {
        //Version
        var vehicle = new VehicleResource();
        vehicle.id = Optional.of(version.getPublicId());
        vehicle.name = Optional.of(version.getName());
        vehicle.value = Optional.of(version.getValue());

        //Model
        var model = version.getModel();
        var modelResource = new VehicleModelResource();
        modelResource.id = Optional.of(model.getPublicId());
        modelResource.name = Optional.of(model.getName());
        modelResource.imageUrl = Optional.of(model.getImage());

        //Brand
        var brand = model.getBrand();
        var brandResource = new VehicleBrandResource();
        brandResource.id = Optional.of(brand.getPublicId());
        brandResource.name = Optional.of(brand.getName());

        modelResource.brand = brandResource;
        vehicle.model = modelResource;

        return vehicle;
    }

    static class VersionResource {
        public Optional<UUID> id;
        public Optional<String> name;
        public Optional<BigDecimal> value;
    }

    static class VehicleResource {
        public Optional<UUID> id;
        public Optional<String> name;
        public Optional<BigDecimal> value;
        public VehicleModelResource model;
    }

    static class VehicleModelResource {
        public Optional<UUID> id;
        public Optional<String> name;
        public Optional<String> imageUrl;
        public VehicleBrandResource brand;
    }

    static class VehicleBrandResource {
        public Optional<UUID> id;
        public Optional<String> name;
    }


}