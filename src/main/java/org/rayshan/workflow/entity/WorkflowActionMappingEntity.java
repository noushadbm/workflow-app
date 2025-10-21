package org.rayshan.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "workflow_action_mappings")
@IdClass(WorkflowActionMappingEntity.WorkflowActionMappingId.class)
public class WorkflowActionMappingEntity {
    @Id
    @Column(name = "template_id")
    private Long templateId;
    @Id
    @Column(name = "level")
    private Long level;
    @Id
    @Column(name = "action_id")
    private String actionId;
    @Column(name = "action_name")
    private String actionName;
    @Column(name = "next_level")
    private Long nextLevel;

    @Data
    public static class WorkflowActionMappingId implements Serializable {
        private Long templateId;
        private Long level;
        private String actionId;
        public WorkflowActionMappingId() {
        }

        public WorkflowActionMappingId(Long templateId, Long level, String actionId) {
            this.templateId = templateId;
            this.level = level;
            this.actionId = actionId;
        }
    }
}
