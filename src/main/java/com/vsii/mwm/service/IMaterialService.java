package com.vsii.mwm.service;


import com.vsii.mwm.service.dto.MaterialDto;
import com.vsii.mwm.service.dto.PageInputDto;

import com.vsii.mwm.service.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface IMaterialService {

    MaterialDto getDetailMaterialById(Integer id);

    MaterialDto createMaterial(MaterialDto materialDto);

    MaterialDto updateMaterial(Integer id, MaterialDto materialDto);

    void deleteMaterial(Integer id);

    PageResponse<?> getMaterialList(Integer page, Integer size, String sortProperty, String sortOrder ,
                                              String materialName, String materialCode);
}
