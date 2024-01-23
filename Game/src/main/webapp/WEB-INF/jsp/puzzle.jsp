<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ジグソーパズル</title>
<!-- ドラッグ アンド ドロップ機能用の JavaScript -->
<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', (event) => {
	    let draggedElement = null; // ドラッグされている要素を保持する変数
	
	    // ドラッグ開始時の処理を追加
	    document.querySelectorAll('.puzzle-piece').forEach(elem => {
	        elem.addEventListener('dragstart', function(e) {
	            draggedElement = this; // ドラッグされている要素を設定
	        });
	    });
	
	    // ドロップ対象に入った時の処理を追加
	    document.getElementById('puzzle-container').addEventListener('dragover', function(e) {
	        e.preventDefault(); // デフォルトの処理をキャンセル（ドロップを許可）
	    });
	
	    // ドロップされた時の処理を追加
	    document.getElementById('puzzle-container').addEventListener('drop', function(e) {
	        e.preventDefault(); // デフォルトの処理をキャンセル
	        if (draggedElement) {
	            // ピースをドロップした位置に配置
	            draggedElement.style.position = 'absolute';
	            draggedElement.style.left = e.pageX - draggedElement.offsetWidth / 2 + 'px';
	            draggedElement.style.top = e.pageY - draggedElement.offsetHeight / 2 + 'px';
	            this.appendChild(draggedElement);
	        }
	    });
	});
	
	// パズル完成判定の関数
	function checkPuzzleCompletion() {
	    // ここにパズルが完成したかを判定するロジックを実装
	    // 実際のゲームでは、ピースの位置や向きなどを確認する必要がある
	    // 以下は単純な例
	    let allPiecesCorrect = true;
	    document.querySelectorAll('.puzzle-piece').forEach(elem => {
	        if (!isPieceInCorrectPosition(elem)) {
	            allPiecesCorrect = false;
	        }
	    });
	    if (allPiecesCorrect) {
	        alert('Congratulations! You completed the puzzle!');
	    } else {
	        alert('Not completed yet. Keep trying!');
	    }
	}
	
	// ピースが正しい位置にあるかどうかを判定する関数（実装が必要）
	function isPieceInCorrectPosition(piece) {
	    // ここに各ピースが正しい位置にあるかどうかの判定ロジックを実装
	    // 例: piece.getAttribute('data-correct-position') === 現在の位置
	    return true; // 仮の実装
	}
</script>
</head>
<body>
	<h2>パズル画面</h2>
	<div id="puzzle-container">
		<!-- ここにパズルのピースを表示 -->
	    <div class="puzzle-piece" draggable="true" id="piece1"></div>
	    <div class="puzzle-piece" draggable="true" id="piece2"></div>
	    <!-- 他のパズルピースも同様に追加 -->
	</div>
	<button onclick="checkPuzzleCompletion()">完成</button>
	<style>
	    #puzzle-container {
	        position: relative;
	        width: 500px; /* コンテナのサイズを適切に設定 */
	        height: 500px;
	        border: 1px solid #ccc;
	    }
	
	    .puzzle-piece {
	        width: 100px; /* ピースのサイズを設定 */
	        height: 100px;
	        background-color: blue; /* 仮の背景色 */
	        margin: 10px;
	    }
	</style>
</body>
</html>