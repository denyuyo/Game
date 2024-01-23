<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="jp.jigsawPuzzle.game.dao.PuzzleDAO" 
	import="jp.jigsawPuzzle.game.bean.PuzzleBean"
%>
<%
    String category = request.getParameter("category");
    PuzzleDAO dao = new PuzzleDAO();
    PuzzleBean puzzle = dao.getRandomPuzzleByCategory(category);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ジグソーパズル</title>
</head>
<body>
	<h2>パズル画面</h2>
	<p>
		<button id = "shuffle" onclick="shuffle()">シャッフル</button>
		<span id = "time"></span>
	</p>
		<img src="<%= puzzle.getImagePath() %>">
</body>
</html>