package com.demo.mwm.controller;

import com.demo.mwm.config.Translator;
import com.demo.mwm.service.IMaterialService;
import com.demo.mwm.dto.MaterialDto;
import com.demo.mwm.dto.response.CommonResponse;
import com.demo.mwm.dto.response.PageResponse;
import com.demo.mwm.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
                    @ApiResponse(responseCode = "200",
                            description = "Created material successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class,
                                            description = "Has data: A MaterialDto object representing the created material.")))
            })
    /**
     * create a new Material
     *
     * @param materialDto An object containing the new material information.
     * @return CommonResponse has data: A MaterialDto object representing the created material.
     */
    @PostMapping("/save")
    public CommonResponse<?> createMaterial(
            @Parameter(description = "An object containing the new material information.")
            @Valid @RequestBody MaterialDto materialDto
            ) {
        return new CommonResponse<>()
                .success()
                .data(materialService.createMaterial(materialDto))
                .responseCode(HttpStatus.CREATED.value())
                .message(Translator.toLocale("success.material.create"));
    }

    @Operation(description = "Update a material, update materialCode, materialName, materialPrice, materialQuantity, materialNote, supplierId",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Delete material successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class,
                                            description = "Has data: A MaterialDto object representing the updated material.")))
            })
    /**
     * update a material, update materialCode,materialName,materialPrice,materialQuantity,materialNote,supplierId
     *
     * @param id          The ID of the material to update.
     * @param materialDto An object containing the updated material information.
     * @return CommonResponse
     */
    @PutMapping("/{id}/update")
    public CommonResponse<?> updateMaterial(
            @Parameter(description = "The ID of the material to update")
            @PathVariable Integer id,
            @Parameter(description = "An object containing the updated material information")
            @Valid @RequestBody MaterialDto materialDto,
            HttpServletRequest request
    ) {
        return new CommonResponse<>()
                .success()
                .data(materialService.updateMaterial(id, materialDto))
                .responseCode(HttpStatus.OK.value())
                .message(Translator.toLocale("success.material.update"));

    }


    @Operation(description = "Deletes a material by marking it inactive",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Delete material successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class)))
            })
    /**
     * Deletes a material by marking it inactive.
     * @param id The ID of the material to delete.
     * @return CommonResponse
     */
    @DeleteMapping("/{id}/delete")
    public CommonResponse<?> deleteMaterial(
            @Parameter(description = "The ID of the material to delete")
            @PathVariable Integer id
    ) {
        materialService.deleteMaterial(id);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .message(Translator.toLocale("success.material.delete"));
    }


    @Operation(description = "Retrieves the details of a material by its ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get material successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class,
                                            description = "has data: A MaterialDto object containing the details of the material and its associated supplier.")))
            })
    @GetMapping("/{id}/detail")
    public CommonResponse<?> getDetailMaterial(
            @Parameter(description = "The ID of the material to retrieve")
            @PathVariable Integer id
    ) {
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .data(materialService.getDetailMaterialById(id));
    }

    @Operation(summary = "Retrieves a paginated list of materials.",
            description = "Retrieves a paginated list of materials. If page and size are not provided, returns all materials.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get material page successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PageResponse.class),
                                    examples = @ExampleObject(name = "",
                                            description = "success=true when Get material page successfully",
                                            value = """
                                                    {
                                                        "success": true,
                                                        "responseCode": 200,
                                                        "data": [
                                                         {
                                                                     "id": 1,
                                                                     "materialCode": "VT001",
                                                                     "materialName": "Vật tư 001",
                                                                     "materialPrice": 20000.0,
                                                                     "materialQuantity": 5,
                                                                     "materialNote": "Sản phẩm mới",
                                                                     "supplierId": null,
                                                                     "supplier": null,
                                                                     "updatedAt": "2024-07-02T17:16:27.242944Z",
                                                                     "createdAt": "2024-07-02T16:54:06.684231Z"
                                                                 }
                                                        ],
                                                        "dataCount": 1
                                                    }
                                                    """)))
            })
    @GetMapping("/get-list")
    public PageResponse<?> getMaterialList(
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
        return materialService.getMaterialList(page, size, sortProperty, sortOrder, materialName, materialCode);
    }

}
