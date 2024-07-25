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
@RequestMapping("/suppliers")
public class SupplierController {

    private final ISupplierService supplierService;
    private final AESUtils aesUtils;
    private final RSAUtils rsaUtils;

    public SupplierController(ISupplierService supplierService, AESUtils aesUtils, RSAUtils rsaUtils) {
        this.supplierService = supplierService;
        this.aesUtils = aesUtils;
        this.rsaUtils = rsaUtils;
    }


    /**
     * create a new supplier
     * @param supplierDto An object containing the new supplier information.
     * @return A SupplierDto object representing the created supplier.
     */
    @Operation(description = "Create a new supplier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created supplier successfully. A SupplierDto object representing the created supplier."),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. The request lacks valid authentication credentials."),
                    @ApiResponse(responseCode = "403", description = "Forbidden. The server understood the request but refuses to authorize it.")
            })
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

    /**
     * This method allows updating various fields of a supplier entity, including:
     * update supplierName,supplierCode,supplierAddress,supplierPhoneNumber,supplierNote
     * @param id          The ID of the supplier to update.
     * @param supplierDto An object containing the updated supplier information.
     * @return A SupplierDto object representing the updated supplier.
     */
    @Operation(description = "update supplierName,supplierCode,supplierAddress,supplierPhoneNumber,supplierNote",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated supplier successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful,Supplier not found. Return detailed error", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. The request lacks valid authentication credentials."),
                    @ApiResponse(responseCode = "403", description = "Forbidden. The server understood the request but refuses to authorize it.")
            })
    @PutMapping("/{id}/update")
    public ResponseEntity<SupplierDto> updateSupplier(
            @PathVariable Integer id,
            @Valid @RequestBody SupplierDto supplierDto
    ) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDto));
    }


    /**
     * Deletes a supplier by marking it inactive.
     * @param id The ID of the supplier to delete.
     * @return Message
     */
    @Operation(description = "Deletes a supplier by marking it inactive",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated material successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful,Supplier not found. Return detailed error", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. The request lacks valid authentication credentials."),
                    @ApiResponse(responseCode = "403", description = "Forbidden. The server understood the request but refuses to authorize it.")

            })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteSupplier(
            @PathVariable Integer id
    ) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(Translator.toLocale("success.supplier.delete"));
    }



    @Operation(description = "Retrieves the details of a supplier entity by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get detail supplier successfully."),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. The request lacks valid authentication credentials."),
                    @ApiResponse(responseCode = "403", description = "Forbidden. The server understood the request but refuses to authorize it."),
                    @ApiResponse(responseCode = "404", description = "Unsuccessful, supplier not found. Return detailed error",content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(responseCode = "500", description = "Server error"),
            })
    @GetMapping("/{id}/detail")
    public ResponseEntity<SupplierDto> getSupplierById(
            @PathVariable Integer id
    ) {
        return ResponseEntity
                .ok()
                .body(supplierService.getSupplierById(id));
    }

    /**
     * get all supplier
     *
     * @return List<SupplierDto>
     */
    @Operation(description = "Get all supplier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get all supplier successfully. Return message updated successfully"),
                    @ApiResponse(responseCode = "500", description = "Server error"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. The request lacks valid authentication credentials."),
                    @ApiResponse(responseCode = "403", description = "Forbidden. The server understood the request but refuses to authorize it.")

            })
    @GetMapping("/all")
    public ResponseEntity<List<SupplierDto>> getAllSupplier() {
        return ResponseEntity
                .ok()
                .body(supplierService.getAllSupplier());
    }


    //    @GetMapping("/encrypt/{id}")
//    public ResponseEntity<SupplierDto> getSupplierByIdTestRSA(
//            @PathVariable Integer id
//    ) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
//        String encryptedData = rsaUtils.encrypt(String.valueOf(id));
//
//        return ResponseEntity
//                .ok()
//                .body(supplierService.getSupplierByIdRSA(encryptedData));
//    }

}
