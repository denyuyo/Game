<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// キャッシュの無効化
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ジグソーパズル</title>
	<link rel="stylesheet" href="css/title.css" type="text/css">
</head>
<body>
	<form action="/Game/TitleServlet" method="post" name="Form1">
		<div class="form-group">
			<input class="button" type="submit" name="start" value="スタート">
		</div>
	</form>
</body>
</html>