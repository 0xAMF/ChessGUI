package GUI;



import com.example.engine.board.Board;
import com.example.engine.board.Move;
import com.example.engine.board.Tile;
import com.example.engine.pieces.Piece;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class test extends Application {

    private final int BOARD_SIZE = 8;
    private final int TILE_SIZE = 80;
    private Button[][] boardButtons;
    private Button button;
    private Tile fromTile = null;
    private Tile destinationTile = null;
    private Piece movedPiece = null;
    int[] TileId = new int[65];
    int tileCount = 0;

    GridPane chessboard = new GridPane();
    BorderPane borderPane = new BorderPane();
    ChessGame game;

    @Override
    public void start(Stage primaryStage) throws Exception {

        game = new ChessGame();

        chessboard = createBoardGUI();
        setBoardPieces(game.getBoard());

        borderPane.setCenter(chessboard);

        Scene scene = new Scene(borderPane, 800, 800);
        primaryStage.setTitle("Very Bad Chess");
        primaryStage.getIcons().add(new Image("C:\\Users\\Ahmed\\Desktop\\Tessst\\ChessGUI\\src\\main\\java\\PieceIcon\\icon.jpeg"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createBoardGUI() {
        GridPane chessboard = new GridPane();
        chessboard.setAlignment(Pos.CENTER);
        chessboard.setHgap(1);
        chessboard.setVgap(1);

        boardButtons = new Button[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button tileButton = new Button();
                tileButton.setPrefSize(TILE_SIZE, TILE_SIZE);
                tileButton.setStyle("-fx-background-color: " + ((row + col) % 2 == 0 ? "white" : "gray"));
                int finalRow = row;
                int finalCol = col;
                tileButton.setOnMouseClicked(event -> handleTileClicked(finalRow, finalCol, event));
                boardButtons[row][col] = tileButton;
                chessboard.add(tileButton, col, row);
            }
        }
        return chessboard;
    }

    public void setBoardPieces(Board board) {
        tileCount = 0;
        ImageView pieceIcon;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Tile tile = board.getTile(tileCount);
                pieceIcon = null;
                if (tile.isTileOccupied()) {
                    //System.out.println(tile.getPiece().getPieceColor().toString() + " " + tile.getPiece().toString() + " on board");
                    pieceIcon = tile.getPiece().getPieceIcon();
                }
                tileCount++;
                boardButtons[i][j].setGraphic(pieceIcon);
            }
        }
    }

    private void handleTileClicked(int row, int col, MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            fromTile = game.getBoard().getTile(getTileId(row, col));
            System.out.println("\nSource Tile ID: =========> " + fromTile.getTileCoordinate());
            movedPiece = fromTile.getPiece();

            if (movedPiece == null) {
                System.out.println("No piece is selected");
                fromTile = null;
            }
            else {
                System.out.println(movedPiece.getPieceColor().toString() + " " + movedPiece.toString() + " is Selected");
            }
        }

        else if (event.getButton() == MouseButton.PRIMARY) {
            if (movedPiece != null) {
                destinationTile = game.getBoard().getTile(getTileId(row, col));
                System.out.println("\nDestinationTile ID: =========> " + destinationTile.getTileCoordinate());

                boolean moveSuccess = game.makeMove(fromTile, destinationTile);
                System.out.println(fromTile.getTileCoordinate() + " ============> " + destinationTile.getTileCoordinate());
                System.out.println("Move Success ============> " + moveSuccess);
                if (moveSuccess) {
                    HBox hbox = new HBox();
                    Label label = new Label(game.getBoard().currentPlayer().getPlayerColor().toString() + " To move");
                    label.setFont(Font.font(24));
                    label.setBackground(Background.fill(Color.GRAY));
                    label.setBorder(Border.stroke(Color.BLACK));
                    hbox.getChildren().add(label);

                    Label inCheckLabel = new Label(game.getBoard().currentPlayer().isInCheck() + " ");
                    hbox.getChildren().add(inCheckLabel);
                    hbox.setAlignment(Pos.CENTER);
                    borderPane.setBottom(hbox);
                    
                    System.out.println(movedPiece.getPieceColor().toString() + " " + movedPiece.toString() + " has moved");
                    setBoardPieces(game.getBoard());
                    if (game.isGameOver()) {
                        /*TODO handle game over event*/
                        //displayGameOver();
                    }
                    fromTile = null;
                }
            }
        }
    }


    //convert rows and columns into a single number that represents TileId
    private int getTileId(int row, int col) {
        int tileId = -1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ++tileId;
                if (i == row && j == col) {
                    return tileId;
                }
            }
        }
        return tileId;
    }

    public static void main(String[] args) {
        launch(args);
    }
}