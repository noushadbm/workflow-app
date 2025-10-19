package org.rayshan.workflow.service;

import org.rayshan.workflow.entity.WorkflowLevelMappingEntity;
import org.rayshan.workflow.entity.WorkflowTemplateEntity;
import org.rayshan.workflow.exception.AppException;
import org.rayshan.workflow.modal.WorkflowLevel;
import org.rayshan.workflow.modal.WorkflowTemplate;
import org.rayshan.workflow.repository.WorkflowLvlMapRepository;
import org.rayshan.workflow.repository.WorkflowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkflowService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowLvlMapRepository workflowLvlMapRepository;
    public WorkflowService(WorkflowRepository workflowRepository, WorkflowLvlMapRepository workflowLvlMapRepository) {
        this.workflowRepository = workflowRepository;
        this.workflowLvlMapRepository = workflowLvlMapRepository;
    }

    public WorkflowTemplate getWorkflowTemplateById(Long workflowTemplateId) throws AppException {
        Optional<WorkflowTemplateEntity> wfOpt = workflowRepository.findById(workflowTemplateId);
        if(wfOpt.isPresent()) {
            WorkflowTemplateEntity wft = wfOpt.get();
            List<WorkflowLevelMappingEntity> levels = workflowLvlMapRepository.findByTemplateId(workflowTemplateId);
            WorkflowTemplate workflowTemplate = new WorkflowTemplate();
            workflowTemplate.setTemplateId(wft.getTemplateId());
            workflowTemplate.setTemplateName(wft.getTemplateName());
            workflowTemplate.setDescription(wft.getDescription());
            workflowTemplate.setUpdatedAt(wft.getUpdatedAt());
            workflowTemplate.setUpdatedBy(wft.getUpdatedBy());
            // Map levels to workflowTemplate
            workflowTemplate.setLevels(levels.stream().map(levelMap -> {
                WorkflowLevel level = new WorkflowLevel();
                level.setLevel(levelMap.getLevel());
                level.setActionId(levelMap.getActionId());
                level.setActionName(levelMap.getActionName());
                level.setNextLevel(levelMap.getNextLevel());
                return level;
            }).toList());

            return workflowTemplate;
        } else {
            throw new AppException("Workflow Template not found with workflowTemplateId: " + workflowTemplateId, 404);
        }
    }
}
