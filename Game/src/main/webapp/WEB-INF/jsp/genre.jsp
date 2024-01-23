<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"
	import="java.util.ArrayList"
	import="jp.jigsawPuzzle.game.dao.PuzzleDAO" 
	import="jp.jigsawPuzzle.game.bean.PuzzleBean"
%>
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
</head>
<body>
	<h2>ジャンル一覧</h2>
	<ul>
		<%
			PuzzleDAO dao = new PuzzleDAO();
			ArrayList<PuzzleBean> categories = dao.getAllCategories();
			for (PuzzleBean category : categories) {
		%>
		<%= category.getCategory()  %>
		<% } %>
	</ul>
</body>
</html>