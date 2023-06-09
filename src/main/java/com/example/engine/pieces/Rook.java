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

public final class Rook extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -8, -1, 1, 8 };

    public Rook(final PieceColor pieceColor, final int piecePosition) {
        super(piecePosition, pieceColor, PieceType.ROOK, true);
    }

    public Rook(final PieceColor pieceColor,
            final int piecePosition,
            final boolean isFirstMove) {
        super(piecePosition, pieceColor, PieceType.ROOK, isFirstMove);
    }

    @Override
    public List<Move> calcLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardStructure.isValidCoordinate(candidateDestinationCoordinate)) {
                if (isColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
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
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
        return legalMoves;
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    private static boolean isColumnExclusion(final int position,
            final int offset) {
        return (BoardStructure.FIRST_COLUMN[position] && (offset == -1)) ||
                (BoardStructure.EIGHTH_COLUMN[position] && (offset == 1));
    }
    
    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate());
    }

    @Override
    public ImageView getPieceIcon() throws FileNotFoundException {
        if (this.getPieceColor() == PieceColor.BLACK) {
            Image blackRook = new Image(new FileInputStream("PieceIcon/blackRook.png"));
            return new ImageView(blackRook);
        }
        else {
            Image whiteRook = new Image(new FileInputStream("PieceIcon/whiteRook.png"));
            return new ImageView(whiteRook);
        }
    }

}