package org.rayshan.workflow.controller;

import lombok.extern.log4j.Log4j2;
import org.rayshan.workflow.entity.WorkflowTemplateEntity;
import org.rayshan.workflow.exception.AppException;
import org.rayshan.workflow.modal.ApiResponse;
import org.rayshan.workflow.modal.WorkflowTemplate;
import org.rayshan.workflow.service.WorkflowService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {
    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkflowTemplate> getWorkflow(@PathVariable("id") Long workflowTemplateId) throws AppException {
        log.info("Fetching workflow template with id: {}", workflowTemplateId);
        WorkflowTemplate workflowTemplate = workflowService.getWorkflowTemplateById(workflowTemplateId);
        ApiResponse<WorkflowTemplate> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(workflowTemplate);
        log.info("Returning workflow template: {}", workflowTemplate);
        return response;
    }

    @PutMapping("/{id}")
    public ApiResponse<WorkflowTemplate> updateWorkflow(@PathVariable("id") Long workflowTemplateId, @RequestBody WorkflowTemplate request) throws AppException {
        log.info("Received workflow template update request with id: {}", workflowTemplateId);
        request.setTemplateId(workflowTemplateId);
        WorkflowTemplate workflowTemplate = workflowService.updateWorkflowTemplate(request);
        ApiResponse<WorkflowTemplate> response = new ApiResponse<>(HttpStatus.OK.value(), "Success");
        response.setData(workflowTemplate);
        log.info("Returning updated workflow template: {}", workflowTemplate);
        return response;
    }
}
