package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa o peão (Pawn) */

public class Pawn extends ChessPiece{

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean booleanMatrix[][] = new boolean[getBoard().getRows()][getBoard().getColumns()]; //por padrão, tudo FALSE.
        Position pos = new Position(0, 0);

        if(getColor() == Color.WHITE) {
            if(getMoveCount() == 0) {
                pos.setValues(position.getRow() - 2, position.getColumn());
                Position testPosition = new Position(position.getRow() - 1, position.getColumn());
                //para não pular casa ocupada:
                if(getBoard().positionExists(testPosition) && getBoard().positionExists(pos) && !getBoard().thereIsAPiece(testPosition) && !getBoard().thereIsAPiece(pos))
                    booleanMatrix[pos.getRow()][pos.getColumn()] = true;                     
            }

            pos.setValues(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos))
                booleanMatrix[pos.getRow()][pos.getColumn()] = true;

            pos.setValues(position.getRow() - 1, position.getColumn() - 1); //se tiver inimigo na diagonal
            if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
                booleanMatrix[pos.getRow()][pos.getColumn()] = true;

            pos.setValues(position.getRow() - 1, position.getColumn() + 1); //se tiver inimigo na diagonal
            if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
                booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        }
        else if(getColor() == Color.BLACK) {
            if(getMoveCount() == 0) {
                pos.setValues(position.getRow() + 2, position.getColumn());
                Position testPosition = new Position(position.getRow() + 1, position.getColumn());
                //para não pular casa ocupada:
                if(getBoard().positionExists(testPosition) && getBoard().positionExists(pos) && !getBoard().thereIsAPiece(testPosition) && !getBoard().thereIsAPiece(pos))
                    booleanMatrix[pos.getRow()][pos.getColumn()] = true;  
            }

            pos.setValues(position.getRow() + 1, position.getColumn());
            if(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos))
                booleanMatrix[pos.getRow()][pos.getColumn()] = true;

            pos.setValues(position.getRow() + 1, position.getColumn() - 1); //se tiver inimigo na diagonal
            if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
                booleanMatrix[pos.getRow()][pos.getColumn()] = true;

            pos.setValues(position.getRow() + 1, position.getColumn() + 1); //se tiver inimigo na diagonal
            if(getBoard().positionExists(pos) && isThereOpponentPiece(pos))
                booleanMatrix[pos.getRow()][pos.getColumn()] = true;            
        }
        return booleanMatrix;
    }

    @Override 
    public String toString() {
        return "P";
    }
}