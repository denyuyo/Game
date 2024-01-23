package jp.jigsawPuzzle.game;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.jigsawPuzzle.game.bean.PuzzleBean;
import jp.jigsawPuzzle.game.dao.PuzzleDAO;
import jp.jigsawPuzzle.game.util.PropertyLoader;

/**
 * Servlet implementation class PuzzleServlet
 */
//@WebServlet("/PuzzleServlet")
public class PuzzleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PuzzleServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category");
        PuzzleDAO dao = new PuzzleDAO();
        PuzzleBean puzzle = null;
		try {
			puzzle = dao.getRandomPuzzleByCategory(category);
		} catch (NamingException e) {
			e.printStackTrace();
		}
        request.setAttribute("puzzle", puzzle);
        
		String resultPage = PropertyLoader.getProperty("url.jsp.puzzle");
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultPage = PropertyLoader.getProperty("url.game.success");
		response.sendRedirect(resultPage);
	}

}
