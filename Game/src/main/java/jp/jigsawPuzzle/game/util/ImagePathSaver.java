package jp.jigsawPuzzle.game.util;

import java.io.File;
import java.sql.SQLException;

import javax.naming.NamingException;

import jp.jigsawPuzzle.game.dao.PuzzleDAO;

public class ImagePathSaver {
	private static final String IMAGES_BASE_PATH = "/WEB-INF/images";
	private static final String[] CATEGORIES = {"horror", "comedy", "mystery", "anime"};
	private PuzzleDAO puzzleDAO;
	
	public ImagePathSaver() {
		puzzleDAO = new PuzzleDAO();
	}
	public void saveImagesToDatabase() {
		for (String category : CATEGORIES) {
			File folder = new File(IMAGES_BASE_PATH + "/" + category);
			File[] listOfFiles = folder.listFiles();
			
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String filePath = "images/" + category + "/" + file.getName();
					try {
						puzzleDAO.saveImagePath(category, filePath);
					} catch (SQLException | NamingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		ImagePathSaver saver = new ImagePathSaver();
		saver.saveImagesToDatabase();
	}
}