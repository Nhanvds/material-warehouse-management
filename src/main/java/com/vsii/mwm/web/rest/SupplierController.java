package com.vsii.mwm.web.rest;

import com.vsii.mwm.service.ISupplierService;
import com.vsii.mwm.service.dto.SupplierDto;
import com.vsii.mwm.service.dto.response.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/suppliers")
public class SupplierController {

    private final ISupplierService supplierService;

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("save")
    public CommonResponse<?> createSupplier(
            @Valid @RequestBody SupplierDto supplierDto
    ) {
        supplierService.createSupplier(supplierDto);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.CREATED.value())
                .message("Create supplier successfully");
    }

    @PutMapping("/{id}/update")
    public CommonResponse<?> updateSupplier(
            @PathVariable Integer id,
            @Valid @RequestBody SupplierDto supplierDto
    ) {
        supplierService.updateSupplier(id, supplierDto);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .message("Update supplier successfully");
    }

    @DeleteMapping("/{id}/delete")
    public CommonResponse<?> deleteSupplier(
            @PathVariable Integer id
    ) {
        supplierService.deleteSupplier(id);
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .message("Update supplier successfully");
    }

    @GetMapping("/{id}")
    public CommonResponse<?> getSupplierById(
            @PathVariable Integer id
    ) {
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .data(supplierService.getSupplierById(id));
    }

    @GetMapping("/all")
    public CommonResponse<?> getAllSupplier() {
        return new CommonResponse<>()
                .success()
                .responseCode(HttpStatus.OK.value())
                .data(supplierService.getAllSupplier());
    }

}
