package jp.jigsawPuzzle.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class PuzzleGame extends JFrame {
    private final int pieceCount = 9;
    private final int gridSize = 3;
    private final int imageSize = 200;
    private final Image[] pieces;
    private final int[] solution;
    private int[] piecesOrder;
    private int emptyIndex;

    public PuzzleGame() {
        setTitle("Puzzle Game");
        setSize(imageSize * gridSize, imageSize * gridSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pieces = new Image[pieceCount];
        solution = new int[pieceCount];
        piecesOrder = new int[pieceCount];

        loadImage();
        initSolution();
        shufflePieces();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / imageSize;
                int y = e.getY() / imageSize;
                int index = x + y * gridSize;
                if (isValidMove(index)) {
                    swapPieces(index);
                    repaint();
                    if (isSolved()) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
                    }
                }
            }
        });
    }

    private void loadImage() {
        ImageIcon icon = new ImageIcon("path_to_your_image.jpg");
        Image image = icon.getImage();
        for (int i = 0; i < pieceCount; i++) {
            pieces[i] = createImage(new FilteredImageSource(image.getSource(),
                    new CropImageFilter((i % gridSize) * imageSize, (i / gridSize) * imageSize,
                            imageSize, imageSize)));
        }
    }

    private void initSolution() {
        for (int i = 0; i < pieceCount; i++) {
            solution[i] = i;
        }
        emptyIndex = pieceCount - 1;
    }

    private void shufflePieces() {
        System.arraycopy(solution, 0, piecesOrder, 0, pieceCount);
        for (int i = pieceCount - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = piecesOrder[i];
            piecesOrder[i] = piecesOrder[j];
            piecesOrder[j] = temp;
        }
    }

    private boolean isValidMove(int index) {
        int row1 = emptyIndex / gridSize;
        int col1 = emptyIndex % gridSize;
        int row2 = index / gridSize;
        int col2 = index % gridSize;
        return (Math.abs(row1 - row2) + Math.abs(col1 - col2)) == 1;
    }

    private void swapPieces(int index) {
        int temp = piecesOrder[index];
        piecesOrder[index] = piecesOrder[emptyIndex];
        piecesOrder[emptyIndex] = temp;
        emptyIndex = index;
    }

    private boolean isSolved() {
        for (int i = 0; i < pieceCount; i++) {
            if (piecesOrder[i] != solution[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < pieceCount; i++) {
            int row = i / gridSize;
            int col = i % gridSize;
            int x = col * imageSize;
            int y = row * imageSize;
            g.drawImage(pieces[piecesOrder[i]], x, y, this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PuzzleGame puzzleGame = new PuzzleGame();
            puzzleGame.setVisible(true);
        });
    }
}
