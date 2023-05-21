package com.example.engine.board;

import com.example.engine.PieceColor;
import com.example.engine.Player.BlackPlayer;
import com.example.engine.Player.Player;
import com.example.engine.Player.WhitePlayer;
import com.example.engine.pieces.*;
import com.example.engine.pieces.Pawn;

import java.util.*;

public class Board {
    private final List<Tile> gameBoard;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private Board(final Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, PieceColor.WHITE);
        this.blackPieces= calculateActivePieces(this.gameBoard, PieceColor.BLACK);

        List<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        List<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    public List<Piece> getBlackPieces (){
        return this.blackPieces;
    }

    public List<Piece> getWhitePieces (){
        return this.whitePieces;
    }

    private List<Move> calculateLegalMoves(List<Piece> pieces) {
        List<Move> legalMoves = new ArrayList<>();
        for (Piece piece : pieces){
            legalMoves.addAll(piece.calcLegalMoves(this));
        }
        return legalMoves;
    }

    private List<Piece> calculateActivePieces(List<Tile> gameBoard, PieceColor pieceColor){
        final List<Piece> activePieces = new ArrayList<>();
        for (Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceColor() == pieceColor) {
                    activePieces.add(piece);
                }
            }
        }
        return activePieces;
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    private List<Tile> createGameBoard(final Builder builder) {
        Tile[] tiles = new Tile[BoardStructure.NUM_OF_TILES];
        for (int i = 0; i < BoardStructure.NUM_OF_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return Arrays.asList(tiles);
    }


    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        // Black pieces
        builder.setPiece(new Rook(PieceColor.BLACK, 0));
        builder.setPiece(new Knight(PieceColor.BLACK, 1));
        builder.setPiece(new Bishop(PieceColor.BLACK, 2));
        builder.setPiece(new Queen(PieceColor.BLACK, 3));
        builder.setPiece(new King(PieceColor.BLACK, 4, true, true));
        builder.setPiece(new Bishop(PieceColor.BLACK, 5));
        builder.setPiece(new Knight(PieceColor.BLACK, 6));
        builder.setPiece(new Rook(PieceColor.BLACK, 7));
        builder.setPiece(new Pawn(PieceColor.BLACK, 8, true));
        builder.setPiece(new Pawn(PieceColor.BLACK, 9, true));
        builder.setPiece(new Pawn(PieceColor.BLACK, 10, true));
        builder.setPiece(new Pawn(PieceColor.BLACK, 11, true));
        builder.setPiece(new Pawn(PieceColor.BLACK, 12, true));
        builder.setPiece(new Pawn(PieceColor.BLACK, 13, true));
        builder.setPiece(new Pawn(PieceColor.BLACK, 14, true));
        builder.setPiece(new Pawn(PieceColor.BLACK, 15, true));
        // White pieces
        builder.setPiece(new Pawn(PieceColor.WHITE, 48, true));
        builder.setPiece(new Pawn(PieceColor.WHITE, 49, true));
        builder.setPiece(new Pawn(PieceColor.WHITE, 50, true));
        builder.setPiece(new Pawn(PieceColor.WHITE, 51, true));
        builder.setPiece(new Pawn(PieceColor.WHITE, 52, true));
        builder.setPiece(new Pawn(PieceColor.WHITE, 53, true));
        builder.setPiece(new Pawn(PieceColor.WHITE, 54, true));
        builder.setPiece(new Pawn(PieceColor.WHITE, 55, true));
        builder.setPiece(new Rook(PieceColor.WHITE, 56));
        builder.setPiece(new Knight(PieceColor.WHITE, 57));
        builder.setPiece(new Bishop(PieceColor.WHITE, 58));
        builder.setPiece(new Queen(PieceColor.WHITE, 59));
        builder.setPiece(new King(PieceColor.WHITE, 60, true, true));
        builder.setPiece(new Bishop(PieceColor.WHITE, 61));
        builder.setPiece(new Knight(PieceColor.WHITE, 62));
        builder.setPiece(new Rook(PieceColor.WHITE, 63));
        //white to move
        builder.setMoveMaker(PieceColor.WHITE);
        //build the board
        return builder.build();
    }

    public Player blackPlayer() {
        return this.blackPlayer;
    }

    public Player whitePlayer() {
        return this.whitePlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }


    public List<Move> getAllLegalMoves() {
        List<Move> AllLegalMoves = new ArrayList<>();
        AllLegalMoves.addAll(this.whitePlayer.getLegalMoves());
        AllLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        return AllLegalMoves;
    }

    //using the builder design pattern.
    public static class Builder{
        private Map<Integer, Piece> boardConfig;
        private PieceColor nextMoveMaker;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }
        public Builder setMoveMaker(PieceColor nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

        public Builder removePiece(int tileCoordinate) {
            this.boardConfig.put(tileCoordinate, null);
            return this;
        }

        ///FOR NOW
        void setEnpassantPawn(Pawn movedPawn) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
