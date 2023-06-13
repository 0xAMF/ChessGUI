package com.example.engine.pieces;

import com.example.engine.PieceColor;
import com.example.engine.board.Board;
import com.example.engine.board.BoardStructure;
import com.example.engine.board.Move;
import com.example.engine.board.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public Queen(final PieceColor pieceColor, final int piecePosition) {
        super(piecePosition, pieceColor, PieceType.QUEEN, false);
    }

    @Override
    public List<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardStructure.isValidCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffset;

                if (BoardStructure.isValidCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.pieceMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final PieceColor piece_color = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != piece_color) {
                            //legalMoves.add(new Move.attackMove(board, pieceAtDestination, candidateDestinationCoordinate, pieceAtDestination));
                            legalMoves.add(new Move.pieceMove(board, this, candidateDestinationCoordinate));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }
    
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardStructure.FIRST_COLUMN[currentPosition]
                && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardStructure.EIGHTH_COLUMN[currentPosition]
                && (candidateOffset == 1 || candidateOffset == -7 || candidateOffset == 9);
    }

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate());
    }

    @Override
    public ImageView getPieceIcon() throws FileNotFoundException {
        if (this.getPieceColor() == pieceColor.BLACK) {
            Image blackQueen = new Image(new FileInputStream("PieceIcon/blackQueen.png"));
            return new ImageView(blackQueen);
        }
        else {
            Image whiteQueen = new Image(new FileInputStream("PieceIcon/whiteQueen.png"));
            return new ImageView(whiteQueen);
        }
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

}
