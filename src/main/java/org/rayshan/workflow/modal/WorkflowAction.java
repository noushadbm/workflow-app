package org.rayshan.workflow.modal;

import lombok.Data;

@Data
public class WorkflowAction {
    private Long level;
    private String actionId;
    private String actionName;
    private Long nextLevel;
}
