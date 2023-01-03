package boardgame;

/** Classe para uma peça do tabuleiro, que tem uma posição no tabuleiro e está associada a um tabuleiro. */

public abstract class Piece {
    protected Position position; //inicia como nula, sem posição no tabuleiro.
    private Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public abstract boolean[][] possibleMoves();

    //Implementações concretas que dependem de um método abstrato: 
    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean movesMatrix[][] = possibleMoves();

        for(int i = 0; i < movesMatrix.length; i++) {
            for(int j = 0; j < movesMatrix.length; j++) {
                if(movesMatrix[i][j])   
                    return true; //existe pelo menos um movimento possível
            }
        }
        return false; //não existem movimentos possíveis
    }
 
    protected Board getBoard() { //protected para ficar restrito apenas a essa camada boardgame.
        return board;
    }
}