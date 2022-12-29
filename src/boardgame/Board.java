package boardgame;

/** Classe tabuleiro, que contém linhas e colunas e uma matriz de peças.  */

public class Board {
    private int rows;
    private int columns;
    private Piece pieces[][];

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public Piece piece(int row, int column) {
        return pieces[row][column]; //retorna uma peça dada sua linha e coluna no tabuleiro.
    }

    public Piece piece(Position position) {
        return pieces[position.getRow()][position.getColumn()]; //retorna uma peça dada sua posição no tabuleiro.
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }   
}