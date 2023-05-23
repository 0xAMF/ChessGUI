package com.example.engine.pieces;

import com.example.engine.PieceColor;
import com.example.engine.board.Board;
import com.example.engine.board.BoardStructure;
import com.example.engine.board.Move;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = { 8, 16, 7, 9 };

    public Pawn(final PieceColor pieceColor, final int piecePosition,final boolean isFirstMove) {
        super(piecePosition, pieceColor, PieceType.PAWN, isFirstMove);
    }

    @Override
    public List<Move> calcLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * candidateCoordinateOffset);

            if (!BoardStructure.isValidCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (candidateCoordinateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.pieceMove(board, this, candidateDestinationCoordinate));
            } else if (candidateCoordinateOffset == 16 && this.isFirstMove() &&
                    (BoardStructure.SECOND_ROW[this.piecePosition] && this.getPieceColor().isBlack()) ||
                    (BoardStructure.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isWhite())) {
                final int behindCandidateDestinationCoordinate = this.piecePosition
                        + (this.getPieceColor().getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.pieceMove(board, this, candidateDestinationCoordinate));
                }
            } else if (candidateCoordinateOffset == 7 &&
                    !((BoardStructure.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
                            (BoardStructure.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(new Move.pieceMove(board, this, candidateDestinationCoordinate));
                    }
                }

            } else if (candidateCoordinateOffset == 9 &&
                    !((BoardStructure.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
                            (BoardStructure.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(new Move.pieceMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new Pawn(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate(), false);
    }

    @Override
    public ImageView getPieceIcon() {
        if (this.getPieceColor() == PieceColor.BLACK) {
            return new ImageView("C:\\Users\\Ahmed\\Desktop\\Tessst\\ChessGUI\\src\\main\\java\\PieceIcon\\blackPawn.png");
        }
        else {
            return new ImageView("C:\\Users\\Ahmed\\Desktop\\Tessst\\ChessGUI\\src\\main\\java\\PieceIcon\\whitePawn.png");
        }
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    
}
