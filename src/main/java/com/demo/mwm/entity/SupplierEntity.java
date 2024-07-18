package com.demo.mwm.entity;

import com.demo.mwm.utils.Constants;
import jakarta.persistence.*;

/**
 * Entity class representing a supplier in the system.
 */
@Entity
@Table(name = Constants.SupplierColumn.TABLE_NAME)
public class SupplierEntity extends AbstractAuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Constants.SupplierColumn.ID)
    private Integer id;

    @Column(name = Constants.SupplierColumn.SUPPLIER_CODE, unique = true)
    private String supplierCode;

    @Column(name = Constants.SupplierColumn.SUPPLIER_NAME)
    private String supplierName;

    @Column(name = Constants.SupplierColumn.SUPPLIER_ADDRESS)
    private String supplierAddress;

    @Column(name = Constants.SupplierColumn.SUPPLIER_PHONE_NUMBER)
    private String supplierPhoneNumber;

    @Column(name = Constants.SupplierColumn.SUPPLIER_NOTE)
    private String supplierNote;

    @Column(name = Constants.SupplierColumn.IS_ACTIVE)
    private Boolean isActive = true;


    @Override
    public Integer getId() {
        return id;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }


    public String getSupplierPhoneNumber() {
        return supplierPhoneNumber;
    }

    public void setSupplierPhoneNumber(String supplierPhoneNumber) {
        this.supplierPhoneNumber = supplierPhoneNumber;
    }

    public String getSupplierNote() {
        return supplierNote;
    }

    public void setSupplierNote(String supplierNote) {
        this.supplierNote = supplierNote;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
