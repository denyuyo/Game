package jp.jigsawPuzzle.game.bean;

public class PuzzleBean {
	
	private int puzzleId;
	private String title;
	private String category;
	private String imagePath;
	private int pieceCount;
	
	public int getPuzzleId() {
		return puzzleId;
	}
	public void setPuzzleId(int puzzleId) {
		this.puzzleId = puzzleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getPieceCount() {
		return pieceCount;
	}
	public void setPieceCount(int pieceCount) {
		this.pieceCount = pieceCount;
	}
}
