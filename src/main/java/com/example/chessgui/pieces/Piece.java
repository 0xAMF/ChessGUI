package com.example.chessgui.pieces;

import com.example.chessgui.PieceColor;
import com.example.chessgui.board.Board;
import com.example.chessgui.board.Move;
import javafx.scene.image.ImageView;

import java.util.List;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final PieceColor pieceColor;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final int piecePosition, final PieceColor pieceColor, PieceType pieceType, final boolean isFirstMove){
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }
    
    private int computeHashCode(){
        int result = pieceType.hashCode();
        result = 31 * result + pieceColor.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                pieceColor == otherPiece.getPieceColor() && isFirstMove == otherPiece.isFirstMove();
    }
    
    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }
    
    public PieceType getPieceType() {
        return this.pieceType;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public PieceColor getPieceColor(){
        return this.pieceColor;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    //Legal moves list method
    public abstract List<Move> calcLegalMoves(final Board board);
    
    public abstract Piece movePiece(Move move);

    public abstract ImageView getPieceIcon();

    public enum PieceType {
        PAWN("Pawn") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("Knight") {
            @Override
            public boolean isKing() {
                return false;
            }
            
            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP( "Bishop") {
            @Override
            public boolean isKing() {
                return false;
            }
            
            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK( "Rook") {
            @Override
            public boolean isKing() {
                return false;
            }
            
            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN( "Queen") {
            @Override
            public boolean isKing() {
                return false;
            }
            
            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("King") {
            @Override
            public boolean isKing() {
                return true;
            }
            
            @Override
            public boolean isRook() {
                return false;
            }
        };

        private String pieceName;

        PieceType(String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
        public abstract boolean isKing();

        public abstract boolean isRook();
    }


}
