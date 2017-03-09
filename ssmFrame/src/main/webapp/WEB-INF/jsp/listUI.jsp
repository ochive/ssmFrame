<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附件下载</title>
</head>
<body>
	<div>
		<table>
			<tr>
				<th>序号</th>
				<th>附件名称</th>
				<th>操作</th>
			</tr>
			
			<c:choose>
				<c:when test="${paths not empty}">
					<c:forEach items="paths" var="path" varStatus="vs">
					<tr>
						<td>${vs.count}</td>
						<td>${path.filename}</td>
						<td>
							<a href="http://10.132.1.144:8080/ssmFrame/fileService/fetchFile?filePathId=${path.idFilepaths}">下载</a>
						</td>
					</tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="3">没找到任何附件</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
</body>
</html>