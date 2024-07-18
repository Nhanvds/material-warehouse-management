package com.demo.mwm.controller;

import com.demo.mwm.component.Translator;
import com.demo.mwm.service.ISupplierService;
import com.demo.mwm.dto.SupplierDto;
import com.demo.mwm.utils.AESUtils;
import com.demo.mwm.utils.RSAUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/suppliers")
public class SupplierController {

    private final ISupplierService supplierService;
    private final AESUtils aesUtils;
    private final RSAUtils rsaUtils;

    public SupplierController(ISupplierService supplierService, AESUtils aesUtils, RSAUtils rsaUtils) {
        this.supplierService = supplierService;
        this.aesUtils = aesUtils;
        this.rsaUtils = rsaUtils;
    }


    @Operation(description = "Create a new supplier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created supplier successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })
    /**
     * create a new supplier
     * @param supplierDto An object containing the new supplier information.
     * @return CommonResponse.data: A SupplierDto object representing the created supplier.
     */
    @PostMapping("save")
    public ResponseEntity<SupplierDto> createSupplier(
            @Valid @RequestBody SupplierDto supplierDto
    ) {
        return ResponseEntity.ok()
                .body(supplierService.createSupplier(supplierDto));
    }
    @PostMapping("/encrypt/save")
    public ResponseEntity<SupplierDto> createSupplierTestAES(
            @Valid @RequestBody SupplierDto supplierDto
    ) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String supplierJson = new ObjectMapper().writeValueAsString(supplierDto);
        String encryptedSupplier = aesUtils.encrypt(supplierJson);
        return ResponseEntity.ok()
                .body(supplierService.createSupplierAES(encryptedSupplier));
    }


    @Operation(description = "update supplierName,supplierCode,supplierAddress,supplierPhoneNumber,supplierNote",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated supplier successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful,Supplier not found. Return detailed error", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })
    /**
     * This method allows updating various fields of a supplier entity, including:
     * update supplierName,supplierCode,supplierAddress,supplierPhoneNumber,supplierNote
     * @param id          The ID of the supplier to update.
     * @param supplierDto An object containing the updated supplier information.
     * @return CommonResponse.data: A SupplierDto object representing the updated supplier.
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<SupplierDto> updateSupplier(
            @PathVariable Integer id,
            @Valid @RequestBody SupplierDto supplierDto
    ) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDto));
    }


    @Operation(description = "Deletes a supplier by marking it inactive",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated material successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful,Supplier not found. Return detailed error", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),

            })
    /**
     * Deletes a supplier by marking it inactive.
     * @param id The ID of the supplier to delete.
     * @return CommonResponse
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteSupplier(
            @PathVariable Integer id
    ) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(Translator.toLocale("success.supplier.delete"));
    }



    @GetMapping("/encrypt/{id}")
    public ResponseEntity<SupplierDto> getSupplierByIdTestRSA(
            @PathVariable Integer id
    ) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        String encryptedData = rsaUtils.encrypt(String.valueOf(id));

        return ResponseEntity
                .ok()
                .body(supplierService.getSupplierByIdRSA(encryptedData));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(
            @PathVariable Integer id
    ) {
        return ResponseEntity
                .ok()
                .body(supplierService.getSupplierById(id));
    }

    @Operation(description = "Get all supplier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get all supplier successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "500", description = "Server error"),

            })
    /**
     * get all supplier
     *
     * @return CommonResponse.data: List<SupplierDto>
     */
    @GetMapping("/all")
    public ResponseEntity<List<SupplierDto>> getAllSupplier() {
        return ResponseEntity
                .ok()
                .body(supplierService.getAllSupplier());
    }

}
