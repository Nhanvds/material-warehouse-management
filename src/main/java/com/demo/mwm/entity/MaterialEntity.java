package com.demo.mwm.entity;

import jakarta.persistence.*;

/**
 * Entity class representing a material in the system.
 */
@Entity
@Table(name = "materials")
public class MaterialEntity extends AbstractAuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "material_code", unique = true)
    private String materialCode;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "material_price")
    private Double materialPrice;

    @Column(name = "material_quantity")
    private Integer materialQuantity;

    @Column(name = "material_note")
    private String materialNote;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "is_active")
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
