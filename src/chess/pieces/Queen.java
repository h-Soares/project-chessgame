package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa a rainha (Queen) */

public class Queen extends ChessPiece {

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean booleanMatrix[][] = new boolean[getBoard().getRows()][getBoard().getColumns()]; //por padrão, tudo FALSE.
        Position pos = new Position(0, 0); //apenas para ter um valor inicial.

        //Movimentos da torre e do bispo:
        //para cima
        pos.setValues(position.getRow() - 1, position.getColumn());
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setRow(pos.getRow() - 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //para esquerda
        pos.setValues(position.getRow(), position.getColumn() - 1);
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setColumn(pos.getColumn() - 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //para direita
        pos.setValues(position.getRow(), position.getColumn() + 1);
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setColumn(pos.getColumn() + 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //para baixo
        pos.setValues(position.getRow() + 1, position.getColumn());
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setRow(pos.getRow() + 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //noroeste
        pos.setValues(position.getRow() - 1, position.getColumn() - 1);
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setValues(pos.getRow() - 1, pos.getColumn() - 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //nordeste
        pos.setValues(position.getRow() - 1, position.getColumn() + 1);
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setValues(pos.getRow() - 1, pos.getColumn() + 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //sudeste
        pos.setValues(position.getRow() + 1, position.getColumn() + 1);
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setValues(pos.getRow() + 1, pos.getColumn() + 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //sudoeste
        pos.setValues(position.getRow() + 1, position.getColumn() - 1);
        while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
            pos.setValues(pos.getRow() + 1, pos.getColumn() - 1);
        }
        if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;           

        return booleanMatrix;
    }   

    @Override
    public String toString() {
        return "Q";
    }
}