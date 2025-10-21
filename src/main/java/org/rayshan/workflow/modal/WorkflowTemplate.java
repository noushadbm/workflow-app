package org.rayshan.workflow.modal;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkflowTemplate {
    private Long templateId;
    private String templateName;
    private String description;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private List<WorkflowLevel> levels;
    private List<WorkflowAction> actions;
}
