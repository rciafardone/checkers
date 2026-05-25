package com.example.checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersApp extends Application
{
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 75;

    private final BoardSquare[][] board = new BoardSquare[BOARD_SIZE][BOARD_SIZE];
    private final GamePiece[][] pieceMap = new GamePiece[BOARD_SIZE][BOARD_SIZE];

    // Groups let us layer the visual elements neatly (board on bottom, tokens on top)
    private final Group squareGroup = new Group();
    private final Group pieceGroup = new Group();

    @Override
    public void start(Stage primaryStage)
    {
        Pane root = new Pane();
        root.getChildren().addAll(squareGroup, pieceGroup);

        // Nested loops to assemble our board matrix
        for (int row = 0; row < BOARD_SIZE; row++)
        {
            for (int col = 0; col < BOARD_SIZE; col++)
            {
                BoardSquare square = new BoardSquare(row, col, SQUARE_SIZE);
                board[row][col] = square;
                squareGroup.getChildren().add(square);

                // Check if we should spawn a game piece on this tile
                GamePiece piece = spawnInitialPiece(row, col);
                if (piece != null)
                {
                    pieceMap[row][col] = piece;
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        Scene scene = new Scene(root, BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private GamePiece spawnInitialPiece(int row, int col)
    {
        // Pieces only live on dark tiles
        boolean isDarkSquare = (row + col) % 2 != 0;
        if (!isDarkSquare)
        {
            return null;
        }

        // Top 3 rows get Black pieces
        if (row < 3)
        {
            return new GamePiece(this, PieceType.BLACK, row, col, SQUARE_SIZE);
        }

        // Bottom 3 rows get Red pieces
        if (row > 4)
        {
            return new GamePiece(this, PieceType.RED, row, col, SQUARE_SIZE);
        }

        return null;
    }

    public GamePiece getPieceAt(int row, int col)
    {
        return pieceMap[row][col];
    }

    public void movePieceInMatrix(int oldRow, int oldCol, int newRow, int newCol)
    {
    // 1. Grab the piece currently sitting at the old position
    GamePiece piece = pieceMap[oldRow][oldCol];

    // 2. Clear out the old position in the matrix so it becomes null (empty)
    pieceMap[oldRow][oldCol] = null;

    // 3. Place that piece into the new target position in the matrix
    pieceMap[newRow][newCol] = piece;
    }

    public void removePieceFromBoard(int row, int col)
    {
        GamePiece pieceToRemove = pieceMap[row][col];

        if (pieceToRemove != null)
        {
            // 1. Clear it out of our backend tracking array
            pieceMap[row][col] = null;

            // 2. Remove its visual circle from the JavaFX rendering group
            pieceGroup.getChildren().remove(pieceToRemove);
        }
    }


    /******************************************THIS IS THE END********************************************************/
    /******************************************THIS IS THE END********************************************************/
    /******************************************THIS IS THE END********************************************************/
}