package com.lf.gw.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SaleAmt1 entity.
 */
public class SaleAmt1DTO implements Serializable {

    private Long id;

    @NotNull
    private Long amt1Id;

    @NotNull
    private Long projectId;

    @NotNull
    private Long shopId;

    @NotNull
    private String amt1Info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmt1Id() {
        return amt1Id;
    }

    public void setAmt1Id(Long amt1Id) {
        this.amt1Id = amt1Id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getAmt1Info() {
        return amt1Info;
    }

    public void setAmt1Info(String amt1Info) {
        this.amt1Info = amt1Info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaleAmt1DTO saleAmt1DTO = (SaleAmt1DTO) o;
        if(saleAmt1DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleAmt1DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleAmt1DTO{" +
            "id=" + getId() +
            ", amt1Id=" + getAmt1Id() +
            ", projectId=" + getProjectId() +
            ", shopId=" + getShopId() +
            ", amt1Info='" + getAmt1Info() + "'" +
            "}";
    }
}
