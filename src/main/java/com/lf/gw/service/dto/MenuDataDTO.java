package com.lf.gw.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the MenuData entity.
 */
public class MenuDataDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String keyWord;

    @NotNull
    private String icon;

    @NotNull
    private String url;

    private Boolean expended = false;

    private Long parentId;

    private String parentName;

    private List<MenuDataDTO> children = new ArrayList<MenuDataDTO>();

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<MenuDataDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDataDTO> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isExpended() {
        return expended;
    }

    public void setExpended(Boolean expended) {
        this.expended = expended;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long menuDataId) {
        this.parentId = menuDataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuDataDTO menuDataDTO = (MenuDataDTO) o;
        if(menuDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menuDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenuDataDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", keyWord='" + getKeyWord() + "'" +
            ", icon='" + getIcon() + "'" +
            ", url='" + getUrl() + "'" +
            ", expended='" + isExpended() + "'" +
            "}";
    }
}
