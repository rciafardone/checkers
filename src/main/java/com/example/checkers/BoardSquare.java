package com.example.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardSquare extends Rectangle
{
    public static final Color DARK_SQUARE_COLOR = Color.web("#5c4033"); // Dark Walnut
    public static final Color LIGHT_SQUARE_COLOR = Color.web("#f5deb3"); // Light Wheat
    private final int row;
    private final int col;
    private final boolean isDark;

    public BoardSquare(int row, int col, int size)
    {
        this.row = row;
        this.col = col;

        // A square is dark if the sum of its row and column coordinates is odd
        this.isDark = (row + col) % 2 != 0;

        // Set the pixel dimensions of the rectangle
        setWidth(size);
        setHeight(size);

        // Position the square precisely on our pixel grid
        setX(col * size);
        setY(row * size);

        // Color alternating squares (Light Wood vs Dark Walnut style)
        if (isDark)
        {
            setFill(DARK_SQUARE_COLOR);
        }
        else
        {
            setFill(LIGHT_SQUARE_COLOR);
        }
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public boolean isDarkSquare()
    {
        return isDark;
    }
}