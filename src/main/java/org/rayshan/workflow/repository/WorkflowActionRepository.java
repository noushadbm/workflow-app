package org.rayshan.workflow.repository;

import org.rayshan.workflow.entity.WorkflowActionMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowActionRepository extends JpaRepository<WorkflowActionMappingEntity, WorkflowActionMappingEntity.WorkflowActionMappingId>  {
    void deleteByTemplateId(Long templateId);

    List<WorkflowActionMappingEntity> findByTemplateId(Long templateId);
}
