package org.rayshan.workflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "workflow_level_mappings")
@IdClass(WorkflowLevelMapping.WorkflowLevelMappingId.class)
public class WorkflowLevelMapping {
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

    // Getters and Setters
    public Long getTemplateId() {
        return templateId;
    }
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
    public Long getLevel() {
        return level;
    }
    public void setLevel(Long level) {
        this.level = level;
    }
    public String getActionId() {
        return actionId;
    }
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }
    public String getActionName() {
        return actionName;
    }
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public Long getNextLevel() {
        return nextLevel;
    }
    public void setNextLevel(Long nextLevel) {
        this.nextLevel = nextLevel;
    }


    public class WorkflowLevelMappingId {
        private Long templateId;
        private Long level;

        // Getters and Setters
        public Long getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Long templateId) {
            this.templateId = templateId;
        }

        public Long getLevel() {
            return level;
        }

        public void setLevel(Long level) {
            this.level = level;
        }
    }
}
