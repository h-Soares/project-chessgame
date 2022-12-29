package boardgame;

/** Classe para uma peça do tabuleiro, que tem uma posição no tabuleiro e está associada a um tabuleiro. */

public abstract class Piece {
    protected Position position; //inicia como nula, sem posição no tabuleiro.
    private Board board;

    public Piece(Board board) {
        this.board = board;
    }

    protected Board getBoard() { //protected para ficar restrito apenas a essa camada boardgame.
        return board;
    }
}