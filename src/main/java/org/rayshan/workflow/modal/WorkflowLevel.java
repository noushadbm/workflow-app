package org.rayshan.workflow.modal;

import lombok.Data;

@Data
public class WorkflowLevel {
    private Long level;
    private String actionId;
    private String actionName;
    private Long nextLevel;
}
