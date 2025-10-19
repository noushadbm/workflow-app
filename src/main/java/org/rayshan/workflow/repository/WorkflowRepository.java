package org.rayshan.workflow.repository;

import org.rayshan.workflow.entity.WorkflowTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepository extends JpaRepository<WorkflowTemplateEntity, Long> {
}
