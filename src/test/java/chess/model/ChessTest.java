package chess.model;

import chess.model.utils.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.model.Chess.BOARD_SIZE;
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
    public void isCheckMateTest1() {
        assertTrue(inCheckMateState.isCheckMate(inCheckMateState.checkMateBoard));
        assertFalse(initial.isCheckMate(initial.checkMateBoard));
        assertTrue(inWinState.isCheckMate(inWinState.checkMateBoard));
    }
    @Test
    public void isCheckMateTest2() {
        inGameState.makeMove(new Posititon(4, 5), new Posititon(3, 6));
        inGameState.makeMove(new Posititon(0, 5), new Posititon(2, 3));
        inGameState.makeMove(new Posititon(4, 3), new Posititon(3, 2));
        inGameState.makeMove(new Posititon(2, 3), new Posititon(5, 6));
        assertTrue(inGameState.isCheckMate(inGameState.checkMateBoard));
    }
    @Test
    public void isCheckMateTest3() {
        inGameState.makeMove(new Posititon(4, 0), new Posititon(1, 3));
        assertTrue(inGameState.isCheckMate(inCheckMateState.checkMateBoard));
    }


    @Test
    public void isLegalMoveTestRook1() {
        initial.makeMove(new Posititon(7, 0), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(5 ,3)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(3 ,3)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(2 ,3)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(1 ,3)));
    }
    @Test
    public void isLegalMoveTestRook2() {
        initial.makeMove(new Posititon(7, 0), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,2)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,1)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,0)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,4)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,5)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,6)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,7)));
    }
    @Test
    public void isLegalMoveTestRook3() {
        initial.makeMove(new Posititon(7, 0), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(5 ,4)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(6 ,5)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(7 ,6)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(7 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(6 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(0 ,3)));
    }


    @Test
    public void isLegalMoveTestBishop1() {
        initial.makeMove(new Posititon(7, 2), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(5 ,4)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(6 ,5)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(7 ,6)));
    }
    @Test
    public void isLegalMoveTestBishop2() {
        initial.makeMove(new Posititon(7, 2), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(3 ,2)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(2 ,1)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(1 ,0)));
    }
    @Test
    public void isLegalMoveTestBishop3() {
        initial.makeMove(new Posititon(7, 2), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(5 ,2)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(6 ,1)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(7 ,0)));
    }
    @Test
    public void isLegalMoveTestBishop4() {
        initial.makeMove(new Posititon(7, 2), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(3 ,4)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(2 ,5)));
        assertTrue(initial.isLegalMove(new Posititon(4, 3), new Posititon(1 ,6)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(0 ,7)));
    }
    @Test
    public void isLegalMoveTestBishop5() {
        initial.makeMove(new Posititon(7, 2), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(5 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(6 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(7 ,3)));
    }
    @Test
    public void isLegalMoveTestBishop6() {
        initial.makeMove(new Posititon(7, 2), new Posititon(4, 3));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(4 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(3 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(2 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(1 ,3)));
        assertFalse(initial.isLegalMove(new Posititon(4, 3), new Posititon(0 ,3)));
    }


    @Test
    public void checkMateBoardTest1() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            assertTrue(initial.checkMateBoard[1][i]);
            assertTrue(initial.checkMateBoard[2][i]);
            assertFalse(initial.checkMateBoard[3][i]);
            assertFalse(initial.checkMateBoard[4][i]);
            assertFalse(initial.checkMateBoard[5][i]);
            assertFalse(initial.checkMateBoard[6][i]);
            assertFalse(initial.checkMateBoard[7][i]);
            if (i == 0 || i == 7) {
                assertFalse(initial.checkMateBoard[0][i]);
            }
            else assertTrue(initial.checkMateBoard[0][i]);
        }
    }
    @Test
    public void checkMateBoardTest2() {
        initial.makeMove(new Posititon(6, 3), new Posititon(5, 3));
        assertFalse(initial.checkMateBoard[2][0]);
        assertFalse(initial.checkMateBoard[2][1]);
        assertFalse(initial.checkMateBoard[2][2]);
        assertFalse(initial.checkMateBoard[2][3]);
        assertFalse(initial.checkMateBoard[2][4]);
        assertFalse(initial.checkMateBoard[2][5]);
        assertFalse(initial.checkMateBoard[2][6]);
        assertTrue(initial.checkMateBoard[2][7]);

        assertFalse(initial.checkMateBoard[3][0]);
        assertFalse(initial.checkMateBoard[3][1]);
        assertFalse(initial.checkMateBoard[3][2]);
        assertFalse(initial.checkMateBoard[3][3]);
        assertFalse(initial.checkMateBoard[3][4]);
        assertFalse(initial.checkMateBoard[3][5]);
        assertTrue(initial.checkMateBoard[3][6]);
        assertFalse(initial.checkMateBoard[3][7]);

        assertFalse(initial.checkMateBoard[4][0]);
        assertFalse(initial.checkMateBoard[4][1]);
        assertTrue(initial.checkMateBoard[4][2]);
        assertFalse(initial.checkMateBoard[4][3]);
        assertTrue(initial.checkMateBoard[4][4]);
        assertTrue(initial.checkMateBoard[4][5]);
        assertFalse(initial.checkMateBoard[4][6]);
        assertFalse(initial.checkMateBoard[4][7]);

        assertFalse(initial.checkMateBoard[7][0]);
        assertFalse(initial.checkMateBoard[7][7]);
        assertTrue(initial.checkMateBoard[7][1]);
        assertTrue(initial.checkMateBoard[7][2]);
        assertTrue(initial.checkMateBoard[7][3]);
        assertTrue(initial.checkMateBoard[7][4]);
        assertTrue(initial.checkMateBoard[7][5]);
        assertTrue(initial.checkMateBoard[7][6]);

        for (var i = 0; i < BOARD_SIZE; i++) {
            assertTrue(initial.checkMateBoard[6][i]);
            assertTrue(initial.checkMateBoard[5][i]);
            assertFalse(initial.checkMateBoard[0][i]);
            assertFalse(initial.checkMateBoard[1][i]);
        }
    }
    @Test
    public void checkMateBoardTest3() {
        assertTrue(inGameState.checkMateBoard[0][0]);
        assertTrue(inGameState.checkMateBoard[0][1]);
        assertTrue(inGameState.checkMateBoard[0][2]);
        assertTrue(inGameState.checkMateBoard[0][3]);
        assertTrue(inGameState.checkMateBoard[0][4]);
        assertTrue(inGameState.checkMateBoard[0][5]);
        assertTrue(inGameState.checkMateBoard[0][6]);
        assertFalse(inGameState.checkMateBoard[0][7]);

        assertTrue(inGameState.checkMateBoard[1][0]);
        assertFalse(inGameState.checkMateBoard[1][1]);
        assertTrue(inGameState.checkMateBoard[1][2]);
        assertTrue(inGameState.checkMateBoard[1][3]);
        assertTrue(inGameState.checkMateBoard[1][4]);
        assertTrue(inGameState.checkMateBoard[1][5]);
        assertTrue(inGameState.checkMateBoard[1][6]);
        assertTrue(inGameState.checkMateBoard[1][7]);

        assertTrue(inGameState.checkMateBoard[2][0]);
        assertTrue(inGameState.checkMateBoard[2][1]);
        assertTrue(inGameState.checkMateBoard[2][2]);
        assertTrue(inGameState.checkMateBoard[2][3]);
        assertTrue(inGameState.checkMateBoard[2][4]);
        assertTrue(inGameState.checkMateBoard[2][5]);
        assertTrue(inGameState.checkMateBoard[2][6]);
        assertTrue(inGameState.checkMateBoard[2][7]);

        assertTrue(inGameState.checkMateBoard[3][0]);
        assertFalse(inGameState.checkMateBoard[3][1]);
        assertTrue(inGameState.checkMateBoard[3][2]);
        assertTrue(inGameState.checkMateBoard[3][3]);
        assertFalse(inGameState.checkMateBoard[3][4]);
        assertTrue(inGameState.checkMateBoard[3][5]);
        assertTrue(inGameState.checkMateBoard[3][6]);
        assertFalse(inGameState.checkMateBoard[3][7]);

        assertFalse(inGameState.checkMateBoard[4][0]);
        assertTrue(inGameState.checkMateBoard[4][1]);
        assertFalse(inGameState.checkMateBoard[4][2]);
        assertTrue(inGameState.checkMateBoard[4][3]);
        assertTrue(inGameState.checkMateBoard[4][4]);
        assertTrue(inGameState.checkMateBoard[4][5]);
        assertFalse(inGameState.checkMateBoard[4][6]);
        assertTrue(inGameState.checkMateBoard[4][7]);

        assertFalse(inGameState.checkMateBoard[5][0]);
        assertFalse(inGameState.checkMateBoard[5][1]);
        assertFalse(inGameState.checkMateBoard[5][2]);
        assertFalse(inGameState.checkMateBoard[5][3]);
        assertFalse(inGameState.checkMateBoard[5][4]);
        assertTrue(inGameState.checkMateBoard[5][5]);
        assertFalse(inGameState.checkMateBoard[5][6]);
        assertFalse(inGameState.checkMateBoard[5][7]);

        assertFalse(inGameState.checkMateBoard[6][0]);
        assertFalse(inGameState.checkMateBoard[6][1]);
        assertFalse(inGameState.checkMateBoard[6][2]);
        assertFalse(inGameState.checkMateBoard[6][3]);
        assertFalse(inGameState.checkMateBoard[6][4]);
        assertFalse(inGameState.checkMateBoard[6][5]);
        assertTrue(inGameState.checkMateBoard[6][6]);
        assertFalse(inGameState.checkMateBoard[6][7]);

        assertFalse(inGameState.checkMateBoard[7][0]);
        assertFalse(inGameState.checkMateBoard[7][1]);
        assertFalse(inGameState.checkMateBoard[7][2]);
        assertFalse(inGameState.checkMateBoard[7][3]);
        assertFalse(inGameState.checkMateBoard[7][4]);
        assertFalse(inGameState.checkMateBoard[7][5]);
        assertFalse(inGameState.checkMateBoard[7][6]);
        assertFalse(inGameState.checkMateBoard[7][7]);
    }
    @Test
    public void checkMateBoardTest4() {
        inGameState.makeMove(new Posititon(6, 7), new Posititon(4, 7));
        assertFalse(inGameState.checkMateBoard[0][0]);
        assertFalse(inGameState.checkMateBoard[0][1]);
        assertFalse(inGameState.checkMateBoard[0][2]);
        assertFalse(inGameState.checkMateBoard[0][3]);
        assertFalse(inGameState.checkMateBoard[0][4]);
        assertFalse(inGameState.checkMateBoard[0][5]);
        assertFalse(inGameState.checkMateBoard[0][6]);
        assertFalse(inGameState.checkMateBoard[0][7]);

        assertTrue(inGameState.checkMateBoard[1][0]);
        assertFalse(inGameState.checkMateBoard[1][1]);
        assertFalse(inGameState.checkMateBoard[1][2]);
        assertTrue(inGameState.checkMateBoard[1][3]);
        assertFalse(inGameState.checkMateBoard[1][4]);
        assertFalse(inGameState.checkMateBoard[1][5]);
        assertFalse(inGameState.checkMateBoard[1][6]);
        assertFalse(inGameState.checkMateBoard[1][7]);

        assertTrue(inGameState.checkMateBoard[2][0]);
        assertFalse(inGameState.checkMateBoard[2][1]);
        assertTrue(inGameState.checkMateBoard[2][2]);
        assertFalse(inGameState.checkMateBoard[2][3]);
        assertFalse(inGameState.checkMateBoard[2][4]);
        assertFalse(inGameState.checkMateBoard[2][5]);
        assertFalse(inGameState.checkMateBoard[2][6]);
        assertFalse(inGameState.checkMateBoard[2][7]);

        assertTrue(inGameState.checkMateBoard[3][0]);
        assertTrue(inGameState.checkMateBoard[3][1]);
        assertTrue(inGameState.checkMateBoard[3][2]);
        assertTrue(inGameState.checkMateBoard[3][3]);
        assertTrue(inGameState.checkMateBoard[3][4]);
        assertFalse(inGameState.checkMateBoard[3][5]);
        assertTrue(inGameState.checkMateBoard[3][6]);
        assertFalse(inGameState.checkMateBoard[3][7]);

        assertFalse(inGameState.checkMateBoard[4][0]);
        assertTrue(inGameState.checkMateBoard[4][1]);
        assertTrue(inGameState.checkMateBoard[4][2]);
        assertTrue(inGameState.checkMateBoard[4][3]);
        assertFalse(inGameState.checkMateBoard[4][4]);
        assertTrue(inGameState.checkMateBoard[4][5]);
        assertFalse(inGameState.checkMateBoard[4][6]);
        assertTrue(inGameState.checkMateBoard[4][7]);

        assertTrue(inGameState.checkMateBoard[5][0]);
        assertTrue(inGameState.checkMateBoard[5][1]);
        assertTrue(inGameState.checkMateBoard[5][2]);
        assertTrue(inGameState.checkMateBoard[5][3]);
        assertFalse(inGameState.checkMateBoard[5][4]);
        assertTrue(inGameState.checkMateBoard[5][5]);
        assertFalse(inGameState.checkMateBoard[5][6]);
        assertTrue(inGameState.checkMateBoard[5][7]);

        assertTrue(inGameState.checkMateBoard[6][0]);
        assertFalse(inGameState.checkMateBoard[6][1]);
        assertTrue(inGameState.checkMateBoard[6][2]);
        assertTrue(inGameState.checkMateBoard[6][3]);
        assertTrue(inGameState.checkMateBoard[6][4]);
        assertTrue(inGameState.checkMateBoard[6][5]);
        assertTrue(inGameState.checkMateBoard[6][6]);
        assertTrue(inGameState.checkMateBoard[6][7]);

        assertFalse(inGameState.checkMateBoard[7][0]);
        assertTrue(inGameState.checkMateBoard[7][1]);
        assertTrue(inGameState.checkMateBoard[7][2]);
        assertTrue(inGameState.checkMateBoard[7][3]);
        assertFalse(inGameState.checkMateBoard[7][4]);
        assertTrue(inGameState.checkMateBoard[7][5]);
        assertTrue(inGameState.checkMateBoard[7][6]);
        assertFalse(inGameState.checkMateBoard[7][7]);
    }
    @Test
    public void checkMateBoardTest5() {
        inCheckMateState.makeMove(new Posititon(0, 5), new Posititon(4, 1));

        assertTrue(inCheckMateState.checkMateBoard[0][0]);
        assertTrue(inCheckMateState.checkMateBoard[0][1]);
        assertTrue(inCheckMateState.checkMateBoard[0][2]);
        assertTrue(inCheckMateState.checkMateBoard[0][3]);
        assertTrue(inCheckMateState.checkMateBoard[0][4]);
        assertTrue(inCheckMateState.checkMateBoard[0][5]);
        assertTrue(inCheckMateState.checkMateBoard[0][6]);
        assertFalse(inCheckMateState.checkMateBoard[0][7]);

        assertTrue(inCheckMateState.checkMateBoard[1][0]);
        assertFalse(inCheckMateState.checkMateBoard[1][1]);
        assertTrue(inCheckMateState.checkMateBoard[1][2]);
        assertTrue(inCheckMateState.checkMateBoard[1][3]);
        assertTrue(inCheckMateState.checkMateBoard[1][4]);
        assertTrue(inCheckMateState.checkMateBoard[1][5]);
        assertFalse(inCheckMateState.checkMateBoard[1][6]);
        assertTrue(inCheckMateState.checkMateBoard[1][7]);

        assertTrue(inCheckMateState.checkMateBoard[2][0]);
        assertTrue(inCheckMateState.checkMateBoard[2][1]);
        assertTrue(inCheckMateState.checkMateBoard[2][2]);
        assertFalse(inCheckMateState.checkMateBoard[2][3]);
        assertTrue(inCheckMateState.checkMateBoard[2][4]);
        assertTrue(inCheckMateState.checkMateBoard[2][5]);
        assertTrue(inCheckMateState.checkMateBoard[2][6]);
        assertTrue(inCheckMateState.checkMateBoard[2][7]);

        assertTrue(inCheckMateState.checkMateBoard[3][0]);
        assertFalse(inCheckMateState.checkMateBoard[3][1]);
        assertTrue(inCheckMateState.checkMateBoard[3][2]);
        assertTrue(inCheckMateState.checkMateBoard[3][3]);
        assertFalse(inCheckMateState.checkMateBoard[3][4]);
        assertTrue(inCheckMateState.checkMateBoard[3][5]);
        assertTrue(inCheckMateState.checkMateBoard[3][6]);
        assertFalse(inCheckMateState.checkMateBoard[3][7]);

        assertFalse(inCheckMateState.checkMateBoard[4][0]);
        assertTrue(inCheckMateState.checkMateBoard[4][1]);
        assertFalse(inCheckMateState.checkMateBoard[4][2]);
        assertTrue(inCheckMateState.checkMateBoard[4][3]);
        assertTrue(inCheckMateState.checkMateBoard[4][4]);
        assertTrue(inCheckMateState.checkMateBoard[4][5]);
        assertFalse(inCheckMateState.checkMateBoard[4][6]);
        assertTrue(inCheckMateState.checkMateBoard[4][7]);

        assertTrue(inCheckMateState.checkMateBoard[5][0]);
        assertFalse(inCheckMateState.checkMateBoard[5][1]);
        assertTrue(inCheckMateState.checkMateBoard[5][2]);
        assertFalse(inCheckMateState.checkMateBoard[5][3]);
        assertFalse(inCheckMateState.checkMateBoard[5][4]);
        assertTrue(inCheckMateState.checkMateBoard[5][5]);
        assertFalse(inCheckMateState.checkMateBoard[5][6]);
        assertFalse(inCheckMateState.checkMateBoard[5][7]);

        assertFalse(inCheckMateState.checkMateBoard[6][0]);
        assertFalse(inCheckMateState.checkMateBoard[6][1]);
        assertFalse(inCheckMateState.checkMateBoard[6][2]);
        assertTrue(inCheckMateState.checkMateBoard[6][3]);
        assertFalse(inCheckMateState.checkMateBoard[6][4]);
        assertFalse(inCheckMateState.checkMateBoard[6][5]);
        assertTrue(inCheckMateState.checkMateBoard[6][6]);
        assertFalse(inCheckMateState.checkMateBoard[6][7]);

        assertFalse(inCheckMateState.checkMateBoard[7][0]);
        assertFalse(inCheckMateState.checkMateBoard[7][1]);
        assertFalse(inCheckMateState.checkMateBoard[7][2]);
        assertFalse(inCheckMateState.checkMateBoard[7][3]);
        assertTrue(inCheckMateState.checkMateBoard[7][4]);
        assertFalse(inCheckMateState.checkMateBoard[7][5]);
        assertFalse(inCheckMateState.checkMateBoard[7][6]);
        assertFalse(inCheckMateState.checkMateBoard[7][7]);
    }
    @Test
    public void checkMateBoardTest6() {
        assertFalse(inCheckMateState.checkMateBoard[0][0]);
        assertFalse(inCheckMateState.checkMateBoard[0][1]);
        assertTrue(inCheckMateState.checkMateBoard[0][2]);
        assertTrue(inCheckMateState.checkMateBoard[0][3]);
        assertTrue(inCheckMateState.checkMateBoard[0][4]);
        assertFalse(inCheckMateState.checkMateBoard[0][5]);
        assertFalse(inCheckMateState.checkMateBoard[0][6]);
        assertFalse(inCheckMateState.checkMateBoard[0][7]);

        assertFalse(inCheckMateState.checkMateBoard[1][0]);
        assertTrue(inCheckMateState.checkMateBoard[1][1]);
        assertTrue(inCheckMateState.checkMateBoard[1][2]);
        assertFalse(inCheckMateState.checkMateBoard[1][3]);
        assertTrue(inCheckMateState.checkMateBoard[1][4]);
        assertTrue(inCheckMateState.checkMateBoard[1][5]);
        assertFalse(inCheckMateState.checkMateBoard[1][6]);
        assertFalse(inCheckMateState.checkMateBoard[1][7]);

        assertFalse(inCheckMateState.checkMateBoard[2][0]);
        assertFalse(inCheckMateState.checkMateBoard[2][1]);
        assertTrue(inCheckMateState.checkMateBoard[2][2]);
        assertTrue(inCheckMateState.checkMateBoard[2][3]);
        assertTrue(inCheckMateState.checkMateBoard[2][4]);
        assertFalse(inCheckMateState.checkMateBoard[2][5]);
        assertFalse(inCheckMateState.checkMateBoard[2][6]);
        assertFalse(inCheckMateState.checkMateBoard[2][7]);

        assertFalse(inCheckMateState.checkMateBoard[3][0]);
        assertTrue(inCheckMateState.checkMateBoard[3][1]);
        assertTrue(inCheckMateState.checkMateBoard[3][2]);
        assertTrue(inCheckMateState.checkMateBoard[3][3]);
        assertTrue(inCheckMateState.checkMateBoard[3][4]);
        assertFalse(inCheckMateState.checkMateBoard[3][5]);
        assertTrue(inCheckMateState.checkMateBoard[3][6]);
        assertFalse(inCheckMateState.checkMateBoard[3][7]);

        assertTrue(inCheckMateState.checkMateBoard[4][0]);
        assertFalse(inCheckMateState.checkMateBoard[4][1]);
        assertTrue(inCheckMateState.checkMateBoard[4][2]);
        assertTrue(inCheckMateState.checkMateBoard[4][3]);
        assertTrue(inCheckMateState.checkMateBoard[4][4]);
        assertTrue(inCheckMateState.checkMateBoard[4][5]);
        assertFalse(inCheckMateState.checkMateBoard[4][6]);
        assertFalse(inCheckMateState.checkMateBoard[4][7]);

        assertTrue(inCheckMateState.checkMateBoard[5][0]);
        assertTrue(inCheckMateState.checkMateBoard[5][1]);
        assertTrue(inCheckMateState.checkMateBoard[5][2]);
        assertTrue(inCheckMateState.checkMateBoard[5][3]);
        assertFalse(inCheckMateState.checkMateBoard[5][4]);
        assertTrue(inCheckMateState.checkMateBoard[5][5]);
        assertTrue(inCheckMateState.checkMateBoard[5][6]);
        assertTrue(inCheckMateState.checkMateBoard[5][7]);

        assertTrue(inCheckMateState.checkMateBoard[6][0]);
        assertFalse(inCheckMateState.checkMateBoard[6][1]);
        assertFalse(inCheckMateState.checkMateBoard[6][2]);
        assertTrue(inCheckMateState.checkMateBoard[6][3]);
        assertTrue(inCheckMateState.checkMateBoard[6][4]);
        assertTrue(inCheckMateState.checkMateBoard[6][5]);
        assertTrue(inCheckMateState.checkMateBoard[6][6]);
        assertTrue(inCheckMateState.checkMateBoard[6][7]);

        assertFalse(inCheckMateState.checkMateBoard[7][0]);
        assertTrue(inCheckMateState.checkMateBoard[7][1]);
        assertTrue(inCheckMateState.checkMateBoard[7][2]);
        assertTrue(inCheckMateState.checkMateBoard[7][3]);
        assertFalse(inCheckMateState.checkMateBoard[7][4]);
        assertTrue(inCheckMateState.checkMateBoard[7][5]);
        assertTrue(inCheckMateState.checkMateBoard[7][6]);
        assertFalse(inCheckMateState.checkMateBoard[7][7]);
    }
    @Test
    public void checkMateBoardTest7() {
        inGameState.makeMove(new Posititon(4, 5), new Posititon(3, 6));
        inGameState.makeMove(new Posititon(0, 5), new Posititon(2, 3));
        inGameState.makeMove(new Posititon(4, 3), new Posititon(3, 2));
        inGameState.makeMove(new Posititon(2, 3), new Posititon(5, 6));
        assertTrue(inGameState.checkMateBoard[7][4]);
        assertFalse(inGameState.checkMateBoard[7][5]);
    }


    @Test
    public void ChessConstructorTest() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            assertEquals(ChessPiece.BLACK_PAWN, initial.getPiece(1, i));
        }

        for (var i = 0; i < BOARD_SIZE; i++) {
            assertEquals(ChessPiece.WHITE_PAWN, initial.getPiece(6, i));
        }
        assertEquals(ChessPiece.BLACK_ROOK, initial.getPiece(0, 0));
        assertEquals(ChessPiece.BLACK_ROOK, initial.getPiece(0, 7));
        assertEquals(ChessPiece.BLACK_KNIGHT, initial.getPiece(0, 1));
        assertEquals(ChessPiece.BLACK_KNIGHT, initial.getPiece(0, 6));
        assertEquals(ChessPiece.BLACK_BISHOP, initial.getPiece(0, 2));
        assertEquals(ChessPiece.BLACK_BISHOP, initial.getPiece(0, 5));
        assertEquals(ChessPiece.BLACK_QUEEN, initial.getPiece(0, 3));
        assertEquals(ChessPiece.BLACK_KING, initial.getPiece(0, 4));

        assertEquals(ChessPiece.WHITE_ROOK, initial.getPiece(7, 0));
        assertEquals(ChessPiece.WHITE_ROOK, initial.getPiece(7, 7));
        assertEquals(ChessPiece.WHITE_KNIGHT, initial.getPiece(7, 1));
        assertEquals(ChessPiece.WHITE_KNIGHT, initial.getPiece(7, 6));
        assertEquals(ChessPiece.WHITE_BISHOP, initial.getPiece(7, 2));
        assertEquals(ChessPiece.WHITE_BISHOP, initial.getPiece(7, 5));
        assertEquals(ChessPiece.WHITE_QUEEN, initial.getPiece(7, 3));
        assertEquals(ChessPiece.WHITE_KING, initial.getPiece(7, 4));
    }


    @Test
    public void makeMoveTest1() {
        initial.makeMove(new Posititon(6, 4), new Posititon(4, 4));
        assertEquals(ChessPiece.EMPTY, initial.getPiece(6, 4));
        assertEquals(ChessPiece.WHITE_PAWN, initial.getPiece(4, 4));
    }
    @Test
    public void makeMoveTest2() {
        inGameState.makeMove(new Posititon(4, 3), new Posititon(3, 2));
        assertEquals(ChessPiece.EMPTY, inGameState.getPiece(4, 3));
        assertEquals(ChessPiece.WHITE_PAWN, inGameState.getPiece(3, 2));
    }
    @Test
    public void makeMoveTest() {
        inCheckMateState.makeMove(new Posititon(0, 1), new Posititon(1, 3));
        assertEquals(ChessPiece.EMPTY, inCheckMateState.getPiece(0, 1));
        assertEquals(ChessPiece.BLACK_KNIGHT, inCheckMateState.getPiece(1, 3));
    }


    @Test
    public void isLegalMoveTest1() {
        assertTrue(initial.isLegalMove(new Posititon(6, 3), new Posititon(4, 3)));
        assertTrue(initial.isLegalMove(new Posititon(6, 3), new Posititon(5, 3)));
        assertFalse(initial.isLegalMove(new Posititon(6, 3), new Posititon(5, 2)));
        assertFalse(initial.isLegalMove(new Posititon(6, 3), new Posititon(3, 3)));
        assertTrue(initial.isLegalMove(new Posititon(7, 1), new Posititon(5, 0)));
        assertTrue(initial.isLegalMove(new Posititon(7, 1), new Posititon(5, 2)));
        assertFalse(initial.isLegalMove(new Posititon(7, 0), new Posititon(3, 0)));
        assertFalse(initial.isLegalMove(new Posititon(7, 2), new Posititon(6, 1)));
        assertFalse(initial.isLegalMove(new Posititon(1, 4), new Posititon(2, 4)));
        assertFalse(initial.isLegalMove(new Posititon(8, 8), new Posititon(3, 3)));
        assertFalse(initial.isLegalMove(new Posititon(8, 0), new Posititon(9, 9)));
        assertFalse(initial.isLegalMove(new Posititon(-1, -1), new Posititon(-2, -2)));
        assertFalse(initial.isLegalMove(new Posititon(-4, -4), new Posititon(4, 3)));

        assertTrue(inGameState.isLegalMove(new Posititon(4, 3), new Posititon(3, 2)));
        assertFalse(inGameState.isLegalMove(new Posititon(5, 4), new Posititon(4, 5)));
        assertFalse(inGameState.isLegalMove(new Posititon(5, 4), new Posititon(3, 6)));
        assertFalse(inGameState.isLegalMove(new Posititon(4, 2), new Posititon(3, 2)));
        assertTrue(inGameState.isLegalMove(new Posititon(4, 0), new Posititon(2, 2)));
        assertTrue(inGameState.isLegalMove(new Posititon(4, 0), new Posititon(1, 3)));
        assertFalse(inGameState.isLegalMove(new Posititon(0, 2), new Posititon(0, 1)));
        assertFalse(inGameState.isLegalMove(new Posititon(1, 1), new Posititon(6, 6)));
        assertFalse(inGameState.isLegalMove(new Posititon(4, 2), new Posititon(5, 2)));
        assertTrue(inGameState.isLegalMove(new Posititon(7, 1), new Posititon(6, 3)));
        assertTrue(inGameState.isLegalMove(new Posititon(5, 4), new Posititon(6, 3)));

        assertTrue(inCheckMateState.isLegalMove(new Posititon(0, 1), new Posititon(1, 3)));
        assertFalse(inCheckMateState.isLegalMove(new Posititon(0, 1), new Posititon(2, 0)));
        assertFalse(inCheckMateState.isLegalMove(new Posititon(0, 3), new Posititon(1, 2)));
        assertFalse(inCheckMateState.isLegalMove(new Posititon(0, 3), new Posititon(0, 2)));
        assertTrue(inCheckMateState.isLegalMove(new Posititon(0, 3), new Posititon(1, 3)));
        assertTrue(inCheckMateState.isLegalMove(new Posititon(0, 4), new Posititon(1, 3)));
        assertFalse(inCheckMateState.isLegalMove(new Posititon(0, 5), new Posititon(1, 4)));
        assertFalse(inCheckMateState.isLegalMove(new Posititon(3, 2), new Posititon(2, 2)));
        assertFalse(inCheckMateState.isLegalMove(new Posititon(6, 0), new Posititon(5, 0)));
    }
    @Test
    public void isLegalMoveTest2() {
        initial.makeMove(new Posititon(6, 4), new Posititon(5, 4));
        initial.makeMove(new Posititon(1, 4), new Posititon(2, 4));
        initial.makeMove(new Posititon(7, 4), new Posititon(6, 4));
        initial.makeMove(new Posititon(0, 3), new Posititon(4, 7));
        initial.makeMove(new Posititon(6, 5), new Posititon(5, 5));
        initial.makeMove(new Posititon(1, 0), new Posititon(2, 0));
        assertFalse(initial.isLegalMove(new Posititon(6, 4), new Posititon(7, 4)));
        assertFalse(initial.isLegalMove(new Posititon(6, 4), new Posititon(6, 5)));
    }
    @Test
    public void isLegalMoveTest3() {
        inGameState.makeMove(new Posititon(6,0), new Posititon(5, 0));
        assertFalse(inGameState.isLegalMove(new Posititon(1, 3), new Posititon(2, 3)));
    }
    @Test
    public void isLegalMoveTest4() {
        initial.makeMove(new Posititon(6, 4), new Posititon(4, 4));
        initial.makeMove(new Posititon(1, 3), new Posititon(3, 3));
        initial.makeMove(new Posititon(6, 0), new Posititon(5, 0));
        initial.makeMove(new Posititon(0, 3), new Posititon(2, 4));
        assertFalse(initial.isLegalMove(new Posititon(4, 4), new Posititon(3, 3)));
    }


    @Test
    public void getNextPlayerTest() {
        assertEquals(State.Player.PLAYER_1, initial.getNextPlayer());
        assertEquals(State.Player.PLAYER_1, inGameState.getNextPlayer());
        assertEquals(State.Player.PLAYER_2, inCheckMateState.getNextPlayer());
        assertEquals(State.Player.PLAYER_1, inWinState.getNextPlayer());
    }


    @Test
    public void isGameOverTest1() {
        assertTrue(inWinState.isGameOver());
        assertFalse(initial.isGameOver());
        assertFalse(inCheckMateState.isGameOver());
    }
    @Test
    public void isGameOverTest2() {
        inGameState.makeMove(new Posititon(4, 5), new Posititon(3, 6));
        inGameState.makeMove(new Posititon(0, 5), new Posititon(2, 3));
        inGameState.makeMove(new Posititon(4, 3), new Posititon(3, 2));
        inGameState.makeMove(new Posititon(2, 3), new Posititon(5, 6));
        assertFalse(inGameState.isGameOver());
    }


    @Test
    public void isLegalToMoveFromTest1() {
        assertTrue(initial.isLegalToMoveFrom(new Posititon(6, 3)));
        assertTrue(initial.isLegalToMoveFrom(new Posititon(7, 1)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(7, 0)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(7, 3)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(7, 2)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(7, 4)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(0, 0)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(0, 1)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(0, 2)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(0, 3)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(0, 4)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(1, 0)));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(1, 4)));

        assertTrue(inGameState.isLegalToMoveFrom(new Posititon(4, 0)));
        assertTrue(inGameState.isLegalToMoveFrom(new Posititon(4, 3)));
        assertTrue(inGameState.isLegalToMoveFrom(new Posititon(5, 4)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(4, 2)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(7, 0)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(7, 5)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(0, 0)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(0, 1)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(0, 2)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(0, 3)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(3, 2)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(8, 3)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(3, 8)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(8, 8)));
        assertFalse(inGameState.isLegalToMoveFrom(new Posititon(-1, -1)));

        assertEquals(ChessPiece.WHITE_QUEEN, inCheckMateState.getPiece(1, 3));
        assertTrue(inCheckMateState.isLegalToMoveFrom(new Posititon(0, 1)));
        assertEquals(ChessPiece.WHITE_QUEEN, inCheckMateState.getPiece(1, 3));
        assertTrue(inCheckMateState.isCheckMate(inCheckMateState.checkMateBoard));
        assertTrue(inCheckMateState.isLegalToMoveFrom(new Posititon(0, 3)));
        assertTrue(inCheckMateState.isLegalToMoveFrom(new Posititon(0, 4)));
        assertFalse(inCheckMateState.isLegalToMoveFrom(new Posititon(0, 0)));
        assertFalse(inCheckMateState.isLegalToMoveFrom(new Posititon(1, 1)));
        assertFalse(inCheckMateState.isLegalToMoveFrom(new Posititon(0, 5)));
        assertFalse(inCheckMateState.isLegalToMoveFrom(new Posititon(2, 4)));
        assertFalse(inCheckMateState.isLegalToMoveFrom(new Posititon(1, 3)));
        assertFalse(inCheckMateState.isLegalToMoveFrom(new Posititon(4, 2)));
        assertFalse(inCheckMateState.isLegalToMoveFrom(new Posititon(7, 1)));
    }
    @Test
    public void isLegalToMoveFromTest2() {
        initial.makeMove(new Posititon(6, 4), new Posititon(5, 4));
        initial.makeMove(new Posititon(1, 5), new Posititon(2, 5));
        initial.makeMove(new Posititon(7, 3), new Posititon(3, 7));
        assertTrue(initial.isLegalToMoveFrom(new Posititon(1, 6)));
    }
    @Test
    public void isLegalToMoveFromTest3() {
        initial.makeMove(new Posititon(6, 4), new Posititon(5, 4));
        initial.makeMove(new Posititon(1, 5), new Posititon(2, 5));
        initial.makeMove(new Posititon(7, 3), new Posititon(3, 7));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(0, 3)));
    }
    @Test
    public void isLegalToMoveFromTest4() {
        initial.makeMove(new Posititon(6, 4), new Posititon(5, 4));
        initial.makeMove(new Posititon(1, 5), new Posititon(2, 5));
        initial.makeMove(new Posititon(7, 3), new Posititon(3, 7));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(0, 4)));
    }
    @Test
    public void isLegalToMoveFromTest5() {
        initial.makeMove(new Posititon(7, 6), new Posititon(5, 5));
        initial.makeMove(new Posititon(0, 3), new Posititon(3, 4));
        initial.makeMove(new Posititon(6, 4), new Posititon(4, 4));
        initial.makeMove(new Posititon(3, 4), new Posititon(4, 4));
        assertFalse(initial.isLegalToMoveFrom(new Posititon(5, 5)));
    }

    @Test
    public void getStatusTest() {
        assertEquals(State.Status.IN_PROGRESS, initial.getStatus());
        assertEquals(State.Status.IN_PROGRESS, inGameState.getStatus());
        assertEquals(State.Status.IN_PROGRESS, inCheckMateState.getStatus());
        assertEquals(State.Status.PLAYER_2_WINS, inWinState.getStatus());
    }
}
