package com.lf.gw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ShopInfo.
 */
@Entity
@Table(name = "shop_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(
        name="ID_GENERATOR",
        table="ID_GENERATOR",
        pkColumnName = "PK_NAME",
        valueColumnName = "PK_VALUE",
        pkColumnValue = "SHOP_INFO_ID",
        allocationSize=10,
        initialValue = 0
    )
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")
    private Long id;

    @NotNull
    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @NotNull
    @Column(name = "shop_name", nullable = false)
    private String shopName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public ShopInfo shopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public ShopInfo projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getShopName() {
        return shopName;
    }

    public ShopInfo shopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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
        ShopInfo shopInfo = (ShopInfo) o;
        if (shopInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shopInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
            "id=" + getId() +
            ", shopId=" + getShopId() +
            ", projectId=" + getProjectId() +
            ", shopName='" + getShopName() + "'" +
            "}";
    }
}
