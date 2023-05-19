package com.example.chessgui.Player;

import com.example.chessgui.PieceColor;
import com.example.chessgui.board.Board;
import com.example.chessgui.board.Move;
import com.example.chessgui.board.Tile;
import com.example.chessgui.pieces.Piece;
import com.example.chessgui.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class BlackPlayer extends Player{
    public BlackPlayer(final Board board,
                       final List<Move> whiteStandardLegalMoves,
                       final List<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public PieceColor getPlayerColor() {
        return PieceColor.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
    
    @Override
    protected List<Move> calculateKingCastels(final List<Move> playerLegals, final List<Move> opponentsLegals) {
        final List<Move> kingCastles =  new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            //whites king side castle
            if(this.board.getTile(5).isTileOccupied() && this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
                       calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
                       rookTile.getPiece().getPieceType().isRook() ) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, 
                                                                    this.playerKing, 
                                                                    6, 
                                                                    (Rook)rookTile.getPiece(), 
                                                                    rookTile.getTileCoordinate(), 
                                                                    5));
                    }
                }
            }
            //whites queen side castle
            if(this.board.getTile(1).isTileOccupied() && 
                this.board.getTile(1).isTileOccupied() && 
                this.board.getTile(3).isTileOccupied()) {
                    final Tile rookTile = this.board.getTile(0);
                    if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                        if(calculateAttacksOnTile(2, opponentsLegals).isEmpty() &&
                           calculateAttacksOnTile(3, opponentsLegals).isEmpty() &&
                           rookTile.getPiece().getPieceType().isRook() ){
                            kingCastles.add(new Move.QueenSideCastleMove(this.board, 
                                                                        this.playerKing, 
                                                                        2, 
                                                                        (Rook)rookTile.getPiece(), 
                                                                        rookTile.getTileCoordinate(), 
                                                                        3));
                        }
                    }
            }
        }
        return null;
    }
}
