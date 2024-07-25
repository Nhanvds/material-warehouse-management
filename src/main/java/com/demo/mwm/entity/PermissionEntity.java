package com.demo.mwm.entity;

import com.demo.mwm.utils.AuthoritiesConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "permissions")
public class PermissionEntity extends AbstractAuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Permission name cannot blank")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Permission endpoint cannot blank")
    @Column(name="endpoint",nullable = false)
    private String endpoint;

    @NotBlank(message = "Permission method cannot blank")
    @Column(name = "method",nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthoritiesConstants.HTTP_METHOD method;

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public AuthoritiesConstants.HTTP_METHOD getMethod() {
        return method;
    }

    public void setMethod(AuthoritiesConstants.HTTP_METHOD method) {
        this.method = method;
    }
}
