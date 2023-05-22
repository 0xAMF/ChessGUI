package com.example.engine.pieces;

import com.example.engine.PieceColor;
import com.example.engine.board.Board;
import com.example.engine.board.BoardStructure;
import com.example.engine.board.Move;
import com.example.engine.board.Tile;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public final class King extends Piece {
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

    private final boolean isCastled;
    private final boolean kingSideCastleCapable;
    private final boolean queenSideCastleCapable;

    public King(final PieceColor PieceColor,
            final int piecePosition,
            final boolean kingSideCastleCapable,
            final boolean queenSideCastleCapable) {
        super(piecePosition, PieceColor, PieceType.KING, true);
        this.isCastled = false;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public King(final PieceColor PieceColor,
            final int piecePosition,
            final boolean isFirstMove,
            final boolean isCastled,
            final boolean kingSideCastleCapable,
            final boolean queenSideCastleCapable) {
        super(piecePosition, PieceColor, PieceType.KING, isFirstMove);
        this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    public boolean isKingSideCastleCapable() {
        return this.kingSideCastleCapable;
    }

    public boolean isQueenSideCastleCapable() {
        return this.queenSideCastleCapable;
    }

    @Override
    public List<Move> calcLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardStructure.isValidCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
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
                            legalMoves.add(new Move.attackMove(board, pieceAtDestination,
                                    candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof King)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final King king = (King) other;
        return isCastled == king.isCastled;
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode()) + (isCastled ? 1 : 0);
    }

    private static boolean isFirstColumnExclusion(final int currentCandidate,
            final int candidateOffset) {
        return BoardStructure.FIRST_COLUMN[currentCandidate]
                && ((candidateOffset == -9) || (candidateOffset == -1) ||
                        (candidateOffset == 7));
    }

    private static boolean isEighthColumnExclusion(final int currentCandidate,
            final int candidateOffset) {
        return BoardStructure.EIGHTH_COLUMN[currentCandidate]
                && ((candidateOffset == -7) || (candidateOffset == 1) ||
                        (candidateOffset == 9));
    }

    @Override
    public Piece movePiece(Move move) {
        return new King(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate(), false, false);
    }

    @Override
    public ImageView getPieceIcon() {
        if (this.getPieceColor() == PieceColor.BLACK) {
            return new ImageView("C:\\Users\\Ahmed\\Desktop\\Tessst\\ChessGUI\\src\\main\\java\\PieceIcon\\blackKing.png");
        }
        else {
            return new ImageView("C:\\Users\\Ahmed\\Desktop\\Tessst\\ChessGUI\\src\\main\\java\\PieceIcon\\whiteKing.png");
        }
    }

}
