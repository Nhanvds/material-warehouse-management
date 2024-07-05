package com.demo.mwm.web.rest;

import com.demo.mwm.service.IMaterialService;
import com.demo.mwm.service.dto.MaterialDto;
import com.demo.mwm.service.dto.response.CommonResponse;
import com.demo.mwm.service.dto.response.PageResponse;
import com.demo.mwm.service.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping("/save")
    public CommonResponse<?> createMaterial(
            @Valid @RequestBody MaterialDto materialDto) {
        materialService.createMaterial(materialDto);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.CREATED.value())
                .message("Create material successfully");
    }

    @PutMapping("/{id}/update")
    public CommonResponse<?> updateMaterial(
            @PathVariable Integer id,
            @Valid @RequestBody MaterialDto materialDto
    ) {
        materialService.updateMaterial(id, materialDto);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .message("Update material successfully");
    }

    @DeleteMapping("/{id}/delete")
    public CommonResponse<?> deleteMaterial(
            @PathVariable Integer id
    ) {
        materialService.deleteMaterial(id);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .message("Delete material successfully");
    }

    @GetMapping("/{id}/detail")
    public CommonResponse<?> getDetailMaterial(
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
