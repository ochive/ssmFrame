package com.hu.ssmFrame.activiti.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hu.ssmFrame.activiti.service.IActivitiService;

@Controller
@RequestMapping("/flowMgmt")
public class FlowMgmtController {
	@Autowired
	ProcessEngine processEngine;
	
	@Resource(name="activitiService")
	IActivitiService activitiService;
	
	/**
	 * 显示activiti流程管理页面.
	 * @return
	 */
	@RequestMapping(value="flowListUI",method=RequestMethod.GET)
	public String flowListUI(Model model){
		List<ProcessDefinition> defiList = activitiService.findLatestFlowDefi();
		model.addAttribute("defiList", defiList);
		return "listUI";
	}
}
