package app;

import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while(true) { //true apenas para teste
            try {
                UserInterface.clearScreen();
                UserInterface.printChessBoard(chessMatch.getPieces());
                System.out.println();
                UserInterface.printCapturedPieces(capturedPieces);
                System.out.println();
                System.out.println("Turn: " + chessMatch.getTurn());
                System.out.println("Waiting for player " + chessMatch.getColorCurrentPlayer());
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UserInterface.readChessPosition(scan);

                boolean sourceMoves[][] = chessMatch.sourcePossibleMoves(source);
                UserInterface.clearScreen();
                UserInterface.printChessBoard(chessMatch.getPieces(), sourceMoves);
                
                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UserInterface.readChessPosition(scan);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if(capturedPiece != null)
                    capturedPieces.add(capturedPiece);
            }
            catch(ChessException e) { //TENTAR ARRUMAR ESSA REPETIÇÃO.
                System.out.println(e.getMessage());
                System.out.print("PRESS ENTER ");
                scan.nextLine();
                scan.nextLine();
            }
            catch(InputMismatchException e) {
                System.out.println(e.getMessage());
                System.out.print("PRESS ENTER ");
                scan.nextLine();
                scan.nextLine();
            }
        }
        //scan.close();
    }
}