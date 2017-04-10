<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批流程管理</title>
</head>
<body>
	<div>
		<table>
			<th>
				<td>流程名称</td>
				<td>最新版本</td>
				<td>说明</td>
				<td>相关操作</td>
			</th>
			<c:choose>
				<c:when test="${not empty defiList}">
					<c:forEach items="${defiList}" var="defi" varStatus="vs">
						<tr>
							<td>${vs.count}</td>
							<td>${defi.name}</td>
							<td>${defi.version}</td>
							<td>${defi.description}</td>
							<td>删除  查看流程图
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="4">目前没有任何流程.</td></tr>
				</c:otherwise>
			</c:choose>
			
		</table>
	</div>
	
	<div>
		<input type="button" onclick="deployFlowDefi()" value="部署流程定义图"/>
	</div>
	
</body>
</html>