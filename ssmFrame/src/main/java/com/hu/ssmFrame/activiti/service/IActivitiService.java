package com.hu.ssmFrame.activiti.service;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

public interface IActivitiService {
	/**
	 * 查找所有流程的最新版本.
	 * @return
	 */
	public abstract List<ProcessDefinition> findLatestFlowDefi();
}
