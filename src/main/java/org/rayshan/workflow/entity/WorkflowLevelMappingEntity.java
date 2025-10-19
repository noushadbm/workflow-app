package org.rayshan.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column(name = "action_id")
    private String actionId;

    @Column(name = "action_name")
    private String actionName;
    @Column(name = "next_level")
    private Long nextLevel;

    public class WorkflowLevelMappingId {
        private Long templateId;
        private Long level;
    }
}
