package chess.model;

import chess.model.Chess;
import chess.model.ChessPieces;
import chess.model.Posititon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessTest {

    Chess initial;
    Chess inGameState;
    Chess inCheckMateState;
    Chess inWinState;

    @BeforeEach
    public void StartUp() {
        initial = new Chess();

        inGameState = new Chess();
        inGameState.makeMove(new Posititon(6, 5), new Posititon(4, 5));
        inGameState.makeMove(new Posititon(1, 4), new Posititon(2, 4));
        inGameState.makeMove(new Posititon(6, 2), new Posititon(4, 2));
        inGameState.makeMove(new Posititon(1, 2), new Posititon(3, 2));
        inGameState.makeMove(new Posititon(7, 3), new Posititon(4, 0));
        inGameState.makeMove(new Posititon(1, 1), new Posititon(2, 1));
        inGameState.makeMove(new Posititon(6, 3), new Posititon(4, 3));
        inGameState.makeMove(new Posititon(1, 6), new Posititon(3, 6));
        inGameState.makeMove(new Posititon(7, 2), new Posititon(5, 4));
        inGameState.makeMove(new Posititon(0, 2), new Posititon(1, 1));

        inCheckMateState = new Chess();
        inCheckMateState.makeMove(new Posititon(6, 5), new Posititon(4, 5));
        inCheckMateState.makeMove(new Posititon(1, 4), new Posititon(2, 4));
        inCheckMateState.makeMove(new Posititon(6, 2), new Posititon(4, 2));
        inCheckMateState.makeMove(new Posititon(1, 2), new Posititon(3, 2));
        inCheckMateState.makeMove(new Posititon(7, 3), new Posititon(4, 0));
        inCheckMateState.makeMove(new Posititon(1, 1), new Posititon(2, 1));
        inCheckMateState.makeMove(new Posititon(6, 3), new Posititon(5, 3));
        inCheckMateState.makeMove(new Posititon(1, 6), new Posititon(3, 6));
        inCheckMateState.makeMove(new Posititon(7, 2), new Posititon(5, 4));
        inCheckMateState.makeMove(new Posititon(0, 2), new Posititon(1, 1));
        inCheckMateState.makeMove(new Posititon(4, 0), new Posititon(1, 3));

        inWinState = new Chess();
        inWinState.makeMove(new Posititon(6, 5), new Posititon(5, 5));
        inWinState.makeMove(new Posititon(1, 4), new Posititon(3, 4));
        inWinState.makeMove(new Posititon(6, 6), new Posititon(4, 6));
        inWinState.makeMove(new Posititon(0, 3), new Posititon(4, 7));
    }

    @Test
    public void isCheckMateTest() {
        assertTrue(inCheckMateState.isCheckMate());
        assertFalse(initial.isCheckMate());
        assertTrue(inWinState.isCheckMate());
    }

    @Test
    public void ChessConstructorTest() {
        for (var i = 0; i < Chess.BOARD_SIZE; i++) {
            assertEquals(ChessPieces.BLACK_PAWN, initial.get(1, i));
        }

        for (var i = 0; i < Chess.BOARD_SIZE; i++) {
            assertEquals(ChessPieces.WHITE_PAWN, initial.get(6, i));
        }
        assertEquals(ChessPieces.BLACK_ROOK, initial.get(0, 0));
        assertEquals(ChessPieces.BLACK_ROOK, initial.get(0, 7));
        assertEquals(ChessPieces.BLACK_KNIGHT, initial.get(0, 1));
        assertEquals(ChessPieces.BLACK_KNIGHT, initial.get(0, 6));
        assertEquals(ChessPieces.BLACK_BISHOP, initial.get(0, 2));
        assertEquals(ChessPieces.BLACK_BISHOP, initial.get(0, 5));
        assertEquals(ChessPieces.BLACK_QUEEN, initial.get(0, 3));
        assertEquals(ChessPieces.BLACK_KING, initial.get(0, 4));

        assertEquals(ChessPieces.WHITE_ROOK, initial.get(7, 0));
        assertEquals(ChessPieces.WHITE_ROOK, initial.get(7, 7));
        assertEquals(ChessPieces.WHITE_KNIGHT, initial.get(7, 1));
        assertEquals(ChessPieces.WHITE_KNIGHT, initial.get(7, 6));
        assertEquals(ChessPieces.WHITE_BISHOP, initial.get(7, 2));
        assertEquals(ChessPieces.WHITE_BISHOP, initial.get(7, 5));
        assertEquals(ChessPieces.WHITE_QUEEN, initial.get(7, 3));
        assertEquals(ChessPieces.WHITE_KING, initial.get(7, 4));
    }

    @Test
    public void makeMoveTest() {
        initial.makeMove(new Posititon(6,4), new Posititon(4, 4));
        assertEquals(ChessPieces.EMPTY, initial.get(6, 4));
        assertEquals(ChessPieces.WHITE_PAWN, initial.get(4, 4));

        inGameState.makeMove(new Posititon(4, 3), new Posititon(3, 2));
        assertEquals(ChessPieces.EMPTY, inGameState.get(4, 3));
        assertEquals(ChessPieces.WHITE_PAWN, inGameState.get(3, 2));

        inCheckMateState.makeMove(new Posititon(0, 1), new Posititon(1, 3));
        assertEquals(ChessPieces.EMPTY, inCheckMateState.get(0, 1));
        assertEquals(ChessPieces.BLACK_KNIGHT, inCheckMateState.get(1, 3));
    }
    @Test
    public void isLegalMoveTest() {

    }
    @Test
    public void getNextPlayerTest() {

    }
    @Test
    public void isGameOverTest() {
        assertTrue(inWinState.isGameOver());
        assertFalse(initial.isGameOver());
        assertFalse(inCheckMateState.isGameOver());
    }
    @Test
    public void isLegalToMoveFromTest() {

    }
    @Test
    public void getStatusTest() {

    }
}
