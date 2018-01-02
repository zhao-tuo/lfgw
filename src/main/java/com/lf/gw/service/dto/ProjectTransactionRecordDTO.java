package com.lf.gw.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProjectTransactionRecord entity.
 */
public class ProjectTransactionRecordDTO implements Serializable {

    private Long id;

    @NotNull
    private Long recordId;

    @NotNull
    private Long projectId;

    @NotNull
    private Integer transactionYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getTransactionYear() {
        return transactionYear;
    }

    public void setTransactionYear(Integer transactionYear) {
        this.transactionYear = transactionYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectTransactionRecordDTO projectTransactionRecordDTO = (ProjectTransactionRecordDTO) o;
        if(projectTransactionRecordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectTransactionRecordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectTransactionRecordDTO{" +
            "id=" + getId() +
            ", recordId=" + getRecordId() +
            ", projectId=" + getProjectId() +
            ", transactionYear=" + getTransactionYear() +
            "}";
    }
}
