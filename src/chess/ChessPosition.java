package chess;

import boardgame.Position;
import chess.exceptions.ChessException;

/** Classe para representar uma posição na matriz para posição de xadrez e vice-versa. */

public class ChessPosition {
    private char column; //No xadrez, é primeiro coluna, depois linha. De A a H.
    private int row; //No xadrez, de 8 a 1.
    
    public ChessPosition(char column, int row) {
        if(column < 'A' || column > 'H'|| row < 1 || row > 8)
            throw new ChessException("Error in ChessPosition. Valid values are from A1 to H8");    

        this.column = column;
        this.row = row;
    }

    protected Position toPosition() { //Transforma em posição NA MATRIZ (0,0), (1,0)... etc
        return new Position((8 - row), (column - 'A')); //ver nas linhas e colunas do tabuleiro
    }

    protected static ChessPosition fromPosition(Position position) { //Transforma em posição NO XADREZ (A8 , B1...)
        return new ChessPosition(((char) ('A' + position.getColumn())), (8 - position.getRow()));
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