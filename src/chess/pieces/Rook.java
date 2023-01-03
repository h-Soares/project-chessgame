package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa a torre (Rook) */

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean booleanMatrix[][] = new boolean[getBoard().getRows()][getBoard().getColumns()]; //por padrão, tudo FALSE.
        Position pos = new Position(0, 0); //apenas para ter um valor inicial.
        
        //Com base na posição atual da peça (position), verifica todos os movimentos possíveis.
        //A posição atual da peça (position) muda ao fazer performChessMove -> makeMove -> board.placePiece

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

        return booleanMatrix;
    }
    
    @Override 
    public String toString() {
        return "R"; //Para ser impresso no tabuleiro
    }
}