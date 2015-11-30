<%@ page pageEncoding="utf-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>  
<html>  
<head>  
<meta charset="utf-8">  
<title>上传图片</title>  
</head>  
<body>  
<form action="${ctx}/rest/wisclassroom/uploadwisclassroom" method="post" enctype="multipart/form-data">
<table>
<tr>
		<input id="file1" type="file" name="file" />
</tr>
		<tr>
			<td>mac</td>
			<td><input id="mac" name="mac" value="D050993DEF36"/></td>
		</tr>
		<tr>
			<td>date</td>
			<td><input id="date" name="date" value="2015-05-08"/></td>
		</tr>
		<tr>
			<td>classnum</td>
			<td><input id="classnum" name="classnum" value="34"/></td>
		</tr>
		<tr>
			<td>filename</td>
			<td><input id="filename" name="filename" /></td>
		</tr>
		<tr>
			<td>filePath</td>
			<td><input id="filePath" name="filePath" value="课堂产生\\案例分析"/></td>
		</tr>
		<tr>
			<td>size</td>
			<td><input id="size" name="size" /></td>
		</tr>
		<tr>
		<td>
		<input id="tijiao" type="submit" value="Submit" />
		</td>
		</tr>
		</table>
	</form>  
</body>  
</html> 