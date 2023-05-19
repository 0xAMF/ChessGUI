package com.example.chessgui.board;

import java.util.Arrays;

public class BoardStructure {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);
    
    public static final int NUM_OF_TILES = 64;
    public static final int TILES_PER_ROW = 8;


    private static boolean[] initColumn(int columnNumber) {
        final boolean[] columns = new boolean[NUM_OF_TILES];
        Arrays.fill(columns, false);
        do {
            columns[columnNumber] = true;
            columnNumber += TILES_PER_ROW;
        }while (columnNumber < NUM_OF_TILES);
        return columns;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] rows = new boolean[NUM_OF_TILES];
        Arrays.fill(rows, false);
        do {
            rows[rowNumber] = true;
            rowNumber++;
        }while(rowNumber % TILES_PER_ROW != 0);

        return rows;
    }

    public static boolean isValidCoordinate(int tileCoordinate){
        return tileCoordinate >=0 && tileCoordinate < NUM_OF_TILES;
    }
}
