package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa a torre (Rook) */

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }
    
    @Override 
    public String toString() {
        return "R"; //Para ser impresso no tabuleiro
    }
}