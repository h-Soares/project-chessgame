package boardgame.exceptions;

/** Classe para exceções no tabuleiro. */

public class BoardException extends RuntimeException {
    public BoardException(String message) {
        super(message);
    }
}