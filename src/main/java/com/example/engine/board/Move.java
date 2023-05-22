package com.example.engine.board;

import com.example.engine.board.Board.Builder;
import com.example.engine.pieces.Pawn;
import com.example.engine.pieces.Piece;
import com.example.engine.pieces.Rook;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    public static final Move NULL_MOVE = new NullMove();

    Move(final Board board, final Piece movedPiece, final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getMoveDestinationCoordinate() {
        return this.destinationCoordinate;
    }
    
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        return result;
    }
    
    public boolean equals(final Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof Move)){
            return false;
        }
        final Move otherMove = (Move) other;
        return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
               getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public int getCurrentCoordinate(){
        return this.getMovedPiece().getPiecePosition();
    }
    
    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
    
    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isAttack(){
        return false;
    }
    
    public boolean isCastlingMove(){
        return false;
    }
    
    public Piece getAttackedPiece(){
        return null;
    }
   
    public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
                // builder.setPiece(piece);
            }
            //move the moved piece
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getPlayerColor());
            return builder.build();
    }


    public static final class pieceMove extends Move{
        public pieceMove(final Board board, final Piece movedPiece, final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }
    
    public static class attackMove extends Move{
        final Piece attackedPiece;
        public attackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }
        @Override
        public boolean equals(final Object other){
            if (this == other){
                return true;
            }
            if (!(other instanceof Move)){
                return false;
            }
            final attackMove otherAttackMove = (attackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.attackedPiece);
        }
        @Override
        public Board execute(){
            return null;
        }
        @Override
        public boolean isAttack(){
            return true;
        }
        @Override
        public Piece getAttackedPiece(){
        return this.attackedPiece;
        }
    }
    
    public static final class PawnMove extends Move{
        public PawnMove(final Board board, final Piece movedPiece, final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }
    
    public static class PawnAttackMove extends attackMove{
        public PawnAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }
    
    public static final class PawnJump extends Move{
        public PawnJump(final Board board, final Piece movedPiece, final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
        
        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                    builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getPlayerColor());
            return builder.build();
        }
    }
    
    static abstract class CastleMove extends Move{
        
        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;
        
        public CastleMove(final Board board, 
                          final Piece movedPiece, 
                          final int destinationCoordinate,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination){
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }
        
        public Rook getCastleRook(){
            return this.castleRook;
        }
        
        @Override
        public boolean isCastlingMove(){
            return true;
        }
        
        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece) ){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                    builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRook.getPieceColor(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getPlayerColor());
            return builder.build();
        }
        
    }
    
    public static final class KingSideCastleMove extends CastleMove{
        public KingSideCastleMove(final Board board, 
                                  final Piece movedPiece, 
                                  final int destinationCoordinate,
                                  final Rook castleRook,
                                  final int castleRookStart,
                                  final int castleRookDestination){
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        @Override
        public String toString() {
            return "0-0";
        }
    }
    
    public static final class QueenSideCastleMove extends CastleMove{
        public QueenSideCastleMove(final Board board, 
                                   final Piece movedPiece, 
                                   final int destinationCoordinate,final Rook castleRook,
                                   final int castleRookStart,
                                   final int castleRookDestination){
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        @Override
        public String toString() {
            return "0-0-0";
        }
    }
    
    public static final class NullMove extends Move{
        public NullMove(){
            super(null, null ,-1);
        }
        public Board execute() {
            throw new RuntimeException("Cannot execute the null move!");
        }
    }
    
    public static class MoveFactory{
        public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate){
            for(final Move move : board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }

}
