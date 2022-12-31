package boardgame;

import boardgame.exceptions.BoardException;

/** Classe tabuleiro, que contém linhas e colunas e uma matriz de peças.  */

public class Board {
    private final int rows;
    private final int columns; //linhas e colunas do tabuleiro não podem ser mudadas.
    private Piece pieces[][];

    public Board(int rows, int columns) {
        if(rows < 1 || columns < 1)
            throw new BoardException("Invalid board, there must be at least 1 row and 1 column");

        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns]; //refere-se ao TAMANHO da matriz.
    }

    public void placePiece(Piece piece, Position position) {
        if(thereIsAPiece(position)) //verifica, primeiro, se a posição existe
            throw new BoardException("There is already a piece on position " + position);

        pieces[position.getRow()][position.getColumn()] = piece; //coloca no vetor de peças a peça passada como argumento.
        piece.position = position; //a peça não tem mais posição nula.            
    }

    public Piece removePiece(Position position) {
        if(!thereIsAPiece(position))
            return null;

        getPiece(position).position = null; //remove a posição característica da peça
        Piece returnPiece = getPiece(position); //para retornar a peça que está na posição do tabuleiro que ficará null
        pieces[position.getRow()][position.getColumn()] = null; //peça removida da posição no tabuleiro
        return returnPiece; //para saber a peça removida.
    }

    private boolean positionExists(int row, int column) {  //refere-se ao ÍNDICE na matriz. 
        return ((row >= 0 && row < rows) && (column >= 0 && column < columns));
    }

    private boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if(positionExists(position))
            return (getPiece(position) != null);
        else
            throw new BoardException("The position " + position + " doesn't exist");
    }

    public Piece getPiece(int row, int column) {
        if(positionExists(row, column))
            return pieces[row][column]; //retorna uma peça dada sua linha e coluna no tabuleiro.
        else
            throw new BoardException("This position doesn't exist ");
    }

    public Piece getPiece(Position position) {
        if(positionExists(position))
            return pieces[position.getRow()][position.getColumn()]; //retorna uma peça dada sua posição no tabuleiro.
        else
            throw new BoardException("This position doesn't exist");
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}