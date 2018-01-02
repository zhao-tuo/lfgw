package com.lf.gw.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProjectInfo entity.
 */
public class ProjectInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long projectId;

    @NotNull
    private String projectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectInfoDTO projectInfoDTO = (ProjectInfoDTO) o;
        if(projectInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectInfoDTO{" +
            "id=" + getId() +
            ", projectId=" + getProjectId() +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
