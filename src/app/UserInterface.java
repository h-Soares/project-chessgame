package app;

import chess.ChessPiece;

/** Classe para imprimir o tabuleiro para o usuário. */

public class UserInterface {
    public static void printChessBoard(ChessPiece pieces[][]) {
        for(int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for(int j = 0; j < pieces.length; j++) {
                printOnePiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println("  A B C D E F G H");
    }

    private static void printOnePiece(ChessPiece piece) {
        if(piece == null)
            System.out.print("-");
        else
            System.out.print(piece);

        System.out.print(" ");
    }
}