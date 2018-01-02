package com.lf.gw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SaleAmt1.
 */
@Entity
@Table(name = "sale_amt1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SaleAmt1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(
        name="ID_GENERATOR",
        table="ID_GENERATOR",
        pkColumnName = "PK_NAME",
        valueColumnName = "PK_VALUE",
        pkColumnValue = "SALE_AMT1_ID",
        allocationSize=10,
        initialValue = 0
    )
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")
    private Long id;

    @NotNull
    @Column(name = "amt_1_id", nullable = false)
    private Long amt1Id;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @NotNull
    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @NotNull
    @Column(name = "amt_1_info", nullable = false)
    private String amt1Info;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmt1Id() {
        return amt1Id;
    }

    public SaleAmt1 amt1Id(Long amt1Id) {
        this.amt1Id = amt1Id;
        return this;
    }

    public void setAmt1Id(Long amt1Id) {
        this.amt1Id = amt1Id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public SaleAmt1 projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getShopId() {
        return shopId;
    }

    public SaleAmt1 shopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getAmt1Info() {
        return amt1Info;
    }

    public SaleAmt1 amt1Info(String amt1Info) {
        this.amt1Info = amt1Info;
        return this;
    }

    public void setAmt1Info(String amt1Info) {
        this.amt1Info = amt1Info;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SaleAmt1 saleAmt1 = (SaleAmt1) o;
        if (saleAmt1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleAmt1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleAmt1{" +
            "id=" + getId() +
            ", amt1Id=" + getAmt1Id() +
            ", projectId=" + getProjectId() +
            ", shopId=" + getShopId() +
            ", amt1Info='" + getAmt1Info() + "'" +
            "}";
    }
}
