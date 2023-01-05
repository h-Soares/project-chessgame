package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa o cavalo (Knight) */

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean booleanMatrix[][] = new boolean[getBoard().getRows()][getBoard().getColumns()]; //por padrão, tudo FALSE.
        Position pos = new Position(0, 0);

        //todas as possibilidades em formato de L:   
        pos.setValues(position.getRow() - 2, position.getColumn() - 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        pos.setValues(position.getRow() - 2, position.getColumn() + 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        pos.setValues(position.getRow() - 1, position.getColumn() - 2);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        pos.setValues(position.getRow() - 1, position.getColumn() + 2);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;    
            
        pos.setValues(position.getRow() + 2, position.getColumn() - 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        pos.setValues(position.getRow() + 2, position.getColumn() + 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        pos.setValues(position.getRow() - 1, position.getColumn() - 2);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        pos.setValues(position.getRow() - 1, position.getColumn() + 2);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        return booleanMatrix;
    }   

    @Override 
    public String toString() {
        return "N";
    }
}