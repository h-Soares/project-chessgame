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

        while(!chessMatch.getCheckMate()) {
            try {
                UserInterface.clearScreen();
                UserInterface.printChessMatch(chessMatch, capturedPieces);
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
                if(chessMatch.getPromoted() != null) {
                    System.out.print("Piece for promotion (B/N/R/Q): ");
                    String typePiece = scan.next().toUpperCase();
                    while(!typePiece.equals("B") && !typePiece.equals("N") && !typePiece.equals("R") && !typePiece.equals("Q")) {
                        System.out.println("Invalid input!");
                        System.out.print("Piece for promotion (B/N/R/Q): ");  
                        typePiece = scan.next().toUpperCase();  
                    }
                    chessMatch.replacePromotedPiece(typePiece);
                }
            }
            catch(ChessException | InputMismatchException e) { //mesmo corpo de código.
                System.out.println(e.getMessage());
                System.out.print("PRESS ENTER ");
                scan.nextLine();
                scan.nextLine();
            }
        }
        UserInterface.clearScreen();
        UserInterface.printChessMatch(chessMatch, capturedPieces);
        scan.close();
    }
}