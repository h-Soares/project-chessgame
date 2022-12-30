package chess.exceptions;

/** Classe para exceções nas regras de xadrez. */

public class ChessException extends RuntimeException {
    public ChessException(String message) {
        super(message);
    }
}