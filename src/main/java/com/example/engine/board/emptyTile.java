package com.example.engine.board;

import com.example.engine.pieces.Piece;

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
