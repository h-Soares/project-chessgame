package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

/** Classe que representa o peão (Pawn) */

public class Pawn extends ChessPiece{
    private ChessMatch chessMatch; //para fazer o en passant

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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

            //en passant brancas: ocorre na linha 5 (na matriz, 3) se
            //tiver um peão inimigo que deu 2 passos e parou do lado esquerdo/direito de um peão aliado
            if(position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable())
                    booleanMatrix[left.getRow() - 1][left.getColumn()] = true;

                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable())
                    booleanMatrix[right.getRow() - 1][right.getColumn()] = true;
            }
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
                
            //en passant pretas
            if(position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable())
                    booleanMatrix[left.getRow() + 1][left.getColumn()] = true;

                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable())
                    booleanMatrix[right.getRow() + 1][right.getColumn()] = true;
            }
        }
        return booleanMatrix;
    }

    @Override 
    public String toString() {
        return "P";
    }
}