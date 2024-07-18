package com.demo.mwm.entity;

import com.demo.mwm.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entity class representing a material in the system.
 */
@Entity
@Table(name = Constants.MaterialColumn.TABLE_NAME)
public class MaterialEntity extends AbstractAuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Constants.MaterialColumn.ID)
    private Integer id;

    @Size(max = 50, message = "Material code must be at most 50 characters long")
    @NotBlank(message = "valid.material.code.not.blank")
    @Column(name = Constants.MaterialColumn.MATERIAL_CODE, unique = true)
    private String materialCode;

    @Size(max = 50, message = "Material name must be at most 50 characters long")    @NotBlank(message = "valid.material.name.not.blank")
    @Column(name = Constants.MaterialColumn.MATERIAL_NAME)
    private String materialName;

    @NotNull(message = "valid.material.price.not.null")
    @Column(name = Constants.MaterialColumn.MATERIAL_PRICE)
    private Double materialPrice;

    @NotNull(message = "valid.material.quantity.not.null")
    @Column(name = Constants.MaterialColumn.MATERIAL_QUANTITY)
    private Integer materialQuantity;

    @Size(max = 500, message = "Material note must be at most 500 characters long")
    @Column(name = Constants.MaterialColumn.MATERIAL_NOTE)
    private String materialNote;

    @NotNull(message = "valid.supplier.id.not.null")
    @Column(name = Constants.MaterialColumn.SUPPLIER_ID)
    private Integer supplierId;

    @Column(name = Constants.MaterialColumn.IS_ACTIVE)
    private Boolean isActive = true;

    @Override
    public Integer getId() {
        return id;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
