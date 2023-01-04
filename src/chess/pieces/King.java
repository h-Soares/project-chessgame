package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa o rei (King) */

public class King extends ChessPiece{

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() { //implementação TESTE
        boolean booleanMatrix[][] = new boolean[getBoard().getRows()][getBoard().getColumns()]; //por padrão, tudo FALSE.
        Position pos = new Position(0, 0);

        //Implementação DIFERENTE da aula sobre os movimentos do rei. Até este ponto, o código está correto.
        
        //para cima     
        pos.setValues(position.getRow() - 1, position.getColumn());
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        //para esquerda
        pos.setValues(position.getRow(), position.getColumn() - 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        //para direita
        pos.setValues(position.getRow(), position.getColumn() + 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        //para baixo
        pos.setValues(position.getRow() + 1, position.getColumn());
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;    
            
        //noroeste
        pos.setValues(position.getRow() - 1, position.getColumn() - 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        //nordeste
        pos.setValues(position.getRow() - 1, position.getColumn() + 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        //sudeste
        pos.setValues(position.getRow() + 1, position.getColumn() + 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;
        
        //sudoeste
        pos.setValues(position.getRow() + 1, position.getColumn() - 1);
        if(getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereOpponentPiece(pos)))
            booleanMatrix[pos.getRow()][pos.getColumn()] = true;

        return booleanMatrix;
    }
    
    @Override 
    public String toString() {
        return "K"; 
    }
}