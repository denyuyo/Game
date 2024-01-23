function createPuzzlePieces(imagePath, pieceCount) {
    const container = document.getElementById('puzzle-container');
    container.style.position = 'relative';
    container.style.width = '400px'; // コンテナのサイズ
    container.style.height = '400px';

    const piecesPerRow = Math.sqrt(pieceCount); // 行あたりのピース数
    const pieceSize = container.offsetWidth / piecesPerRow; // ピースのサイズ

    for (let i = 0; i < pieceCount; i++) {
        const piece = document.createElement('div');
        piece.className = 'puzzle-piece';
        piece.style.width = pieceSize + 'px';
        piece.style.height = pieceSize + 'px';
        piece.style.backgroundImage = 'url(' + imagePath + ')';
        piece.style.backgroundSize = (container.offsetWidth) + 'px ' + (container.offsetHeight) + 'px';

        // 各ピースが元の画像の異なる部分を表示するように設定
        const col = i % piecesPerRow;
        const row = Math.floor(i / piecesPerRow);
        const x = col * pieceSize;
        const y = row * pieceSize;
        piece.style.backgroundPosition = `-${x}px -${y}px`;

        // 初期位置をランダムに設定
        piece.style.left = Math.floor(Math.random() * (container.offsetWidth - pieceSize)) + 'px';
        piece.style.top = Math.floor(Math.random() * (container.offsetHeight - pieceSize)) + 'px';

        // ドラッグ＆ドロップのイベントリスナーを追加
        addDragAndDropListeners(piece, x, y);

        container.appendChild(piece);
    }
}



function addDragAndDropListeners(piece) {
	let offsetX, offsetY;
	piece.onmousedown = function(event) {
		offsetX = event.offsetX;
		offsetY = event.offsetY;
		document.onmousemove = function(event) {
			piece.style.left = (event.pageX - offsetX) + 'px';
			piece.style.top = (event.pageY - offsetY) + 'px';
		};
		document.onmouseup = function() {
			document.onmousemove = null;
			document.onmouseup = null;
			// ここでピースの位置をチェックし、必要に応じて調整する
			
		};
	};
	
}

window.onload = function() {
	createPuzzlePieces('<%= puzzle.getImagePath() %>', <%= puzzle.getPieceCount() %>);
};

// canvas js

<style>
.puzzle-piece {
	width: 100px;
	height: 100px;
	background-size: cover;
	position: absolute;
	cursor: pointer;
}
</style>

//JavaScript の変数と定数の定義
const pieceSize = 80;
let can = document.getElementById('can');
let ctx = can.getContext('2d');
let pieces = []; // Pieceオブジェクトを格納する配列
let colMax = 0;  // ピースは横に何個並ぶか？
let rowMax = 0;  // ピースは縦に何個並ぶか？

//Pieceクラスの定義
class Piece {
 constructor(image, outline, x, y) {
     this.Image = image;
     this.Outline = outline;
     this.X = x;
     this.Y = y;
     this.OriginalCol = Math.round(x / pieceSize); // 本来の位置
     this.OriginalRow = Math.round(y / pieceSize);
 }

 Draw() {
     ctx.drawImage(this.Image, this.X, this.Y);
     ctx.drawImage(this.Outline, this.X, this.Y);
 }

 IsClick(x, y) {
     let s = pieceSize / 4;
     if (x < this.X + s || this.X + s * 5 < x || y < this.Y + s || this.Y + s * 5 < y) {
         return false;
     }
     return true;
 }
}

//ピースの生成
window.onload = async () => {
 let sourceImage = await createSourceImage(); // 元画像の読み込み（後述）

 // ピースの行数と列数の計算
 colMax = Math.floor(sourceImage.width / pieceSize);
 rowMax = Math.floor(sourceImage.height / pieceSize);

 // canvasのサイズ設定
 can.width = colMax * pieceSize * 2;
 can.height = rowMax * pieceSize * 2;

 pieces = [];
 for (let row = 0; row < rowMax; row++) {
     for (let col = 0; col < colMax; col++) {
         let image = await createPiece(sourceImage, row, col, rowMax, colMax, false);
         let outline = await createPiece(sourceImage, row, col, rowMax, colMax, true);
         pieces.push(new Piece(image, outline, col * pieceSize, row * pieceSize));
     }
 }
 drawAll(); // 全ピースの描画
};

//元の画像を読み込む処理
async function createSourceImage() {
    let can = document.getElementById('can');
    let imagePath = can.getAttribute('data-image-path');
    let image = new Image();
    return await new Promise((resolve, reject) => {
        image.src = imagePath;
        image.onload = () => {
            can.width = image.width;
            can.height = image.height;
            resolve(image);
        };
        image.onerror = (error) => {
            console.error("画像の読み込みに失敗しました: ", imagePath);
            reject(error);
        };
    });
}


//画像をCanvasに描画する処理
async function drawImageOnCanvas() {
    let image = await createSourceImage();
    let can = document.getElementById('can');
    let ctx = can.getContext('2d');
    ctx.drawImage(image, 0, 0, can.width, can.height); // 画像をCanvasに描画
}


