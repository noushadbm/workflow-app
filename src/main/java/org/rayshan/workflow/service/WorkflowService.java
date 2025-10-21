package org.rayshan.workflow.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.rayshan.workflow.entity.WorkflowActionMappingEntity;
import org.rayshan.workflow.entity.WorkflowLevelMappingEntity;
import org.rayshan.workflow.entity.WorkflowTemplateEntity;
import org.rayshan.workflow.exception.AppException;
import org.rayshan.workflow.modal.WorkflowLevel;
import org.rayshan.workflow.modal.WorkflowTemplate;
import org.rayshan.workflow.repository.WorkflowActionRepository;
import org.rayshan.workflow.repository.WorkflowLvlMapRepository;
import org.rayshan.workflow.repository.WorkflowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class WorkflowService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowLvlMapRepository workflowLvlMapRepository;
    private final WorkflowActionRepository workflowActionRepository;
    public WorkflowService(WorkflowRepository workflowRepository,
                           WorkflowLvlMapRepository workflowLvlMapRepository,
                           WorkflowActionRepository workflowActionRepository) {
        this.workflowRepository = workflowRepository;
        this.workflowLvlMapRepository = workflowLvlMapRepository;
        this.workflowActionRepository = workflowActionRepository;
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
                level.setAssignee(levelMap.getAssignee());
                return level;
            }).toList());

            List<WorkflowActionMappingEntity> levelActions = workflowActionRepository.findByTemplateId(workflowTemplateId);
            workflowTemplate.setActions(levelActions.stream().map(actionMap -> {
                org.rayshan.workflow.modal.WorkflowAction action = new org.rayshan.workflow.modal.WorkflowAction();
                action.setLevel(actionMap.getLevel());
                action.setActionId(actionMap.getActionId());
                action.setActionName(actionMap.getActionName());
                action.setNextLevel(actionMap.getNextLevel());
                return action;
            }).toList());

            return workflowTemplate;
        } else {
            throw new AppException("Workflow Template not found with workflowTemplateId: " + workflowTemplateId, 404);
        }
    }

    @Transactional
    public WorkflowTemplate updateWorkflowTemplate(WorkflowTemplate request) throws AppException {
        log.info("Updating workflow template with id: {}", request.getTemplateId());
        this.workflowRepository.save(convertToEntity(request));
        this.workflowLvlMapRepository.deleteByTemplateId(request.getTemplateId());
        this.workflowActionRepository.deleteByTemplateId(request.getTemplateId());

        List<WorkflowLevelMappingEntity> allLevels = new ArrayList<>();
        request.getLevels().stream().forEach(level -> {
            WorkflowLevelMappingEntity levelEntity = new WorkflowLevelMappingEntity();
            levelEntity.setTemplateId(request.getTemplateId());
            levelEntity.setLevel(level.getLevel());
            levelEntity.setAssignee(level.getAssignee());
            allLevels.add(levelEntity);
        });

        List<WorkflowActionMappingEntity> allActions = new ArrayList<>();
        List<WorkflowLevel> levels = request.getLevels();
        for(int i = 0; i < levels.size(); i++) {
            WorkflowLevel level = levels.get(i);
            Long nextLevel = (i < request.getLevels().size() -1) ? request.getLevels().get(i +1).getLevel() : -1l;

            WorkflowActionMappingEntity actionEntity = new WorkflowActionMappingEntity();
            actionEntity.setTemplateId(request.getTemplateId());
            actionEntity.setLevel(level.getLevel());
            actionEntity.setActionId("APPROVE");
            actionEntity.setActionName("Approve");
            actionEntity.setNextLevel(nextLevel);
            allActions.add(actionEntity);

            actionEntity = new WorkflowActionMappingEntity();
            actionEntity.setTemplateId(request.getTemplateId());
            actionEntity.setLevel(level.getLevel());
            actionEntity.setActionId("REJECT");
            actionEntity.setActionName("Reject");
            actionEntity.setNextLevel(-1l);
            allActions.add(actionEntity);
        }

//        request.getLevels().stream().forEach(level -> {
//            WorkflowActionMappingEntity actionEntity = new WorkflowActionMappingEntity();
//            actionEntity.setTemplateId(request.getTemplateId());
//            actionEntity.setLevel(level.getLevel());
//            actionEntity.setActionId("APPROVE");
//            actionEntity.setActionName("Approve");
//            actionEntity.setNextLevel(level.getNextLevel());
//            allActions.add(actionEntity);
//
//            actionEntity = new WorkflowActionMappingEntity();
//            actionEntity.setTemplateId(request.getTemplateId());
//            actionEntity.setLevel(level.getLevel());
//            actionEntity.setActionId("REJECT");
//            actionEntity.setActionName("Reject");
//            actionEntity.setNextLevel(-1l);
//            allActions.add(actionEntity);
//        });

        this.workflowLvlMapRepository.saveAll(allLevels);
        workflowActionRepository.saveAll(allActions);
        log.info("Workflow template with id: {} updated successfully", request.getTemplateId());
        return getWorkflowTemplateById(request.getTemplateId());
    }

    private WorkflowTemplateEntity convertToEntity(WorkflowTemplate wfTemplateModal) {
        WorkflowTemplateEntity entity = new WorkflowTemplateEntity();
        entity.setTemplateId(wfTemplateModal.getTemplateId());
        entity.setTemplateName(wfTemplateModal.getTemplateName());
        entity.setDescription(wfTemplateModal.getDescription());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(wfTemplateModal.getUpdatedBy());
        return entity;
    }

//    private WorkflowLevelMappingEntity convertLevelToEntity(WorkflowLevel level) {
//        WorkflowLevelMappingEntity entity = new WorkflowLevelMappingEntity();
//        entity.setTemplateId(level.getTemplateId());
//        entity.setLevel(level.getLevel());
//        entity.setAssignee(level.getAssignee());
//        entity.setActionId(level.getActionId());
//        entity.setActionName(level.getActionName());
//        entity.setNextLevel(level.getNextLevel());
//        return entity;
//    }
}
