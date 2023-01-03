package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
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

    public boolean[][] sourcePossibleMoves(ChessPosition sourcePosition) { //verificar movimentos possíveis de uma determinada peça.
        Position source = sourcePosition.toPosition();
        if(!board.thereIsAPiece(source)) //se a peça na posição source for null
            throw new ChessException("There is no piece on source position");
        if(!board.getPiece(source).isThereAnyPossibleMove())
            throw new ChessException("There is no possible moves for the chosen source piece");
        
        return board.getPiece(source).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition(); //convertendo para posição na MATRIZ.
        Position target = targetPosition.toPosition();
        //Validando source
        if(!board.thereIsAPiece(source)) //se a peça na posição source for null
            throw new ChessException("There is no piece on source position");
        if(!board.getPiece(source).isThereAnyPossibleMove())
            throw new ChessException("There is no possible moves for the chosen source piece");
        //Validando target
        if(!board.getPiece(source).possibleMove(target))
            throw new ChessException("The source chosen piece can't move to target position");

        //se chegou aqui é porque a peça na posição source não é null, todos os if foram satisfeitos
        Piece capturedPiece = makeMove(source, target); //pode ser peça null
        return (ChessPiece) capturedPiece; 
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target); //pode ser peça null
        board.placePiece(piece, target);
        return capturedPiece;
    }

    private void initialSetup() {
        //peças a serem colocadas no tabuleiro.
        //board.placePiece(new Rook(board, Color.BLACK), new Position(0, 0));
        //board.placePiece(new Rook(board, Color.BLACK), new ChessPosition('a', 8).toPosition());
        board.placePiece(new Rook(board, Color.WHITE), new ChessPosition('c', 1).toPosition());
        board.placePiece(new Rook(board, Color.WHITE), new ChessPosition('c', 2).toPosition());
        board.placePiece(new Rook(board, Color.WHITE), new ChessPosition('d', 2).toPosition());
        board.placePiece(new Rook(board, Color.WHITE), new ChessPosition('e', 2).toPosition());
        board.placePiece(new Rook(board, Color.WHITE), new ChessPosition('e', 1).toPosition());
        board.placePiece(new King(board, Color.WHITE), new ChessPosition('d', 1).toPosition());

        board.placePiece(new Rook(board, Color.BLACK), new ChessPosition('c', 7).toPosition());
        board.placePiece(new Rook(board, Color.BLACK), new ChessPosition('c', 8).toPosition());
        board.placePiece(new Rook(board, Color.BLACK), new ChessPosition('d', 7).toPosition());
        board.placePiece(new Rook(board, Color.BLACK), new ChessPosition('e', 7).toPosition());
        board.placePiece(new Rook(board, Color.BLACK), new ChessPosition('e', 8).toPosition());
        board.placePiece(new King(board, Color.BLACK), new ChessPosition('d', 8).toPosition());
    }
}