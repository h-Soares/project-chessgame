package boardgame;

/** Classe para uma posição no tabuleiro, que contém linha e coluna. */

public class Position {
    private int row;
    private int column;
   
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("row: " + row);
        sb.append(" column: " + column);
        return sb.toString();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}