package org.rayshan.workflow.repository;

import org.rayshan.workflow.entity.WorkflowLevelMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowLvlMapRepository extends JpaRepository<WorkflowLevelMappingEntity, WorkflowLevelMappingEntity.WorkflowLevelMappingId>  {
    List<WorkflowLevelMappingEntity> findByTemplateId(Long templateId);
}
