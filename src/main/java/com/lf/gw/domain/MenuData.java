package com.lf.gw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MenuData.
 */
@Entity
@Table(name = "menu_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MenuData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "key_word", nullable = false)
    private String keyWord;

    @NotNull
    @Column(name = "icon", nullable = false)
    private String icon;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "expended")
    private Boolean expended;

    @ManyToOne
    private MenuData parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MenuData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public MenuData keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getIcon() {
        return icon;
    }

    public MenuData icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public MenuData url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isExpended() {
        return expended;
    }

    public MenuData expended(Boolean expended) {
        this.expended = expended;
        return this;
    }

    public void setExpended(Boolean expended) {
        this.expended = expended;
    }

    public MenuData getParent() {
        return parent;
    }

    public MenuData parent(MenuData menuData) {
        this.parent = menuData;
        return this;
    }

    public void setParent(MenuData menuData) {
        this.parent = menuData;
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
        MenuData menuData = (MenuData) o;
        if (menuData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menuData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenuData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", keyWord='" + getKeyWord() + "'" +
            ", icon='" + getIcon() + "'" +
            ", url='" + getUrl() + "'" +
            ", expended='" + isExpended() + "'" +
            "}";
    }
}
