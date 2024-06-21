package chess.model;

import chess.model.utils.TwoPhaseMoveState;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class Chess implements TwoPhaseMoveState<Posititon> {

    public static final int BOARD_SIZE = 8;
    private Player player;
    private final ReadOnlyObjectWrapper<ChessPieces>[][] board;

    public Chess() {
        board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(
                        switch (i) {
                            case 2, 3, 4, 5 -> ChessPieces.EMPTY;
                            case 0 -> blackBackLine(j);
                            case 1 -> ChessPieces.BLACK_PAWN;
                            case 6 -> ChessPieces.WHITE_PAWN;
                            default ->  whiteBackLine(j);
                        }
                );
            }
        }
        player = Player.PLAYER_1;
    }

    private ChessPieces blackBackLine(int col) {
        ChessPieces chessPiece;
        switch (col) {
            case 0,7 -> chessPiece = ChessPieces.BLACK_ROOK;
            case 1,6 -> chessPiece = ChessPieces.BLACK_KNIGHT;
            case 2,5 -> chessPiece = ChessPieces.BLACK_BISHOP;
            case 3 -> chessPiece = ChessPieces.BLACK_QUEEN;
            case 4 -> chessPiece = ChessPieces.BLACK_KING;
            default -> chessPiece = ChessPieces.EMPTY;
        }
        return chessPiece;
    }

    private ChessPieces whiteBackLine(int col) {
        ChessPieces chessPiece;
        switch (col) {
            case 0,7 -> chessPiece = ChessPieces.WHITE_ROOK;
            case 1,6 -> chessPiece = ChessPieces.WHITE_KNIGHT;
            case 2,5 -> chessPiece = ChessPieces.WHITE_BISHOP;
            case 3 -> chessPiece = ChessPieces.WHITE_QUEEN;
            case 4 -> chessPiece = ChessPieces.WHITE_KING;
            default -> chessPiece = ChessPieces.EMPTY;
        }
        return chessPiece;
    }

    public ReadOnlyObjectProperty<ChessPieces> getProperty(int row, int col) {
        return board[row][col].getReadOnlyProperty();
    }

    public ChessPieces get(int row, int col) {
        return board[row][col].get();
    }


    public boolean isCheckMate() {
        return false;
    }

    /**
     * {@return the player who moves next}
     */
    @Override
    public Player getNextPlayer() {
        return null;
    }

    /**
     * {@return whether the game is over}
     */
    @Override
    public boolean isGameOver() {
        return false;
    }

    /**
     * {@return the status of the game}
     */
    @Override
    public Status getStatus() {
        return null;
    }

    /**
     * {@return whether it is possible to make a move from the argument
     * specified}
     *
     * @param from represents where to move from
     */
    @Override
    public boolean isLegalToMoveFrom(Posititon from) {
        return false;
    }

    /**
     * {@return whether the move provided can be applied to the state}
     *
     * @param from represents where to move from
     * @param to   represents where to move to
     */
    @Override
    public boolean isLegalMove(Posititon from, Posititon to) {
        return false;
    }

    /**
     * Applies the move provided to the state. This method should be called if
     * and only if {@link #isLegalMove(Posititon, Posititon)} returns {@code true}.
     *
     * @param from represents where to move from
     * @param to   represents where to move to
     */
    @Override
    public void makeMove(Posititon from, Posititon to) {

    }
}
