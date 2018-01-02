package com.lf.gw.service.mapper;

import com.lf.gw.domain.*;
import com.lf.gw.service.dto.ProjectInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectInfo and its DTO ProjectInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectInfoMapper extends EntityMapper<ProjectInfoDTO, ProjectInfo> {

    

    

    default ProjectInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setId(id);
        return projectInfo;
    }
}