// ページの読み込みが完了したら画像を描画する
window.onload = () => {
    drawImageOnCanvas();
};

//ピースの作成処理
async function createPiece(imagePath, row, col, rowMax, colMax, outlineOnly) {
    let image = new Image();
    return await new Promise(resolve => {
        image.src = imagePath;
        image.onload = () => {
            let canvas = document.createElement('canvas');
            let ctx = canvas.getContext('2d');
            let s = pieceSize / 4;  // 凹凸のサイズ

            canvas.width = s * 6;
            canvas.height = s * 6;

            if (ctx == null) {
                return null;
            }

            // ピースの輪郭を描画
            ctx.beginPath();
            ctx.moveTo(s, s);

            // 上辺の凹凸
            if (row > 0) {
                ctx.lineTo(s * 2, s);
                if ((row + col) % 2 === 0) {
                    ctx.arc(s * 3, s, s, Math.PI, 0, false); // 凸
                } else {
                    ctx.arc(s * 3, s, s, Math.PI, 0, true); // 凹
                }
                ctx.lineTo(s * 4, s);
            } else {
                ctx.lineTo(s * 5, s);
            }

            // 右辺の凹凸
            if (col < colMax - 1) {
                ctx.lineTo(s * 5, s * 2);
                if ((row + col) % 2 === 1) {
                    ctx.arc(s * 5, s * 3, s, Math.PI * 1.5, Math.PI * 0.5, false); // 凸
                } else {
                    ctx.arc(s * 5, s * 3, s, Math.PI * 1.5, Math.PI * 0.5, true); // 凹
                }
                ctx.lineTo(s * 5, s * 4);
            } else {
                ctx.lineTo(s * 5, s * 5);
            }

            // 下辺の凹凸
            if (row < rowMax - 1) {
                ctx.lineTo(s * 4, s * 5);
                if ((row + col) % 2 === 0) {
                    ctx.arc(s * 3, s * 5, s, 0, Math.PI, false); // 凸
                } else {
                    ctx.arc(s * 3, s * 5, s, 0, Math.PI, true); // 凹
                }
                ctx.lineTo(s * 2, s * 5);
            } else {
                ctx.lineTo(s, s * 5);
            }

            // 左辺の凹凸
            if (col > 0) {
                ctx.lineTo(s, s * 4);
                if ((row + col) % 2 === 1) {
                    ctx.arc(s, s * 3, s, Math.PI * 0.5, Math.PI * 1.5, false); // 凸
                } else {
                    ctx.arc(s, s * 3, s, Math.PI * 0.5, Math.PI * 1.5, true); // 凹
                }
                ctx.lineTo(s, s * 2);
            } else {
                ctx.lineTo(s, s);
            }

            ctx.closePath();
            ctx.clip();

            if (outlineOnly) {
                ctx.stroke();
            } else {
                ctx.drawImage(image, s - s * 4 * col, s - s * 4 * row, canvas.width, canvas.height, 0, 0, canvas.width, canvas.height);
            }

            resolve(canvas);
        };
    });
}

//ピースの描画
function drawAll() {
    ctx.clearRect(0, 0, can.width, can.height); // キャンバスをクリア

    // ピースの配置エリアの輪郭を描画
    let s = pieceSize / 4;
    ctx.strokeStyle = '#000';
    ctx.strokeRect(s, s, pieceSize * colMax, pieceSize * rowMax);

    // 全てのピースを描画
    pieces.forEach(piece => {
        piece.Draw();
    });

    // 移動中のピースがあれば最前面に描画
    if (movingPiece !== null) {
        movingPiece.Draw();
    }
}


// マウスボタンが押されたときの処理
window.addEventListener('mousedown', (ev) => {
    if (ev.button !== 0) { // 左クリック以外は無視
        return;
    }

    const rect = can.getBoundingClientRect();
    const mouseX = ev.clientX - rect.left;
    const mouseY = ev.clientY - rect.top;

    // クリックされたピースを探す
    movingPiece = pieces.find(piece => piece.IsClick(mouseX, mouseY));
    if (movingPiece) {
        oldX = movingPiece.X; // 移動前の位置を記録
        oldY = movingPiece.Y;
    }
});

// マウスが動いたときの処理
window.addEventListener('mousemove', (ev) => {
    if (movingPiece) {
        const rect = can.getBoundingClientRect();
        const newX = ev.clientX - rect.left - pieceSize / 2;
        const newY = ev.clientY - rect.top - pieceSize / 2;

        // ピースを移動
        movingPiece.X = newX;
        movingPiece.Y = newY;

        drawAll(); // 全ピースの再描画
    }
});

// マウスボタンを離したときの処理
window.addEventListener('mouseup', (ev) => {
    if (movingPiece) {
        const rect = can.getBoundingClientRect();
        const mouseX = ev.clientX - rect.left;
        const mouseY = ev.clientY - rect.top;

        // ピースを元の位置に戻すか、新しい位置に配置
        // ... ここでピースの配置処理 ...

        movingPiece = null; // 移動中のピースをクリア
        drawAll(); // 全ピースの再描画
    }
});
