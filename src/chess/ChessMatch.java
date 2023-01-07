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
    private ChessPiece enPassantVulnerable; //começa com padrão: null.
    private ChessPiece promoted;
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
        
        if(board.getPiece(source) instanceof King) {//feito por mim sobre o Roque
            if(!canCastling(source, target))
                throw new ChessException("You can't castling if your king pass in a square that is dominated by an opponent piece");
        }

        //se chegou aqui é porque a peça na posição source não é null, todos os if foram satisfeitos
        Piece capturedPiece = makeMove(source, target); //pode ser peça null
        
        ChessPiece movedPiece = (ChessPiece) board.getPiece(target); //é a peça da posição source, mas que foi pega pela posição target

        if(testCheck(colorCurrentPlayer)) {
            undoMove(source, target, capturedPiece);  
            throw new ChessException("You can't put yourself in check");  
        }

        //Promoção de peão
        promoted = null; //é propriedade do chessMatch, logo não é da peça e muda.
        if(movedPiece instanceof Pawn) {
            if(movedPiece.getColor() == Color.WHITE && target.getRow() == 0 || movedPiece.getColor() == Color.BLACK && target.getRow() == 7) //peão chegou no final
                promoted = (ChessPiece) board.getPiece(target);   
        }
        
        if(testCheck(opponent(colorCurrentPlayer))) 
            check = true;
        else
            check = false;
        
        if(testCheckMate(opponent(colorCurrentPlayer)))
            checkMate = true;
        else
            nextTurn();

        //En passant    || passa o turno e depois verifica || é propriedade do chessMatch, logo não é da peça e muda.
        if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2))
            enPassantVulnerable = movedPiece;
        else
            enPassantVulnerable = null;

        return (ChessPiece) capturedPiece; //cada peça sabe as suas características: cor, movimentação, etc.
    }

    public void replacePromotedPiece(String typePiece) {
        if(promoted == null)
            throw new IllegalStateException("There is no piece to be promoted");
        //aqui seria o tratamento de erro de typePiece, mas no main, onde ele é usado, não é permitido o erro.

        Position promotedPosition = promoted.getChessPosition().toPosition();
        Piece removedPiece = board.removePiece(promotedPosition);
        piecesOnBoard.remove(removedPiece);

        ChessPiece newPiece = toPromotePiece(typePiece, promoted.getColor());
        board.placePiece(newPiece, promotedPosition); 
        piecesOnBoard.add(newPiece);

        //Como passou o turno: ----------------------------------------------------------------
        check = testCheck(colorCurrentPlayer) ? true : false;

        if(testCheckMate(colorCurrentPlayer)) {
            checkMate = true;
            colorCurrentPlayer = opponent(colorCurrentPlayer); //para dar print na cor correta.
        }
    }

    private ChessPiece toPromotePiece(String typePiece, Color color) {
        if(typePiece.equals("B"))
            return new Bishop(board, color);
        if(typePiece.equals("N"))
            return new Knight(board, color);
        if(typePiece.equals("R"))
            return new Rook(board, color);
        return new Queen(board, color);
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target); //pode ser peça null
        if(capturedPiece != null) {
            piecesOnBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        board.placePiece(piece, target); //esse target é validado como posição possível de movimento
        ((ChessPiece)piece).increaseMoveCount(); //diferente da aula

        //Roque pequeno
        if(piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookSourcePosition);
            board.placePiece(rook, rookTargetPosition);
            rook.increaseMoveCount();
        }
        //Roque grande
        if(piece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookSourcePosition);
            board.placePiece(rook, rookTargetPosition);
            rook.increaseMoveCount();
        }

        //en passant
        if(piece instanceof Pawn && source.getColumn() != target.getColumn() && capturedPiece == null) {
            Position pawnPosition;
            if(((ChessPiece)piece).getColor() == Color.WHITE)
                pawnPosition = new Position(target.getRow() + 1, target.getColumn());
            else
                pawnPosition = new Position(target.getRow() - 1, target.getColumn());   
                  
            capturedPiece = board.removePiece(pawnPosition);
            piecesOnBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
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
        //Roque pequeno
        if(piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookTargetPosition);
            board.placePiece(rook, rookSourcePosition);
            rook.decreaseMoveCount();;
        }
        //Roque grande
        if(piece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookTargetPosition);
            board.placePiece(rook, rookSourcePosition);
            rook.decreaseMoveCount();;
        }
        //en passant
        if(piece instanceof Pawn && source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
            ChessPiece pawn = (ChessPiece) board.removePiece(target);  
            Position pawnPosition;
            if(((ChessPiece)piece).getColor() == Color.WHITE)
                pawnPosition = new Position(3, target.getColumn());
            else
                pawnPosition = new Position(4, target.getColumn());   

            board.placePiece(pawn, pawnPosition); 
        }
    }

    //feito por mim sobre o Roque
    private boolean canCastling(Position source, Position target) {
        List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(colorCurrentPlayer)).collect(Collectors.toList());

        Position pos1 = new Position(source.getRow(), source.getColumn() + 1);
        Position pos2 = new Position(source.getRow(), source.getColumn() - 1);

        for(Piece piece : opponentPieces) {
            //Roque pequeno
            if(target.getColumn() == source.getColumn() + 2) {
                if(piece.possibleMove(pos1))
                    return false;
            }
            //Roque grande
            else if(target.getColumn() == source.getColumn() - 2) {
                if(piece.possibleMove(pos2)) {
                    return false;
                }
            }
        }
        return true;
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
        placeNewPiece('e', 1, new King(board, Color.WHITE, this)); //esse chess match
        for(char column = 'a'; column <= 'h'; column++)
            placeNewPiece(column, 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        for(char column = 'a'; column <= 'h'; column++)
            placeNewPiece(column, 7, new Pawn(board, Color.BLACK, this));        
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

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public int getTurn() {
        return turn;
    }

    public Color getColorCurrentPlayer() {
        return colorCurrentPlayer;
    }
}