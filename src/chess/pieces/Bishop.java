package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa o bispo (Bishop) */

public class Bishop extends ChessPiece{

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean booleanMatrix[][] = new boolean[getBoard().getRows()][getBoard().getColumns()]; //por padrão, tudo FALSE.
        Position pos = new Position(0, 0);

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
        return "B";
    }
}