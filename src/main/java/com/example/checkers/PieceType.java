package com.example.checkers;

import javafx.scene.paint.Color;

public enum PieceType
{
    RED(Color.web("#c62828")),    // Sleek Crimson Red
    BLACK(Color.web("#212121"));  // Sleek Charcoal Black

    private final Color color;

    PieceType(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
    }
}