package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

/** Classe que contém as regras do jogo de xadrez. */

public class ChessMatch {
    private Board board;
    private int turn;
    private Color colorCurrentPlayer;
    private boolean check;
    private boolean checkMate;

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
        
        if(testCheck(colorCurrentPlayer)) {
            undoMove(source, target, capturedPiece);  
            throw new ChessException("You can't put yourself in check");  
        }
        
        if(testCheck(opponent(colorCurrentPlayer))) 
            check = true;
        else
            check = false;
        
        if(testCheckMate(opponent(colorCurrentPlayer)))
            checkMate = true;
        else
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
        ((ChessPiece)piece).increaseMoveCount(); //diferente da aula
        return capturedPiece;
    }

    public void undoMove(Position source, Position target, Piece capturedPiece) { //se o próprio jogador se colocar em cheque
        Piece piece = board.removePiece(target);
        board.placePiece(piece, source);
        ((ChessPiece)piece).decreaseMoveCount(); //diferente da aula

        if(capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnBoard.add(capturedPiece);
        }
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
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        for(char column = 'a'; column <= 'h'; column++)
            placeNewPiece(column, 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        for(char column = 'a'; column <= 'h'; column++)
            placeNewPiece(column, 7, new Pawn(board, Color.BLACK));        
    }

    private Color opponent(Color color) {
        if(color == Color.WHITE)
            return Color.BLACK;
        else
            return Color.WHITE;
    }

    private ChessPiece findKing(Color color) {
        List<Piece> findList = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        
        for(Piece piece : findList) {
            if(piece instanceof King)
                return (ChessPiece) piece;
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = findKing(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());

        for(Piece piece : opponentPieces) { //implementação DIFERENTE da aula
            if(piece.possibleMove(kingPosition))
                return true;
        }
        return false;
    }

    private boolean testCheckMate(Color color) { //mais pra frente, tentar fazer staleMate (afogamento do rei)
        if(!testCheck(color))
            return false;
        
        List<Piece> colorPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece piece : colorPieces) {
            boolean movesMatrix[][] = piece.possibleMoves();
            for(int i = 0; i < board.getRows(); i++) {
                for(int j = 0; j < board.getColumns(); j++) {
                    if(movesMatrix[i][j]) { //se for uma posição movível
                        Position source = ((ChessPiece)piece).getChessPosition().toPosition(); 
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target); //verifica se ao se mover para a posição movível sai do xeque
                        boolean check = testCheck(color); //para guardar o boolean, pois o movimento será desfeito para não bagunçar o tabuleiro
                        undoMove(source, target, capturedPiece);
                        if(!check)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public int getTurn() {
        return turn;
    }

    public Color getColorCurrentPlayer() {
        return colorCurrentPlayer;
    }
}