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
<script>
//パズルピースを生成して配置する関数
function createPuzzlePieces(imagePath, pieceCount) {
	// パズルを配置するコンテナを取得
	const container = document.getElementById('puzzle-container');
	container.style.position = 'relative'; // コンテナのポジションを相対位置に設定
	container.style.width = '400px'; //  コンテナの幅を設定
	container.style.height = '400px';// コンテナの高さを設定

	// パズルのピース数に基づいて行あたりのピース数を計算（平方根を取る）
	const piecesPerRow = Math.sqrt(pieceCount);
	// ピースのサイズを計算（コンテナの幅をピース数で割る）
	const pieceSize = container.offsetWidth / piecesPerRow;

	// ピースの総数に対してループを回す
	for (let i = 0; i < pieceCount; i++) {
		const piece = document.createElement('div'); // 新しいdiv要素（ピース）を作成
		piece.className = 'puzzle-piece'; // クラス名を設定
		piece.style.width = pieceSize + 'px'; // ピースの幅を設定
		piece.style.height = pieceSize + 'px'; // ピースの高さを設定
		piece.style.backgroundImage = 'url(' + imagePath + ')'; // ピースの背景画像を設定
		piece.style.backgroundSize = (container.offsetWidth) + 'px ' + (container.offsetHeight) + 'px';
		
		// 各ピースが元の画像の異なる部分を表示するように設定
		const col = i % piecesPerRow;
		const row = Math.floor(i / piecesPerRow);
		const x = col * pieceSize;
		const y = row * pieceSize;
		piece.style.backgroundPosition = `-${x}px -${y}px`;
		
		// CSSでドラッグ可能にする
		piece.style.position = 'absolute';
		
		// ピースの初期位置をランダムに設定
		piece.style.left = Math.floor(Math.random() * (container.offsetWidth - pieceSize)) + 'px';
		piece.style.top = Math.floor(Math.random() * (container.offsetHeight - pieceSize)) + 'px';
		
		// ドラッグ＆ドロップのイベントリスナーを追加
		addDragAndDropListeners(piece, x, y);
		
		container.appendChild(piece); // ピースをコンテナに追加
	}
}


//ピースにドラッグ＆ドロップ機能を追加する関数
function addDragAndDropListeners(piece) {
	// マウスボタンが押されたときの処理
	piece.onmousedown = function(event) {
		startX = piece.offsetLeft; // ピースの初期x座標を記録
		startY = piece.offsetTop; // ピースの初期y座標を記録
		offsetX = event.clientX - startX; // マウスとピースのx座標の差を計算
		offsetY = event.clientY - startY; // マウスとピースのy座標の差を計算
		document.onmousemove = function(event) {
			piece.style.left = (event.clientX - offsetX) + 'px'; // ピースのx座標を更新
			piece.style.top = (event.clientY - offsetY) + 'px'; // ピースのy座標を更新
		};
		// マウスボタンが離されたときの処理
		document.onmouseup = function() {
			document.onmousemove = null; // マウス移動イベントを無効化
			document.onmouseup = null; // マウスアップイベントを無効化
			// ピースの位置をチェックし、必要に応じて調整
		};
	};
}
//ページが読み込まれたときにパズルピースを生成する
window.onload = function() {
	createPuzzlePieces('<%= puzzle.getImagePath() %>', <%= puzzle.getPieceCount() %>);
};
</script>
</head>
<body>
	<h2>パズル画面</h2>
	<p>
		<button id = "shuffle" onclick="shuffle()">シャッフル</button>
		<span id = "time"></span>
	</p>
	<p id = "how-to-play">各ピースはドラッグ＆ドロップで移動できます。</p>
		
	<div id="puzzle-container"></div>
		
</body>
</html>