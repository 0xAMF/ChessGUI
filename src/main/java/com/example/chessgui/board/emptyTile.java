package com.example.chessgui.board;

import com.example.chessgui.pieces.Piece;

public class emptyTile extends Tile {

    emptyTile(int tileCoordinate){
        super(tileCoordinate);
    }
    @Override
    public boolean isTileOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}
