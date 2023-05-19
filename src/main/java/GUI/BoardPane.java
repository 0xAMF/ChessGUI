package GUI;

import com.example.chessgui.PieceColor;
import com.example.chessgui.board.Board;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardPane extends GridPane {

    private static final int BOARD_SIZE = 8;
    private Board board;
    private int tileCount;
    public final int[] TileId = new int[65];
    private Button[][] boardButtons = new Button[BOARD_SIZE][BOARD_SIZE];

    BoardPane(Board board) {
        this.board = board;
    }

    public void removePieces(Board board, GridPane pane) {
        tileCount = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getTile(TileId[tileCount]).isTileOccupied()) {
                    /*???*/
                    tileCount++;
                }
            }
        }
    }


    public void setBoardPieces(Board board, GridPane pane) {
        tileCount = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                //Setting up pieces on the board
                if (board.getTile(TileId[tileCount]).isTileOccupied()) {
                    pane.add(board.getTile(TileId[tileCount]).getPiece().getPieceIcon(), j, i);
                }
                tileCount++;
                TileId[tileCount] = tileCount;
            }
        }
    }
}
