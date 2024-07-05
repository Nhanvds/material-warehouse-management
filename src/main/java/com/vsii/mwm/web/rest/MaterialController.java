package com.vsii.mwm.web.rest;

import com.vsii.mwm.service.IMaterialService;
import com.vsii.mwm.service.dto.MaterialDto;
import com.vsii.mwm.service.dto.PageInputDto;
import com.vsii.mwm.service.dto.response.CommonResponse;
import com.vsii.mwm.service.dto.response.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/get-list")
    public PageResponse<?> getMaterialList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "0") Integer size,
            @RequestParam(required = false) String sortProperty,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String materialName,
            @RequestParam(required = false) String materialCode
    ) {
        return materialService.getMaterialList(page, size, sortProperty, sortOrder, materialName, materialCode);
    }

}
