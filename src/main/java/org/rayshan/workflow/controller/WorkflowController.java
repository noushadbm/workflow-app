package org.rayshan.workflow.controller;

import lombok.extern.log4j.Log4j2;
import org.rayshan.workflow.entity.WorkflowTemplateEntity;
import org.rayshan.workflow.exception.AppException;
import org.rayshan.workflow.modal.ApiResponse;
import org.rayshan.workflow.modal.WorkflowTemplate;
import org.rayshan.workflow.service.WorkflowService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {
    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkflowTemplate> getWorkflow(@PathVariable("id") Long workflowId) throws AppException {
        log.info("Fetching workflow template with id: {}", workflowId);
        WorkflowTemplate workflowTemplate = workflowService.getWorkflowTemplateById(workflowId);
        ApiResponse<WorkflowTemplate> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(workflowTemplate);
        log.info("Returning workflow template: {}", workflowTemplate);
        return response;
    }
}
