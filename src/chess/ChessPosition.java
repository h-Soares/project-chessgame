package chess;

import boardgame.Position;
import chess.exceptions.ChessException;

/** Classe para representar uma posição na matriz para posição de xadrez e vice-versa. */

public class ChessPosition {
    private char column; //No xadrez, é primeiro coluna, depois linha. De a a h.
    private int row; //No xadrez, de 8 a 1.
    
    public ChessPosition(char column, int row) {
        if(column < 'a' || column > 'h'|| row < 1 || row > 8)
            throw new ChessException("Error in ChessPosition. Valid values are from a1 to h8");    

        this.column = column;
        this.row = row;
    }

    protected Position toPosition() { //Transforma em posição NA MATRIZ (0,0), (1,0)... etc
        return new Position((8 - row), (column - 'a')); //ver nas linhas e colunas do tabuleiro
    }

    protected static ChessPosition fromPosition(Position position) { //Transforma em posição NO XADREZ (a8 , b1...)
        return new ChessPosition(((char) ('a' + position.getColumn())), (8 - position.getRow()));
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(column);
        sb.append(row);
        return sb.toString();
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }   
}