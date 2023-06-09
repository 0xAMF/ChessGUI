package com.example.engine.board;

import com.example.engine.pieces.Piece;

public class occupiedTile extends Tile {
    Piece occupyingPiece;
    occupiedTile(int tileCoordinate, Piece occupyingPiece){
        super(tileCoordinate);
        this.occupyingPiece = occupyingPiece;
    }

    @Override
    public boolean isTileOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return this.occupyingPiece;
    }
}
