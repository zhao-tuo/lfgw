package com.lf.gw.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ShopInfo entity.
 */
public class ShopInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long shopId;

    @NotNull
    private String shopName;

    private Long projectInfoId;

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getProjectInfoId() {
        return projectInfoId;
    }

    public void setProjectInfoId(Long projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShopInfoDTO shopInfoDTO = (ShopInfoDTO) o;
        if(shopInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shopInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShopInfoDTO{" +
            "id=" + getId() +
            ", shopId=" + getShopId() +
            ", shopName='" + getShopName() +
            ", projectInfoId='" + getProjectInfoId() +
            ", projectName='" + getProjectName() +
            "'" + "}";
    }
}
