package jp.jigsawPuzzle.game.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.jigsawPuzzle.game.bean.PuzzleBean;

public class PuzzleDAO {
	
	public List<PuzzleBean> getPuzzlesByCategory(PuzzleBean category) throws SQLException, NamingException {
		// データベースから特定のジャンルのパズルを取得して返す
		Connection connection = null;
		
		
		return null;
	}
	
	public PuzzleBean getPuzzle(PuzzleBean puzzleId) throws SQLException, NamingException {
		// データベースから特定のIDのパズルを取得して返す
		return null;
	}
	
	public ArrayList<PuzzleBean> getAllPuzzles() throws SQLException, NamingException {
		// データベースからすべてのパズルを取得して返す
		Connection connection = null;
		
		ArrayList<PuzzleBean> puzzles = new ArrayList<PuzzleBean>();
		return null;
	}
}