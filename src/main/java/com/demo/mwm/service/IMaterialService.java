package com.demo.mwm.service;


import com.demo.mwm.dto.MaterialDto;

import com.demo.mwm.dto.response.PageResponse;

public interface IMaterialService {

    MaterialDto getDetailMaterialById(Integer id);

    MaterialDto createMaterial(MaterialDto materialDto);

    MaterialDto updateMaterial(Integer id, MaterialDto materialDto);

    void deleteMaterial(Integer id);

    PageResponse<?> getMaterialList(Integer page, Integer size, String sortProperty, String sortOrder ,
                                              String materialName, String materialCode);
}
