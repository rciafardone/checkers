package com.example.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GamePiece extends StackPane
{
    private final CheckersApp app;

    private double mouseX;
    private double mouseY;

    private final PieceType type;
    private boolean isKing = false;

    private int row;
    private int col;

    public GamePiece(CheckersApp app, PieceType type, int row, int col, int squareSize)
    {
        this.app = app;
        this.type = type;
        this.row = row;
        this.col = col;

        initializeVisuals(squareSize);
        initializeInputHandlers(squareSize);

    }

    private void initializeVisuals(int squareSize)
    {
        // Draw the main game piece circle
        Circle pieceCircle = new Circle();
        pieceCircle.setRadius(squareSize * 0.38); // Proportional sizing (approx 28px)
        pieceCircle.setFill(type.getColor());

        // Add a subtle border/stroke around the circle so it pops out from the dark squares
        pieceCircle.setStroke(Color.web("#ffffff", 0.3));
        pieceCircle.setStrokeWidth(2);

        // Position the piece in the exact center pixel coordinate of its allocated square
        relocate(col * squareSize + (squareSize * 0.12), row * squareSize + (squareSize * 0.12));

        // Add our visual circle to the StackPane layer stack
        getChildren().add(pieceCircle);
    }

    private void initializeInputHandlers(int squareSize)
    {
        this.setOnMousePressed(e ->
        {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        this.setOnMouseDragged(e ->
        {
            double deltaX = e.getSceneX() - mouseX;
            double deltaY = e.getSceneY() - mouseY;

            relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);

            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        this.setOnMouseReleased(e ->
        {
            int newCol = (int) Math.round(getLayoutX() / squareSize);
            int newRow = (int) Math.round(getLayoutY() / squareSize);

            if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8)
            {
                abortMove(squareSize);
                return;
            }

            if (validateMove(newRow, newCol))
            {
                executeMove(newRow, newCol, squareSize);
            }
            else
            {
                abortMove(squareSize);
            }
        });
    }


    private void executeMove(int newRow, int newCol, int squareSize)
    {
        // Check if this move is a jump (distance of 2)
        if (Math.abs(newRow - this.row) == 2)
        {
            int middleRow = (this.row + newRow) / 2;
            int middleCol = (this.col + newCol) / 2;

            // Tell the app backend to remove the captured piece
            app.removePieceFromBoard(middleRow, middleCol);
        }

        // Update the master matrix backend before updating local coordinates
        app.movePieceInMatrix(this.row, this.col, newRow, newCol);

        // Update the piece's local coordinates and visuals
        updatePosition(newRow, newCol, squareSize);
    }

    public PieceType getType()
    {
        return type;
    }

    public boolean isKing()
    {
        return isKing;
    }

    public void makeKing()
    {
        this.isKing = true;
        // Future layout updates for visual crowning will go here!
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public void updatePosition(int newRow, int newCol, int squareSize)
    {
        this.row = newRow;
        this.col = newCol;
        relocate(newCol * squareSize + (squareSize * 0.12), newRow * squareSize + (squareSize * 0.12));
    }

    public void abortMove(int squareSize)
    {
        relocate(col * squareSize + (squareSize * 0.12), row * squareSize + (squareSize * 0.12));
    }

    private boolean validateMove(int newRow, int newCol)
    {
        // Rule 1: Must be within the bounds of the board
        if (!isWithinBounds(newRow, newCol))
        {
            return false;
        }

        // Rule 2: Must be a dark square
        if (!isDarkSquare(newRow, newCol))
        {
            return false;
        }

        // Rule 3: The target square must not be occupied
        if (!isSquareEmpty(newRow, newCol))
        {
            return false;
        }

        // Rule 4: Must follow a legal move or jump pattern
        if (!isValidMovePattern(newRow, newCol))
        {
            return false;
        }


        // All active rules passed
        return true;
    }

    private boolean isValidMovePattern(int newRow, int newCol)
    {
        int rowDelta = Math.abs(newRow - this.row);
        int colDelta = Math.abs(newCol - this.col);

        // Standard move: check if it's exactly 1 diagonal step
        if (rowDelta == 1 && colDelta == 1)
        {
            return true;
        }

        // Jump move: check if it's exactly 2 diagonal steps over an opponent
        if (rowDelta == 2 && colDelta == 2)
        {
            return canJumpOpponent(newRow, newCol);
        }

        // Any other distance/shape is illegal
        return false;
    }

    private boolean canJumpOpponent(int newRow, int newCol)
    {
        // Find the coordinates of the square we are jumping over
        int middleRow = (this.row + newRow) / 2;
        int middleCol = (this.col + newCol) / 2;

        GamePiece jumpedPiece = app.getPieceAt(middleRow, middleCol);

        // There must be a piece in the middle, and its color/type must be different from ours
        return (jumpedPiece != null && jumpedPiece.getType() != this.type);
    }




    private boolean isSquareEmpty(int newRow, int newCol)
    {
        // Check if CheckersApp has a piece registered at these coordinates
        return (app.getPieceAt(newRow, newCol) == null);
    }


    private boolean isWithinBounds(int newRow, int newCol)
    {
        return newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8;
    }

    private boolean isDarkSquare(int newRow, int newCol)
    {
        return (newRow + newCol) % 2 != 0;
    }






}