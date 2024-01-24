package jp.jigsawPuzzle.game.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import jp.jigsawPuzzle.game.bean.PuzzleBean;
import jp.jigsawPuzzle.game.util.DbSource;

public class PuzzleDAO {
	
	// すべてのパズルを取得するメソッド
	public ArrayList<PuzzleBean> getAllPuzzles() throws SQLException, NamingException {
		Connection connection = null;
		
		String sql = "SELECT * FROM puzzles";
		
		ArrayList<PuzzleBean> puzzles = new ArrayList<PuzzleBean>();
		try {
			// データベース接続を確立
			connection = DbSource.getDateSource().getConnection();
			// SQLクエリをセットして、実行する
			PreparedStatement statement = connection.prepareStatement(sql);
						
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("puzzle_id");
				String title = resultSet.getString("title");
				String category = resultSet.getString("category");
				String imagePath = resultSet.getString("image_path");
				int pieceCount = resultSet.getInt("piece_count");
				puzzles.add(new PuzzleBean(id, title, category, imagePath, pieceCount));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return puzzles;
	}
	
	// 画像パスをデータベースに保存するメソッド
	public void saveImagePath(String category, String imagePath) throws SQLException, NamingException {
		
		Connection connection = null;
		String sql = "INSERT INTO Puzzles (category, image_path) VALUES (?, ?)";
		
		try  {
			// データベースへの接続を確立
			connection = DbSource.getDateSource().getConnection();
			// SQL文を準備して、値を設定
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, category);
			statement.setString(2, imagePath);
			// SQL文を実行
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// すべてのユニークなカテゴリーを取得するメソッド
	public ArrayList<PuzzleBean> getAllCategories() throws SQLException, NamingException {
		ArrayList<PuzzleBean> categories = new ArrayList<PuzzleBean>();
		String sql = "SELECT DISTINCT category FROM Puzzles";
		Connection connection = null;
		try  {
			connection = DbSource.getDateSource().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				PuzzleBean category = new PuzzleBean();
				category.setCategory(resultSet.getString("category"));
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return categories;
	}
	
	public PuzzleBean getRandomPuzzleByCategory(String category) throws NamingException {
		PuzzleBean puzzle = null;
	    String sql = "SELECT * FROM Puzzles WHERE category = ? ORDER BY RAND() LIMIT 1";
	    Connection connection = null;
	    try {
	    	connection = DbSource.getDateSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, category);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            // レコードからPuzzleオブジェクトを生成
	            int id = resultSet.getInt("puzzle_id");
	            String title = resultSet.getString("title");
	            String imagePath = resultSet.getString("image_path");
	            int pieceCount = resultSet.getInt("piece_count");
	            puzzle = new PuzzleBean(id, title, category, imagePath, pieceCount);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return puzzle;
	}
	
	// 特定カテゴリーからランダムなパズルIDを取得するメソッド
	public int getRandomPuzzleIdByCategory(String category) throws NamingException {
		int puzzleId = -1;
		String sql = "SELECT puzzle_id FROM Puzzles WHERE category = ? ORDER BY RAND() LIMIT 1";
		Connection connection = null;
		try  {
			connection = DbSource.getDateSource().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, category);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				puzzleId = resultSet.getInt("puzzle_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return puzzleId;
	}
}