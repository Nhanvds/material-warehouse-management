package com.demo.mwm.service.impl;

import com.demo.mwm.entity.PermissionEntity;
import com.demo.mwm.repository.IPermissionRepository;
import com.demo.mwm.service.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    private final IPermissionRepository permissionRepository;

    public PermissionServiceImpl(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * @return All Permissions List<PermissionEntity>
     */
    @Override
    public List<PermissionEntity> getAll() {
        return permissionRepository.findAll();
    }
}
