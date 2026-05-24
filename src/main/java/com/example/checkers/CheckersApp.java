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
            return new GamePiece(PieceType.BLACK, row, col, SQUARE_SIZE);
        }

        // Bottom 3 rows get Red pieces
        if (row > 4)
        {
            return new GamePiece(PieceType.RED, row, col, SQUARE_SIZE);
        }

        return null;
    }
}