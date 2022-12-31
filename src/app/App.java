package app;

import java.util.Scanner;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while(true) { //apenas para teste
            UserInterface.printChessBoard(chessMatch.getPieces());
            System.out.println();
            System.out.print("Source: ");
            ChessPosition source = UserInterface.readChessPosition(scan);
            
            System.out.println();
            System.out.print("Target: ");
            ChessPosition target = UserInterface.readChessPosition(scan);

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }
        //scan.close();
    }
}