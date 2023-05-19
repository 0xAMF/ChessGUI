package GUI;


import com.example.chessgui.PieceColor;
import com.example.chessgui.Player.MoveTransition;
import com.example.chessgui.board.Board;
import com.example.chessgui.board.Move;
import com.example.chessgui.board.Tile;
import com.example.chessgui.pieces.Piece;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class test extends Application {

    private final int BOARD_SIZE = 8;
    private Button[][] boardButtons;
    private Button button;
    private Tile fromTile = null;
    private Tile destinationTile = null;
    private Piece movedPiece = null;
    int[] TileId = new int[65];
    int tileCount = 0;

    Board board = Board.createStandardBoard();
    BoardPane boardPane = new BoardPane(board);
    GridPane chessboard = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        chessboard.setAlignment(Pos.CENTER);
        chessboard.setHgap(1);
        chessboard.setVgap(1);
        boardButtons = new Button[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button button = new Button();
                button.setPrefSize(60, 60);
                button.setStyle("-fx-background-color: " + ((i + j) % 2 == 0 ? "white" : "gray"));
                boardButtons[i][j] = button;
                chessboard.add(button, j, i);
            }
        }

        boardPane.setBoardPieces(board, chessboard);

        // Setting up Tile coordinates
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                TileId[tileCount] = tileCount;
                tileCount++;
            }
        }

        // Add event listeners to the board buttons
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                button = boardButtons[i][j];
                int finalI = i;
                int finalJ = j;

                button.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY){
                        fromTile = null;
                        destinationTile = null;
                        movedPiece = null;

                        if (fromTile == null) {
                            fromTile = board.getTile(TileId[getTileId(finalI, finalJ)]);
                            System.out.println("\nfromTile ID: =========> " + fromTile.getTileCoordinate());
                            movedPiece = fromTile.getPiece();
                            if (movedPiece == null) {
                                fromTile = null;
                            }
                            else if (movedPiece != null) {
                                System.out.println(movedPiece.toString() + " is selected");
                            }
                        }
                    }

                    else if (event.getButton() == MouseButton.PRIMARY) {

                        if (movedPiece != null){
                            destinationTile = board.getTile(TileId[getTileId(finalI, finalJ)]);

                            System.out.println("\nDestinationTile ID: =========> " + destinationTile.getTileCoordinate());

                            Move move = Move.MoveFactory.createMove(board, fromTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            MoveTransition transition = board.currentPlayer().makeMove(move);

                            if (transition.getMoveStatus().isDone()) {
                                System.out.println(movedPiece.toString() + " is moved.");
                                boardPane.getChildren().remove(movedPiece.getPieceIcon());
                                board = transition.getBoardAfterTransition();
                            }
                            fromTile = null;
                            destinationTile = null;
                            movedPiece = null;
                            updateBoard();
                        }
                    }
                });
            }
        }

        Scene scene = new Scene(chessboard, 600, 600);
        primaryStage.setTitle("Very Bad Chess");
        primaryStage.getIcons().add(new Image("C:\\Users\\Ahmed\\Desktop\\Tessst\\ChessGUI\\src\\main\\java\\PieceIcon\\icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateBoard() {

        boardPane.setBoardPieces(board,  chessboard);


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