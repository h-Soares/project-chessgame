package chess;

import boardgame.Board;
import boardgame.Position;
import chess.enums.Color;
import chess.pieces.King;
import chess.pieces.Rook;

/** Classe que contém as regras do jogo de xadrez. */

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece piecesMatrix[][] = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i = 0; i < board.getRows(); i++) {
            for(int j = 0; j < board.getColumns(); j++) {
                piecesMatrix[i][j] = (ChessPiece) board.getPiece(i, j); //Downcast de Piece para ChessPiece, para interpretar como 
            }                                                  //peça de xadrez, e não peça comum.
        }
        return piecesMatrix; //retorna a matriz de peças da partida.
    }

    private void initialSetup() {
        //peças a serem colocadas no tabuleiro.
        board.placePiece(new Rook(board, Color.BLACK), new Position(0, 0));
        board.placePiece(new King(board, Color.BLACK), new Position(0, 1));
    }
}