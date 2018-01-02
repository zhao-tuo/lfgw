package com.lf.gw.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SaleAmt2 entity.
 */
public class SaleAmt2DTO implements Serializable {

    private Long id;

    @NotNull
    private Long amt2Id;

    @NotNull
    private Long projectId;

    @NotNull
    private Long shopId;

    @NotNull
    private String amt2Info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmt2Id() {
        return amt2Id;
    }

    public void setAmt2Id(Long amt2Id) {
        this.amt2Id = amt2Id;
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

    public String getAmt2Info() {
        return amt2Info;
    }

    public void setAmt2Info(String amt2Info) {
        this.amt2Info = amt2Info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaleAmt2DTO saleAmt2DTO = (SaleAmt2DTO) o;
        if(saleAmt2DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleAmt2DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleAmt2DTO{" +
            "id=" + getId() +
            ", amt2Id=" + getAmt2Id() +
            ", projectId=" + getProjectId() +
            ", shopId=" + getShopId() +
            ", amt2Info='" + getAmt2Info() + "'" +
            "}";
    }
}
