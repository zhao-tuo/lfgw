package com.lf.gw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SaleAmt2.
 */
@Entity
@Table(name = "sale_amt2")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SaleAmt2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(
        name="ID_GENERATOR",
        table="ID_GENERATOR",
        pkColumnName = "PK_NAME",
        valueColumnName = "PK_VALUE",
        pkColumnValue = "SALE_AMT2_ID",
        allocationSize=10,
        initialValue = 0
    )
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")
    private Long id;

    @NotNull
    @Column(name = "amt_2_id", nullable = false)
    private Long amt2Id;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @NotNull
    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @NotNull
    @Column(name = "amt_2_info", nullable = false)
    private String amt2Info;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmt2Id() {
        return amt2Id;
    }

    public SaleAmt2 amt2Id(Long amt2Id) {
        this.amt2Id = amt2Id;
        return this;
    }

    public void setAmt2Id(Long amt2Id) {
        this.amt2Id = amt2Id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public SaleAmt2 projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getShopId() {
        return shopId;
    }

    public SaleAmt2 shopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getAmt2Info() {
        return amt2Info;
    }

    public SaleAmt2 amt2Info(String amt2Info) {
        this.amt2Info = amt2Info;
        return this;
    }

    public void setAmt2Info(String amt2Info) {
        this.amt2Info = amt2Info;
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
        SaleAmt2 saleAmt2 = (SaleAmt2) o;
        if (saleAmt2.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleAmt2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleAmt2{" +
            "id=" + getId() +
            ", amt2Id=" + getAmt2Id() +
            ", projectId=" + getProjectId() +
            ", shopId=" + getShopId() +
            ", amt2Info='" + getAmt2Info() + "'" +
            "}";
    }
}
