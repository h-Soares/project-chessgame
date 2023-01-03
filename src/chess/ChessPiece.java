package chess;

import boardgame.Board;
import boardgame.Piece;
import chess.enums.Color;

/** Classe de uma peça de xadrez, que herda de Piece e tem sua própria cor. */

public abstract class ChessPiece extends Piece {
    
    private final Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}