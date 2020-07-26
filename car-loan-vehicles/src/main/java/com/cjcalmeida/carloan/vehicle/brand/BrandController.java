package com.cjcalmeida.carloan.vehicle.brand;

import com.cjcalmeida.carloan.vehicle.domains.Brand;
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
@RequestMapping("/v1/vehicles/brands")
class BrandController {

    private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @Operation(hidden = true)
    public ResponseEntity create(BrandResource resource){
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(hidden = true)
    public ResponseEntity delete(@PathVariable("id") UUID id){
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PatchMapping("/{id}")
    @Operation(hidden = true)
    public ResponseEntity update(@PathVariable("id") UUID id, @RequestBody BrandResource resource){
        //TODO Not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


    @GetMapping
    @Operation(summary = "List all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of available brands", content = {
                    @Content(array = @ArraySchema(uniqueItems = true, minItems = 0, schema =
                        @Schema(implementation = BrandResource.class)))
            })
    })
    public ResponseEntity<List<BrandResource>> list(){
        return ResponseEntity.ok(
                brandService.list().stream()
                    .map(this::toResource)
                    .collect(Collectors.toList())
        );
    }

    private BrandResource toResource(Brand brand) {
        var resource = new BrandResource();
        resource.id = Optional.of(brand.getPublicId());
        resource.name = Optional.of(brand.getName());
        resource.shortDescription = Optional.ofNullable(brand.getShortDescription());
        return resource;
    }

    static class BrandResource {
        public Optional<UUID> id;
        public Optional<String> name;
        public Optional<String> shortDescription;
    }
}
