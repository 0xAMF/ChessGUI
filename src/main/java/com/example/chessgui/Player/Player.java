package com.example.chessgui.Player;

import com.example.chessgui.PieceColor;
import com.example.chessgui.board.Board;
import com.example.chessgui.board.Move;
import com.example.chessgui.pieces.King;
import com.example.chessgui.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final List<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final List<Move> legalMoves,
           final List<Move> opponentMoves) {

        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    protected static List<Move> calculateAttacksOnTile(int piecePosition, List<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : moves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }

    private King establishKing() {
        for (Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing())
                return (King) piece;
        }
        return null;
    }

    /*TODO implement following methods*/
    public boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStalemate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {
        for (final Move move : this.legalMoves) {
            final MoveTransition transitions = makeMove(move);
            if (transitions.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCastled() {
        return false;
    }

    public MoveTransition makeMove(Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final List<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    private Piece getPlayerKing() {
        return this.playerKing;
    }

    public abstract List<Piece> getActivePieces();
    public abstract PieceColor getPlayerColor();
    public abstract Player getOpponent();
    protected abstract List<Move> calculateKingCastels(List<Move> playerLegals, List<Move> opponentsLegals);
}
