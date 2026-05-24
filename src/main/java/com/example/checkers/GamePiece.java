package com.example.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GamePiece extends StackPane
{
    private final PieceType type;
    private boolean isKing = false;

    private int row;
    private int col;

    public GamePiece(PieceType type, int row, int col, int squareSize)
    {
        this.type = type;
        this.row = row;
        this.col = col;

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
}