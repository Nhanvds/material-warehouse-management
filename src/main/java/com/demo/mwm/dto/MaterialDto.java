package com.demo.mwm.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;


/**
 * Data Transfer Object (DTO) for Material.
 */
public class MaterialDto {

    private Integer id;

    @NotBlank(message = "valid.material.code.not.blank")
    private String materialCode;

    @NotBlank(message = "valid.material.name.not.blank")
    private String materialName;

    @NotNull(message = "valid.material.price.not.null")
    private Double materialPrice;

    @Digits(integer = 5, fraction = 0, message = "valid.material.quantity.invalid")
    private Integer materialQuantity;

    private String materialNote;

    @NotNull(message = "valid.supplier.id.not.null")
    private Integer supplierId;

    private SupplierDto supplier;

    private Instant updatedAt;

    private Instant createdAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Double getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(Double materialPrice) {
        this.materialPrice = materialPrice;
    }

    public Integer getMaterialQuantity() {
        return materialQuantity;
    }

    public void setMaterialQuantity(Integer materialQuantity) {
        this.materialQuantity = materialQuantity;
    }

    public String getMaterialNote() {
        return materialNote;
    }

    public void setMaterialNote(String materialNote) {
        this.materialNote = materialNote;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
