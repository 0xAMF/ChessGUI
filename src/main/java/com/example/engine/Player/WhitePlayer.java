package com.example.engine.Player;

import com.example.engine.PieceColor;
import com.example.engine.board.Board;
import com.example.engine.board.Move;
import com.example.engine.board.Tile;
import com.example.engine.pieces.Piece;
import com.example.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class WhitePlayer extends Player{
    public WhitePlayer(final Board board,
                       final List<Move> whiteStandardLegalMoves,
                       final List<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public PieceColor getPlayerColor() {
        return PieceColor.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected List<Move> calculateKingCastels(final List<Move> playerLegals, final List<Move> opponentsLegals) {
        final List<Move> kingCastles =  new ArrayList<>();        
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            //whites king side castle
            if(this.board.getTile(61).isTileOccupied() && this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
                       rookTile.getPiece().getPieceType().isRook() ) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, 
                                                                    this.playerKing, 
                                                                    62, 
                                                                    (Rook)rookTile.getPiece(), 
                                                                    rookTile.getTileCoordinate(), 
                                                                    61));
                    }
                }
            }
            //whites queen side castle
            if(this.board.getTile(59).isTileOccupied() && 
                this.board.getTile(58).isTileOccupied() && 
                this.board.getTile(57).isTileOccupied()) {
                    final Tile rookTile = this.board.getTile(56);
                    if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                        if(Player.calculateAttacksOnTile(59, opponentsLegals).isEmpty() &&
                           Player.calculateAttacksOnTile(58, opponentsLegals).isEmpty() &&
                           rookTile.getPiece().getPieceType().isRook() ){
                            kingCastles.add(new Move.QueenSideCastleMove(this.board, 
                                                                        this.playerKing, 
                                                                        58, 
                                                                        (Rook)rookTile.getPiece(), 
                                                                        rookTile.getTileCoordinate(), 
                                                                        59));
                        }
                    }
            }
        }
        return null;
    }
}
