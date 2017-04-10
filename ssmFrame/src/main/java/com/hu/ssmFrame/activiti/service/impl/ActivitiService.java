package com.hu.ssmFrame.activiti.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;

import com.hu.ssmFrame.activiti.service.IActivitiService;

@Service
public class ActivitiService implements IActivitiService{
	@Resource(name="processEngine")
	ProcessEngine processEngine;

	@Override
	public List<ProcessDefinition> findLatestFlowDefi() {
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		
		return query.latestVersion().orderByProcessDefinitionKey().asc().list();
	}
	
	
}
