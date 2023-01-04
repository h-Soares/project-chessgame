package chess;

import java.util.ArrayList;
import java.util.List;
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
    private int turn;
    private Color colorCurrentPlayer;

    private List<Piece> piecesOnBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        colorCurrentPlayer = Color.WHITE;
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
        Position source = sourcePosition.toPosition();                    //usei para printar colorido em UserInterface
        if(!board.thereIsAPiece(source)) //se a peça na posição source for null
            throw new ChessException("There is no piece on source position");
        if(colorCurrentPlayer != ((ChessPiece) board.getPiece(source)).getColor())
            throw new ChessException("The chosen piece is not yours");
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
        if(colorCurrentPlayer != ((ChessPiece) board.getPiece(source)).getColor())
            throw new ChessException("The chosen piece is not yours");
        if(!board.getPiece(source).isThereAnyPossibleMove())
            throw new ChessException("There is no possible moves for the chosen source piece");
        //Validando target
        if(!board.getPiece(source).possibleMove(target))
            throw new ChessException("The source chosen piece can't move to target position");

        //se chegou aqui é porque a peça na posição source não é null, todos os if foram satisfeitos
        Piece capturedPiece = makeMove(source, target); //pode ser peça null
        nextTurn();
        return (ChessPiece) capturedPiece; 
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target); //pode ser peça null
        if(capturedPiece != null) {
            piecesOnBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        board.placePiece(piece, target);
        return capturedPiece;
    }

    private void nextTurn() {
        turn++;
        if(colorCurrentPlayer == Color.WHITE)
            colorCurrentPlayer = Color.BLACK;
        else
            colorCurrentPlayer = Color.WHITE;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnBoard.add(piece);
    }

    private void initialSetup() {
        //peças a serem colocadas no tabuleiro.
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }

    public int getTurn() {
        return turn;
    }

    public Color getColorCurrentPlayer() {
        return colorCurrentPlayer;
    }
}