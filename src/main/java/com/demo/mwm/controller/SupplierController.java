package com.demo.mwm.controller;

import com.demo.mwm.config.Translator;
import com.demo.mwm.service.ISupplierService;
import com.demo.mwm.dto.SupplierDto;
import com.demo.mwm.dto.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/suppliers")
public class SupplierController {

    private final ISupplierService supplierService;

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }


    @Operation(description = "create a new supplier",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Created supplier successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class)))
            })
    /**
     * create a new supplier
     *
     * @param supplierDto An object containing the new supplier information.
     * @return CommonResponse.data: A SupplierDto object representing the created supplier.
     */
    @PostMapping("save")
    public CommonResponse<?> createSupplier(
            @Valid @RequestBody SupplierDto supplierDto
    ) {
        return new CommonResponse<>()
                .success()
                .data(supplierService.createSupplier(supplierDto))
                .responseCode(HttpStatus.CREATED.value())
                .message(Translator.toLocale("success.supplier.create"));
    }


    @Operation(description = "update supplierName,supplierCode,supplierAddress,supplierPhoneNumber,supplierNote",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Update supplier successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class)))
            })
    /**
     * This method allows updating various fields of a supplier entity, including:
     * update supplierName,supplierCode,supplierAddress,supplierPhoneNumber,supplierNote
     *
     * @param id          The ID of the supplier to update.
     * @param supplierDto An object containing the updated supplier information.
     * @return CommonResponse.data: A SupplierDto object representing the updated supplier.
     */
    @PutMapping("/{id}/update")
    public CommonResponse<?> updateSupplier(
            @PathVariable Integer id,
            @Valid @RequestBody SupplierDto supplierDto
    ) {
        return new CommonResponse<>()
                .success()
                .data(supplierService.updateSupplier(id, supplierDto))
                .responseCode(HttpStatus.OK.value())
                .message(Translator.toLocale("success.supplier.update"));
    }

    @Operation(description = "Deletes a supplier by marking it inactive",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Delete supplier successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class)))
            })
    /**
     * Deletes a supplier by marking it inactive.
     * @param id The ID of the supplier to delete.
     * @return CommonResponse
     */
    @DeleteMapping("/{id}/delete")
    public CommonResponse<?> deleteSupplier(
            @PathVariable Integer id
    ) {
        supplierService.deleteSupplier(id);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .message(Translator.toLocale("success.supplier.delete"));
    }

    @Operation(description = "Retrieves the details of a supplier by its ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get supplier successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponse.class)))
            })
    @GetMapping("/{id}")
    public CommonResponse<?> getSupplierById(
            @PathVariable Integer id
    ) {
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .data(supplierService.getSupplierById(id));
    }


    /**
     * get all supplier
     *
     * @return CommonResponse.data: List<SupplierDto>
     */
    @GetMapping("/all")
    public CommonResponse<?> getAllSupplier() {
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .data(supplierService.getAllSupplier());
    }

}
