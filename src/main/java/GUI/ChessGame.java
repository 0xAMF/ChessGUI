package GUI;

import com.example.engine.Player.MoveTransition;
import com.example.engine.board.Board;
import com.example.engine.board.Move;
import com.example.engine.board.Tile;

import java.util.List;

public class ChessGame {
    private Board board;

    public ChessGame() {
        board = Board.createStandardBoard();
    }

    public boolean makeMove(Tile fromTile, Tile destinationTile) {
        Move move = Move.MoveFactory.createMove(this.board, fromTile.getTileCoordinate(), destinationTile.getTileCoordinate());
        MoveTransition transition = this.board.currentPlayer().makeMove(move);

        if (transition.getMoveStatus().isDone()) {
            this.board = transition.getBoardAfterTransition();
            String currentPlayer = this.board.currentPlayer().getPlayerColor().toString();
            String pieces = this.board.currentPlayer().getActivePieces().toString();
            System.out.println(currentPlayer + " To move");
            System.out.println(pieces);
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return board.currentPlayer().isInCheckMate();
    }

    public Board getBoard() {
        return this.board;
    }
}