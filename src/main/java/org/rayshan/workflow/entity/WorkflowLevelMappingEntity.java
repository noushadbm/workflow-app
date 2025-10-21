package org.rayshan.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "workflow_level_mappings")
@IdClass(WorkflowLevelMappingEntity.WorkflowLevelMappingId.class)
public class WorkflowLevelMappingEntity {
    @Id
    @Column(name = "template_id")
    private Long templateId;
    @Id
    @Column(name = "level")
    private Long level;
    @Column(name = "assignee")
    private String assignee;

    @Data
    public static class WorkflowLevelMappingId implements Serializable {
        private Long templateId;
        private Long level;
        public WorkflowLevelMappingId() {
        }

        public WorkflowLevelMappingId(Long templateId, Long level) {
            this.templateId = templateId;
            this.level = level;
        }
    }
}
