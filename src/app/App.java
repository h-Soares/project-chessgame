package app;

import chess.ChessMatch;

public class App {
    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();
        UserInterface.printChessBoard(chessMatch.getPieces());
    }
}