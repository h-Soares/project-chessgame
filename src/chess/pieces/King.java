package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa o rei (King) */

public class King extends ChessPiece{
    private ChessMatch chessMatch; //para fazer o roque é preciso ter acesso à partida

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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
        
        //Roque (Castling) :
        if(getMoveCount() == 0 && !chessMatch.getCheck()) {
            //Roque pequeno
            Position posRook1 = new Position(position.getRow(), position.getColumn() + 3); //3 colunas de distância do rei
            if(canRookCastling(posRook1)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() + 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() + 2);
                if(!getBoard().thereIsAPiece(pos1) && !getBoard().thereIsAPiece(pos2)) {
                    booleanMatrix[pos2.getRow()][pos2.getColumn()] = true; //"pula casa"
                }
            }
            //Roque grande
            Position posRook2 = new Position(position.getRow(), position.getColumn() - 4);
            if(canRookCastling(posRook2)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() - 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() - 2);  
                Position pos3 = new Position(position.getRow(), position.getColumn() - 3); 
                if(!getBoard().thereIsAPiece(pos1) && !getBoard().thereIsAPiece(pos2) && !getBoard().thereIsAPiece(pos3))
                    booleanMatrix[pos2.getRow()][pos2.getColumn()] = true;  
            }
        }
        return booleanMatrix;
    }

    private boolean canRookCastling(Position position) {
        ChessPiece rook = (ChessPiece) getBoard().getPiece(position);
        return rook != null && rook instanceof Rook && rook.getColor() == getColor() && rook.getMoveCount() == 0;
    }
    
    @Override 
    public String toString() {
        return "K"; 
    }
}