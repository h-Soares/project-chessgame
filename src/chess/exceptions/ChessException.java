package chess.exceptions;

import boardgame.exceptions.BoardException;

/** Classe para exceções nas regras de xadrez. */

public class ChessException extends BoardException { //exceção de xadrez é uma exceção no tabuleiro
    public ChessException(String message) {
        super(message);
    }
}