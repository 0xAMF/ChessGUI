package GUI;

import com.example.engine.Player.MoveTransition;
import com.example.engine.board.Board;
import com.example.engine.board.Move;
import com.example.engine.board.Tile;
import javafx.scene.control.Label;

import java.util.List;

public class ChessGame {
    private Board board;

    public ChessGame() {
        board = Board.createStandardBoard();
    }

    public boolean makeMove(Tile fromTile, Tile destinationTile) {

        Move move = Move.MoveFactory.createMove(this.board, fromTile.getTileCoordinate(), destinationTile.getTileCoordinate());
        MoveTransition transition = this.board.currentPlayer().makeMove(move);

        System.out.println("\n===================== in ChessGame class =======================");
                                                /******DEBUGGING*******/

        System.out.println("Source Tile: " + fromTile.getTileCoordinate() + " |||| Destination Tile: " + destinationTile.getTileCoordinate());
        System.out.println("Move ================> " + move.getDestinationCoordinate());

        List<Move> whiteLegalMoves = this.board.whitePlayer().getLegalMoves();
        List<Move> BoardLegalMoves = this.board.getAllLegalMoves();
        List<Move> blackLegalMoves = this.board.blackPlayer().getLegalMoves();

        /*for (Move legalMoves : BoardLegalMoves) {
            System.out.println("Board legal move: " + legalMoves.getCurrentCoordinate() + " ====> " + legalMoves.getDestinationCoordinate());
        }*/
        System.out.println();
        for (Move legalMoves : whiteLegalMoves) {
            System.out.println("white legal move: " + legalMoves.getCurrentCoordinate() + " ====> " + legalMoves.getDestinationCoordinate());
        }
        System.out.println();
        for (Move legalMoves : blackLegalMoves) {
            System.out.println("black legal move: " + legalMoves.getCurrentCoordinate() + " ====> " + legalMoves.getDestinationCoordinate());
        }

        System.out.println("\n||||||||||| Testing legal moves for white |||||||||||");
        List<Move> whitePiecelegalMove = fromTile.getPiece().calcLegalMoves(this.board);
        for (Move whitePieceMove : whitePiecelegalMove) {
            System.out.println("This white piece has the move: " + whitePieceMove.getCurrentCoordinate() + " ====> " + whitePieceMove.getDestinationCoordinate());
        }



        if (transition.getMoveStatus().isDone()) {
            this.board = transition.getBoardAfterTransition();

            String currentPlayer = this.board.currentPlayer().getPlayerColor().toString();
            String pieces = this.board.currentPlayer().getActivePieces().toString();

            System.out.println(currentPlayer + " To move");
            //Label label = new Label(currentPlayer + "To move");

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