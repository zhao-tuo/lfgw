package com.lf.gw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectTransactionRecord.
 */
@Entity
@Table(name = "project_transaction_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectTransactionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(
        name="ID_GENERATOR",
        table="ID_GENERATOR",
        pkColumnName = "PK_NAME",
        valueColumnName = "PK_VALUE",
        pkColumnValue = "PROJECT_TRANSACTION_RECORD_ID",
        allocationSize = 10,
        initialValue = 0
    )
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")
    private Long id;

    @NotNull
    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @NotNull
    @Column(name = "transaction_year", nullable = false)
    private Integer transactionYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public ProjectTransactionRecord recordId(Long recordId) {
        this.recordId = recordId;
        return this;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public ProjectTransactionRecord projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getTransactionYear() {
        return transactionYear;
    }

    public ProjectTransactionRecord transactionYear(Integer transactionYear) {
        this.transactionYear = transactionYear;
        return this;
    }

    public void setTransactionYear(Integer transactionYear) {
        this.transactionYear = transactionYear;
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
        ProjectTransactionRecord projectTransactionRecord = (ProjectTransactionRecord) o;
        if (projectTransactionRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectTransactionRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectTransactionRecord{" +
            "id=" + getId() +
            ", recordId=" + getRecordId() +
            ", projectId=" + getProjectId() +
            ", transactionYear=" + getTransactionYear() +
            "}";
    }
}
