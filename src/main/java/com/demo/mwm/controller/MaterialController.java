package com.demo.mwm.controller;

import com.demo.mwm.component.Translator;
import com.demo.mwm.dto.PageDto;

import com.demo.mwm.service.IMaterialService;
import com.demo.mwm.dto.MaterialDto;

import com.demo.mwm.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/materials")
public class MaterialController {

    private final IMaterialService materialService;

    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }


    @Operation(description = "create a new Material",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created material successfully. Return the ID of the newly created material entity."),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful, Supplier not found. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),

            })

    /**
     * create a new Material
     *
     * @param materialDto An object containing the new material information.
     * @return The ID of the newly created material entity.
     */
    @PostMapping("/save")
    public ResponseEntity<MaterialDto> createMaterial(
            @Parameter(description = "An object containing the new material information.")
            @Valid @RequestBody MaterialDto materialDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(materialService.createMaterial(materialDto));
    }


    /**
     * This method allows updating various fields of a material entity, including:
     * - materialCode
     * - materialName
     * - materialPrice
     * - materialQuantity
     * - materialNote
     * - supplierId
     *
     * @param id          The ID of the material to update.
     * @param materialDto An object containing the updated material information.
     * @return String: message updated successfully
     */
    @Operation(description = "Update a material, update materialCode, materialName, materialPrice, materialQuantity, materialNote, supplierId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated material successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful,Material or Supplier not found. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })

    @PutMapping("/{id}/update")
    public ResponseEntity<MaterialDto> updateMaterial(
            @Parameter(description = "The ID of the material to update")
            @PathVariable Integer id,
            @Parameter(description = "An object containing the updated material information")
            @Valid @RequestBody MaterialDto materialDto) {
        return ResponseEntity
                .ok()
                .body(materialService.updateMaterial(id, materialDto));

    }

    /**
     * Deletes a material by marking it inactive.
     *
     * @param id The ID of the material to delete.
     * @return CommonResponse
     */
    @Operation(description = "Delete a material by marking it inactive",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delete material successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful, Material not found Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteMaterial(
            @Parameter(description = "The ID of the material to delete")
            @PathVariable Integer id
    ) {
        materialService.deleteMaterial(id);
        return ResponseEntity.ok(Translator.toLocale("success.material.delete"));
    }


    /**
     * Retrieves the details of a material entity by its ID.
     * @param id The ID of the material entity to retrieve.
     * @return  A MaterialDto object containing the details of the material and its associated supplier.
     */
    @Operation(description = "Retrieves the details of a material entity by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get detail material successfully."),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful, Material not found. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })
    @GetMapping("/{id}/detail")
    public ResponseEntity<MaterialDto> getDetailMaterial(
            @Parameter(description = "The ID of the material to retrieve")
            @PathVariable Integer id
    ) {
        return ResponseEntity
                .ok()
                .body(materialService.getDetailMaterialById(id));
    }


    @Operation(description = "Retrieves a paginated list of materials. If page and size are not provided, returns all materials.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })
    /**
     * @param page          :Page number for pagination, default is 0. If 0, returns all materials.
     * @param size          :Page size for pagination, default is 0. If 0, returns all materials.
     * @param sortProperty: Property to sort by, optional.
     * @param sortOrder:    Order of sorting, either 'asc' or 'desc', optional.
     * @param materialName: Filter materials by name, optional.
     * @param materialCode: Filter materials by code, optional.
     * @return PageDto < MaterialDto> a paginated list of materials. If page and size are not provided, returns all materials.
     */
    @GetMapping("/get-list")
    public ResponseEntity<PageDto<MaterialDto>> getMaterialList(
            @Parameter(description = "Page number for pagination, default is 0. If 0, returns all materials.")
            @RequestParam(defaultValue = Constants.Paging.PAGE_NUMBER_DEFAULT) Integer page,
            @Parameter(description = "Page size for pagination, default is 0. If 0, returns all materials.")
            @RequestParam(defaultValue = Constants.Paging.PAGE_SIZE_DEFAULT) Integer size,
            @Parameter(description = "Property to sort by, optional.")
            @RequestParam(required = false) String sortProperty,
            @Parameter(description = "Order of sorting, either 'asc' or 'desc', optional.")
            @RequestParam(required = false) String sortOrder,
            @Parameter(description = "Filter materials by name, optional.")
            @RequestParam(required = false) String materialName,
            @Parameter(description = "Filter materials by code, optional.")
            @RequestParam(required = false) String materialCode
    ) {
        return ResponseEntity.ok()
                .body(materialService.getMaterialList(page, size, sortProperty, sortOrder, materialName, materialCode));
    }

}
