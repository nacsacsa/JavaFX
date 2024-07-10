package chess.model;

import chess.model.utils.TwoPhaseMoveState;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class Chess implements TwoPhaseMoveState<Posititon> {

    public static final int BOARD_SIZE = 8;
    private Player player;
    private final ReadOnlyObjectWrapper<ChessPiece>[][] board;
    private ChessPiece[][] tempBoard;
    public boolean[][] checkMateBoard = new boolean[BOARD_SIZE][BOARD_SIZE];
    private boolean[][] tempCheckMateBoard = new boolean[BOARD_SIZE][BOARD_SIZE];

    public Chess() {
        board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(
                        switch (i) {
                            case 2, 3, 4, 5 -> ChessPiece.EMPTY;
                            case 0 -> blackBackLine(j);
                            case 1 -> ChessPiece.BLACK_PAWN;
                            case 6 -> ChessPiece.WHITE_PAWN;
                            default ->  whiteBackLine(j);
                        }
                );
            }
        }
        player = Player.PLAYER_1;
        createCheckMate(checkMateBoard ,board, player);
        checkMateReset(tempCheckMateBoard);
        tempBoard = new ChessPiece[8][8];
    }

    private void checkMateReset(boolean[][] board) {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = false;
            }
        }
    }

    private void createCheckMate(boolean[][] checkMateBoard, ReadOnlyObjectWrapper<ChessPiece>[][] board, Player player) {
        checkMateReset(checkMateBoard);
        switch (player) {
            case PLAYER_1 -> fillBlackCheckMateTable(checkMateBoard, board);
            case PLAYER_2 -> fillWhiteCheckMateTable(checkMateBoard, board);
        }
    }

    private void createCheckMate(boolean[][] tempCheckMateBoard, ChessPiece[][] tempBoard, Player player) {
        checkMateReset(tempCheckMateBoard);
        switch (player) {
            case PLAYER_1 -> fillBlackCheckMateTable(tempCheckMateBoard, tempBoard);
            case PLAYER_2 -> fillWhiteCheckMateTable(tempCheckMateBoard, tempBoard);
        }
    }

    private void fillWhiteCheckMateTable(boolean[][] tempCheckMateBoard, ChessPiece[][] tempBoard) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (whiteEnemyTemp(new Posititon(row, col))) {
                    switch (tempBoard[row][col]) {
                        case WHITE_PAWN -> whitePawnHitZone(new Posititon(row, col), tempCheckMateBoard);
                        case WHITE_KNIGHT -> knightHitZone(new Posititon(row, col), tempCheckMateBoard);
                        case WHITE_ROOK -> rookHitZoneTemp(new Posititon(row, col), tempCheckMateBoard);
                        case WHITE_BISHOP -> bishopHitZoneTemp(new Posititon(row, col), tempCheckMateBoard);
                        case WHITE_KING -> kingHitZone(new Posititon(row, col), tempCheckMateBoard);
                        case WHITE_QUEEN -> queenHitZoneTemp(new Posititon(row, col), tempCheckMateBoard);
                    }
                }
            }
        }
    }

    private void fillWhiteCheckMateTable(boolean[][] checkMateBoard, ReadOnlyObjectWrapper<ChessPiece>[][] board) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (whiteEnemy(new Posititon(row, col))) {
                    switch (board[row][col].get()) {
                        case WHITE_PAWN -> whitePawnHitZone(new Posititon(row, col), checkMateBoard);
                        case WHITE_KNIGHT -> knightHitZone(new Posititon(row, col), checkMateBoard);
                        case WHITE_ROOK -> rookHitZone(new Posititon(row, col), checkMateBoard);
                        case WHITE_BISHOP -> bishopHitZone(new Posititon(row, col), checkMateBoard);
                        case WHITE_KING -> kingHitZone(new Posititon(row, col), checkMateBoard);
                        case WHITE_QUEEN -> queenHitZone(new Posititon(row, col), checkMateBoard);
                    }
                }
            }
        }
    }

    private void fillBlackCheckMateTable(boolean[][] tempCheckMateBoard, ChessPiece[][] tempBoard) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (blackEnemyTemp(new Posititon(row, col))) {
                    switch (tempBoard[row][col]) {
                        case BLACK_PAWN -> blackPawnHitZone(new Posititon(row, col), tempCheckMateBoard);
                        case BLACK_KNIGHT -> knightHitZone(new Posititon(row, col), tempCheckMateBoard);
                        case BLACK_ROOK -> rookHitZoneTemp(new Posititon(row, col), tempCheckMateBoard);
                        case BLACK_BISHOP -> bishopHitZoneTemp(new Posititon(row, col), tempCheckMateBoard);
                        case BLACK_KING -> kingHitZone(new Posititon(row, col), tempCheckMateBoard);
                        case BLACK_QUEEN -> queenHitZoneTemp(new Posititon(row, col), tempCheckMateBoard);
                    }
                }
            }
        }
    }

    private void fillBlackCheckMateTable(boolean[][] checkMateBoard, ReadOnlyObjectWrapper<ChessPiece>[][] board) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (blackEnemy(new Posititon(row, col))) {
                    switch (board[row][col].get()) {
                        case BLACK_PAWN -> blackPawnHitZone(new Posititon(row, col), checkMateBoard);
                        case BLACK_KNIGHT -> knightHitZone(new Posititon(row, col), checkMateBoard);
                        case BLACK_ROOK -> rookHitZone(new Posititon(row, col), checkMateBoard);
                        case BLACK_BISHOP -> bishopHitZone(new Posititon(row, col), checkMateBoard);
                        case BLACK_KING -> kingHitZone(new Posititon(row, col), checkMateBoard);
                        case BLACK_QUEEN -> queenHitZone(new Posititon(row, col), checkMateBoard);
                    }
                }
            }
        }
    }

    private void whitePawnHitZone(Posititon posititon, boolean[][] checkMateBoard) {
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() + 1))) {
            checkMateBoard[posititon.row() - 1][posititon.col() + 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() - 1))) {
            checkMateBoard[posititon.row() - 1][posititon.col() - 1] = true;
        }
    }

    private void queenHitZone(Posititon posititon, boolean[][] checkMateBoard) {
        bishopHitZone(posititon, checkMateBoard);
        rookHitZone(posititon, checkMateBoard);
    }

    private void queenHitZoneTemp(Posititon posititon, boolean[][] checkMateBoard) {
        bishopHitZoneTemp(posititon, checkMateBoard);
        rookHitZoneTemp(posititon, checkMateBoard);
    }

    private void bishopHitZone(Posititon posititon, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() + i][posititon.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() - i][posititon.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() + i][posititon.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() - i][posititon.col() + i] = true;
            }
            legalMove = false;
        }
    }

    private void bishopHitZoneTemp(Posititon posititon, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row() + j, posititon.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() + i][posititon.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row() - j, posititon.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() - i][posititon.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row() + j, posititon.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() + i][posititon.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row() - j, posititon.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() - i][posititon.col() + i] = true;
            }
            legalMove = false;
        }
    }

    private void rookHitZone(Posititon posititon, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row(), posititon.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row(), posititon.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row()][posititon.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() + i][posititon.col()] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row(), posititon.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row(), posititon.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row()][posititon.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() - i][posititon.col()] = true;
            }
            legalMove = false;
        }
    }

    private void rookHitZoneTemp(Posititon posititon, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row(), posititon.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row(), posititon.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row()][posititon.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row() + j, posititon.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() + i][posititon.col()] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row(), posititon.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row(), posititon.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row()][posititon.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(posititon.row() - j, posititon.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[posititon.row() - i][posititon.col()] = true;
            }
            legalMove = false;
        }
    }

    private void kingHitZone(Posititon posititon, boolean[][] checkMateBoard) {
        if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() + 1))) {
            checkMateBoard[posititon.row() + 1][posititon.col() + 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() - 1))) {
            checkMateBoard[posititon.row() + 1][posititon.col() - 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col()))) {
            checkMateBoard[posititon.row() + 1][posititon.col()] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col()))) {
            checkMateBoard[posititon.row() - 1][posititon.col()] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() - 1))) {
            checkMateBoard[posititon.row() - 1][posititon.col() - 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() + 1))) {
            checkMateBoard[posititon.row() - 1][posititon.col() + 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row(), posititon.col() + 1))) {
            checkMateBoard[posititon.row()][posititon.col() + 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row(), posititon.col() - 1))) {
            checkMateBoard[posititon.row()][posititon.col() - 1] = true;
        }
    }

    private void blackPawnHitZone(Posititon posititon, boolean[][] checkMateBoard) {
            if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() + 1))) {
                checkMateBoard[posititon.row() + 1][posititon.col() + 1] = true;
            }
            if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() - 1))) {
                checkMateBoard[posititon.row() + 1][posititon.col() - 1] = true;
            }
    }

    private void knightHitZone(Posititon posititon, boolean[][] checkMateBoard) {
        if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() + 2))) {
            checkMateBoard[posititon.row() + 1][posititon.col() + 2] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() - 2))) {
            checkMateBoard[posititon.row() + 1][posititon.col() - 2] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() + 2, posititon.col() + 1))) {
            checkMateBoard[posititon.row() + 2][posititon.col() + 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() + 2, posititon.col() - 1))) {
            checkMateBoard[posititon.row() + 2][posititon.col() - 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() + 2))) {
            checkMateBoard[posititon.row() - 1][posititon.col() + 2] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() - 2))) {
            checkMateBoard[posititon.row() - 1][posititon.col() - 2] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 2, posititon.col() + 1))) {
            checkMateBoard[posititon.row() - 2][posititon.col() + 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 2, posititon.col() - 1))) {
            checkMateBoard[posititon.row() - 2][posititon.col() - 1] = true;
        }
    }

    private ChessPiece blackBackLine(int col) {
        ChessPiece chessPiece;
        switch (col) {
            case 0, 7 -> chessPiece = ChessPiece.BLACK_ROOK;
            case 1, 6 -> chessPiece = ChessPiece.BLACK_KNIGHT;
            case 2, 5 -> chessPiece = ChessPiece.BLACK_BISHOP;
            case 3 -> chessPiece = ChessPiece.BLACK_QUEEN;
            case 4 -> chessPiece = ChessPiece.BLACK_KING;
            default -> chessPiece = ChessPiece.EMPTY;
        }
        return chessPiece;
    }

    private ChessPiece whiteBackLine(int col) {
        ChessPiece chessPiece;
        switch (col) {
            case 0,7 -> chessPiece = ChessPiece.WHITE_ROOK;
            case 1,6 -> chessPiece = ChessPiece.WHITE_KNIGHT;
            case 2,5 -> chessPiece = ChessPiece.WHITE_BISHOP;
            case 3 -> chessPiece = ChessPiece.WHITE_QUEEN;
            case 4 -> chessPiece = ChessPiece.WHITE_KING;
            default -> chessPiece = ChessPiece.EMPTY;
        }
        return chessPiece;
    }

    public ReadOnlyObjectProperty<ChessPiece> getProperty(int row, int col) {
        return board[row][col].getReadOnlyProperty();
    }

    public ChessPiece getPiece(int row, int col) {
        if (isOnBoard(new Posititon(row, col))) {
            return board[row][col].get();
        }
        return ChessPiece.NONE;
    }

    private ChessPiece getTempPiece(int row, int col) {
        if (isOnBoard(new Posititon(row, col))) {
            return tempBoard[row][col];
        }
        return ChessPiece.NONE;
    }

    /**
     * {@return whether it is checkmate}
     */
    public boolean isCheckMate(boolean[][] checkMateBoard) {
        switch (getNextPlayer()) {
            case PLAYER_1 -> {
                return isKingInCheckMate(ChessPiece.WHITE_KING, checkMateBoard);
            }
            case PLAYER_2 -> {
                return isKingInCheckMate(ChessPiece.BLACK_KING, checkMateBoard);
            }
        }
        throw new ArithmeticException("No player found!");
    }

    private boolean isCheckMateTemp(boolean[][] checkMateBoard) {
        switch (getNextPlayer()) {
            case PLAYER_1 -> {
                return isKingInCheckMateTemp(ChessPiece.WHITE_KING, checkMateBoard);
            }
            case PLAYER_2 -> {
                return isKingInCheckMateTemp(ChessPiece.BLACK_KING, checkMateBoard);
            }
        }
        throw new ArithmeticException("No player found!");
    }

    private boolean isKingInCheckMate(ChessPiece piece, boolean[][] checkMateBoard) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (getPiece(row, col) == piece) {
                    return checkMateBoard[row][col];
                }
            }
        }
        return false;
    }

    private boolean isKingInCheckMateTemp(ChessPiece piece, boolean[][] checkMateBoard) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (getTempPiece(row, col) == piece) {
                    return checkMateBoard[row][col];
                }
            }
        }
        return false;
    }

    /**
     * {@return the player who moves next}
     */
    @Override
    public Player getNextPlayer() {
        return player;
    }

    /**
     * {@return whether the game is over}
     */
    @Override
    public boolean isGameOver() {
        return !isPlayerCanMove();
    }

    private boolean isPlayerCanMove() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                if (isPlayerPiece(new Posititon(i, j))) {
                    if (isLegalToMoveFrom(new Posititon(i, j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@return the status of the game}
     */
    @Override
    public Status getStatus() {
        if (!isGameOver()) {
            return Status.IN_PROGRESS;
        }
        return player == Player.PLAYER_2 ? Status.PLAYER_1_WINS : Status.PLAYER_2_WINS;
    }

    /**
     * {@return whether it is possible to make a move from the argument
     * specified}
     *
     * @param from represents where to move from
     */
    @Override
    public boolean isLegalToMoveFrom(Posititon from) {
        return isOnBoard(from) && !isEmpty(from) && isPlayerPiece(from) && pieceCanMove(from) && isCheckMateEvasionMove(from) && isNotACheckMateStep(from) && isNotCausesCheckMate(from);
    }

    private boolean isKingCanMove(Posititon from) {
        boolean move1 = false;
        boolean move2 = false;
        boolean move3 = false;
        boolean move4 = false;
        boolean move5 = false;
        boolean move6 = false;
        boolean move7 = false;
        boolean move8 = false;

        if (isOnBoard(new Posititon(from.row() + 1, from.col() + 1)) && (isEmpty(new Posititon(from.row() + 1, from.col() + 1)) || isEnemyPiece(new Posititon(from.row() + 1, from.col() + 1)))) {
            move1 = !checkMateBoard[from.row() + 1][from.col() + 1];
        }
        if (isOnBoard(new Posititon(from.row() + 1, from.col() - 1)) && (isEmpty(new Posititon(from.row() + 1, from.col() - 1)) || isEnemyPiece(new Posititon(from.row() + 1, from.col() - 1)))) {
            move2 = !checkMateBoard[from.row() + 1][from.col() - 1];
        }
        if (isOnBoard(new Posititon(from.row() + 1, from.col())) && (isEmpty(new Posititon(from.row() + 1, from.col())) || isEnemyPiece(new Posititon(from.row() + 1, from.col())))) {
            move3 = !checkMateBoard[from.row() + 1][from.col()];
        }
        if (isOnBoard(new Posititon(from.row(), from.col() + 1)) && (isEmpty(new Posititon(from.row(), from.col() + 1)) || isEnemyPiece(new Posititon(from.row(), from.col() + 1)))) {
            move4 = !checkMateBoard[from.row()][from.col() + 1];
        }
        if (isOnBoard(new Posititon(from.row() - 1, from.col() - 1)) && (isEmpty(new Posititon(from.row() - 1, from.col() - 1)) || isEnemyPiece(new Posititon(from.row() - 1, from.col() - 1)))) {
            move5 = !checkMateBoard[from.row() - 1][from.col() - 1];
        }
        if (isOnBoard(new Posititon(from.row() - 1, from.col() + 1)) && (isEmpty(new Posititon(from.row() - 1, from.col() + 1)) || isEnemyPiece(new Posititon(from.row() - 1, from.col() + 1)))) {
            move6 = !checkMateBoard[from.row() - 1][from.col() + 1];
        }
        if (isOnBoard(new Posititon(from.row() - 1, from.col())) && (isEmpty(new Posititon(from.row() - 1, from.col())) || isEnemyPiece(new Posititon(from.row() - 1, from.col())))) {
            move7 = !checkMateBoard[from.row() - 1][from.col()];
        }
        if (isOnBoard(new Posititon(from.row(), from.col() - 1)) && (isEmpty(new Posititon(from.row(), from.col() - 1)) || isEnemyPiece(new Posititon(from.row(), from.col() - 1)))) {
            move8 = !checkMateBoard[from.row()][from.col() - 1];
        }
        return move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    private boolean isNotACheckMateStep(Posititon from) {
        if (!isCheckMateOver(from)) {
            return false;
        }
        return true;
    }

    private boolean isNotCausesCheckMate(Posititon posititon) {
        ChessPiece piece = board[posititon.row()][posititon.col()].get();
        switch (piece) {
            case WHITE_KING, BLACK_KING -> {
                return isKingCanMove(posititon);
            }
            case WHITE_QUEEN, BLACK_QUEEN -> {
                return isQueenNotMoveToCheckMate(posititon);
            }
            case WHITE_BISHOP, BLACK_BISHOP -> {
                return isBishopNotMoveMoveToCheckMate(posititon);
            }
            case WHITE_ROOK, BLACK_ROOK -> {
                return isRookNotMoveMoveToCheckMate(posititon);
            }
            case WHITE_KNIGHT, BLACK_KNIGHT -> {
                return isKnightNotMoveToCheckMate(posititon);
            }
            case WHITE_PAWN -> {
                return isWhitePawnNotMoveToCheckMate(posititon);
            }
            case BLACK_PAWN -> {
                return isBlackPawnNotMoveToCheckMate(posititon);
            }
        }
        return false;
    }

    private boolean isQueenNotMoveToCheckMate(Posititon posititon) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithQueen(posititon, new Posititon(row, col))) {
                    if (moveToEndCheckMate(posititon, new Posititon(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBlackPawnNotMoveToCheckMate(Posititon posititon) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithBlackPawn(posititon, new Posititon(row, col))) {
                    if (moveToEndCheckMate(posititon, new Posititon(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isWhitePawnNotMoveToCheckMate(Posititon posititon) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithWhitePawn(posititon, new Posititon(row, col))) {
                    if (moveToEndCheckMate(posititon, new Posititon(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isKnightNotMoveToCheckMate(Posititon posititon) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithKnight(posititon, new Posititon(row, col))) {
                    if (moveToEndCheckMate(posititon, new Posititon(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isRookNotMoveMoveToCheckMate(Posititon posititon) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithRook(posititon, new Posititon(row, col))) {
                    if (moveToEndCheckMate(posititon, new Posititon(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBishopNotMoveMoveToCheckMate(Posititon posititon) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithBishop(posititon, new Posititon(row, col))) {
                    if (moveToEndCheckMate(posititon, new Posititon(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheckMateEvasionMove(Posititon from) {
        if (!isCheckMate(checkMateBoard)) {
            return true;
        }
        return isCheckMateOver(from);
    }

    private boolean isCheckMateOver(Posititon posititon) {
        ChessPiece piece = board[posititon.row()][posititon.col()].get();
        switch (piece) {
            case WHITE_KING, BLACK_KING -> {
                return isKingMoveEndCheckMate(posititon);
            }
            case WHITE_QUEEN, BLACK_QUEEN -> {
                return isQueenMoveEndCheckMate(posititon);
            }
            case WHITE_BISHOP, BLACK_BISHOP -> {
                return isBishopMoveEndCheckMate(posititon);
            }
            case WHITE_ROOK, BLACK_ROOK -> {
                return isRookMoveEndCheckMate(posititon);
            }
            case WHITE_KNIGHT, BLACK_KNIGHT -> {
                return isKnightMoveEndCheckMate(posititon);
            }
            case WHITE_PAWN -> {
                return isWhitePawnMoveEndCheckMate(posititon);
            }
            case BLACK_PAWN -> {
                return isBlackPawnMoveEndCheckMate(posititon);
            }
        }
        return false;
    }

    private boolean isBlackPawnMoveEndCheckMate(Posititon posititon) {
        boolean move1 = false;
        boolean move2 = false;
        boolean move3 = false;
        boolean move4 = false;
        if (posititon.row() == 1 && getPiece(posititon.row() + 1, posititon.col()) == ChessPiece.EMPTY && getPiece(posititon.row() + 2, posititon.col()) == ChessPiece.EMPTY) {
           move1 = moveToEndCheckMate(posititon, new Posititon(posititon.row() + 2, posititon.col()));
        }
        if  (getPiece(posititon.row() + 1, posititon.col()) == ChessPiece.EMPTY ) {
            move2 = moveToEndCheckMate(posititon, new Posititon(posititon.row() + 1, posititon.col()));
        }
        if (isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() + 1))) {
            move3 = moveToEndCheckMate(posititon, new Posititon(posititon.row() + 1, posititon.col() + 1));
        }
        if (isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() - 1))) {
            move4 = moveToEndCheckMate(posititon, new Posititon(posititon.row() + 1, posititon.col() - 1));
        }
        return move1 || move2 || move3 || move4;
    }

    private boolean isWhitePawnMoveEndCheckMate(Posititon posititon) {
        boolean move1 = false;
        boolean move2 = false;
        boolean move3 = false;
        boolean move4 = false;
        if (posititon.row() == 6 && getPiece(posititon.row() - 1, posititon.col()) == ChessPiece.EMPTY && getPiece(posititon.row() - 2, posititon.col()) == ChessPiece.EMPTY) {
            move1 = moveToEndCheckMate(posititon, new Posititon(posititon.row() - 2, posititon.col()));
        }
        if (getPiece(posititon.row() - 1, posititon.col()) == ChessPiece.EMPTY) {
            move2 = moveToEndCheckMate(posititon, new Posititon(posititon.row() - 1, posititon.col()));
        }
        if (isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() + 1))) {
            move3 = moveToEndCheckMate(posititon, new Posititon(posititon.row() - 1, posititon.col() + 1));
        }
        if (isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() - 1))) {
            move4 = moveToEndCheckMate(posititon, new Posititon(posititon.row() - 1, posititon.col() - 1));
        }
        return move1 || move2 || move3 || move4;
    }

    private boolean isBishopMoveEndCheckMate(Posititon posititon) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() + i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col() + j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() + i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col() - i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col() - j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() - i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col() - j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() -  i, posititon.col() + i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col() + j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() + i))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isRookMoveEndCheckMate(Posititon posititon) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row(), posititon.col() + i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row(), posititon.col() + j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() + i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col()))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col()))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col()) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col()))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row(), posititon.col() - i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row(), posititon.col() - j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() -  i, posititon.col()))) {
                if (i == 1) {
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col()))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col()) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isQueenMoveEndCheckMate(Posititon posititon) {
        return isBishopMoveEndCheckMate(posititon) || isRookMoveEndCheckMate(posititon);
    }

    private boolean isKingMoveEndCheckMate(Posititon posititon) {
        Posititon pos1 = new Posititon(posititon.row() + 1, posititon.col() + 1);
        Posititon pos2 = new Posititon(posititon.row() + 1, posititon.col() - 1);
        Posititon pos3 = new Posititon(posititon.row() + 1, posititon.col());
        Posititon pos4 = new Posititon(posititon.row(), posititon.col() + 1);
        Posititon pos5 = new Posititon(posititon.row(), posititon.col() - 1);
        Posititon pos6 = new Posititon(posititon.row() - 1, posititon.col() - 1);
        Posititon pos7 = new Posititon(posititon.row() - 1, posititon.col() + 1);
        Posititon pos8 = new Posititon(posititon.row() - 1, posititon.col());
        return moveToEndCheckMate(posititon, pos1) || moveToEndCheckMate(posititon, pos2) ||
                moveToEndCheckMate(posititon, pos3) || moveToEndCheckMate(posititon, pos4) ||
                moveToEndCheckMate(posititon, pos5) || moveToEndCheckMate(posititon, pos6) ||
                moveToEndCheckMate(posititon, pos7) || moveToEndCheckMate(posititon, pos8);
    }

    private boolean isKnightMoveEndCheckMate(Posititon posititon) {
        Posititon pos1 = new Posititon(posititon.row() + 1, posititon.col() + 2);
        Posititon pos2 = new Posititon(posititon.row() + 1, posititon.col() - 2);
        Posititon pos3 = new Posititon(posititon.row() + 2, posititon.col() + 1);
        Posititon pos4 = new Posititon(posititon.row() + 2, posititon.col() - 1);
        Posititon pos5 = new Posititon(posititon.row() - 1, posititon.col() + 2);
        Posititon pos6 = new Posititon(posititon.row() - 1, posititon.col() - 2);
        Posititon pos7 = new Posititon(posititon.row() - 2, posititon.col() + 1);
        Posititon pos8 = new Posititon(posititon.row() - 2, posititon.col() - 1);
        return moveToEndCheckMate(posititon, pos1) || moveToEndCheckMate(posititon, pos2) ||
                moveToEndCheckMate(posititon, pos3) || moveToEndCheckMate(posititon, pos4) ||
                moveToEndCheckMate(posititon, pos5) || moveToEndCheckMate(posititon, pos6) ||
                moveToEndCheckMate(posititon, pos7) || moveToEndCheckMate(posititon, pos8);
    }

    private boolean moveToEndCheckMate(Posititon from, Posititon to) {
        tempBoard = copyBoard();
        if (isOnBoard(to) || isEnemyPiece(to) || isEmpty(to)) {
            tempBoard[to.row()][to.col()] = (getPiece(from.row(), from.col()));
            tempBoard[from.row()][from.col()] = (ChessPiece.EMPTY);
            createCheckMate(tempCheckMateBoard, tempBoard, player);
            return !isCheckMateTemp(tempCheckMateBoard);
        }
        return false;
    }

    private ChessPiece[][] copyBoard() {
        ChessPiece[][] tempBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                tempBoard[i][j] = getPiece(i,j);
            }
        }
        return tempBoard;
    }

    private boolean isOnBoard(Posititon posititon) {
        return posititon.row() >= 0 && posititon.row() < BOARD_SIZE && posititon.col() >= 0 && posititon.col() < BOARD_SIZE;
    }

    private boolean isEmpty(Posititon posititon) {
        if (isOnBoard(posititon)) {
            return getPiece(posititon.row(), posititon.col()) == ChessPiece.EMPTY;
        }
        return false;
    }

    private boolean isPlayerPiece(Posititon posititon) {
        if (Player.PLAYER_1 == player) {
            return getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_PAWN ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_BISHOP ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_KNIGHT ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_ROOK ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_KING ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_QUEEN;
        }
        else
            return getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_PAWN ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_BISHOP ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_KNIGHT ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_ROOK ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_KING ||
                    getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_QUEEN;
    }

    private boolean pieceCanMove(Posititon posititon) {
        ChessPiece piece = getPiece(posititon.row(), posititon.col());
        switch (piece) {
            case BLACK_KNIGHT, WHITE_KNIGHT -> {
                return knightCanMove(posititon);
            }
            case BLACK_KING, WHITE_KING -> {
                return kingCanMove(posititon);
            }
            case BLACK_ROOK, WHITE_ROOK -> {
                return rookCanMove(posititon);
            }
            case BLACK_BISHOP, WHITE_BISHOP -> {
                return bishopCanMove(posititon);
            }
            case BLACK_QUEEN, WHITE_QUEEN -> {
                return queenCanMove(posititon);
            }
            case BLACK_PAWN, WHITE_PAWN -> {
                return pawnCanMove(posititon);
            }
            default -> {
                return false;
            }
        }
    }

    private boolean pawnCanMove(Posititon posititon) {
        switch (getPiece(posititon.row(), posititon.col())) {
            case WHITE_PAWN -> {
                return whitePieceCanMove(posititon);
            }
            case BLACK_PAWN -> {
                return blackPieceCanMove(posititon);
            }
        }
        return false;
    }

    private boolean blackPieceCanMove(Posititon posititon) {
        return isEmpty(new Posititon(posititon.row() + 1, posititon.col()))
                || (isEmpty(new Posititon(posititon.row() + 2, posititon.col())) && isEmpty(new Posititon(posititon.row() + 1, posititon.col())) && posititon.row() == 1)
                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() - 1))
                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() + 1));
    }

    private boolean isEnemyPiece(Posititon posititon) {
        switch (player) {
            case PLAYER_1 -> {
                return blackEnemy(posititon);
            }
            case PLAYER_2 -> {
                return whiteEnemy(posititon);
            }
        }
        throw new ArithmeticException("No player found");
    }

    private boolean whiteEnemy(Posititon posititon) {
        return getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_PAWN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_ROOK
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_KNIGHT
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_BISHOP
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_QUEEN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_KING;
    }

    private boolean whiteEnemyTemp(Posititon posititon) {
        return getTempPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_PAWN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_ROOK
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_KNIGHT
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_BISHOP
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_QUEEN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.WHITE_KING;
    }

    private boolean blackEnemy(Posititon posititon) {
        return getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_PAWN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_ROOK
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_KNIGHT
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_BISHOP
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_QUEEN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_KING;
    }

    private boolean blackEnemyTemp(Posititon posititon) {
        return getTempPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_PAWN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_ROOK
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_KNIGHT
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_BISHOP
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_QUEEN
                || getPiece(posititon.row(), posititon.col()) == ChessPiece.BLACK_KING;
    }

    private boolean whitePieceCanMove(Posititon posititon) {
        return isEmpty(new Posititon(posititon.row() - 1, posititon.col()))
                || (isEmpty(new Posititon(posititon.row() - 2, posititon.col())) && isEmpty(new Posititon(posititon.row() - 1, posititon.col())) && posititon.row() == 6)
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() - 1))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() + 1));
    }

    private boolean queenCanMove(Posititon posititon) {
        return kingCanMove(posititon);
    }

    private boolean bishopCanMove(Posititon posititon) {
        return isEmpty(new Posititon(posititon.row() + 1, posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col() - 1))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row() + 1, posititon.col() - 1))

                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() - 1))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() - 1));
    }

    private boolean rookCanMove(Posititon posititon) {
        return isEmpty(new Posititon(posititon.row(), posititon.col()))
                || isEmpty(new Posititon(posititon.row() + 1, posititon.col()))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col()))
                || isEmpty(new Posititon(posititon.row(), posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row(), posititon.col() - 1))

                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col()))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col()))
                || isEnemyPiece(new Posititon(posititon.row(), posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row(), posititon.col() - 1));
    }

    private boolean kingCanMove(Posititon posititon) {
        return isEmpty(new Posititon(posititon.row() + 1, posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row() + 1, posititon.col() - 1))
                || isEmpty(new Posititon(posititon.row() + 1, posititon.col()))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col()))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col() - 1))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row(), posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row(), posititon.col() - 1))

                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() - 1))
                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col()))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col()))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() - 1))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row(), posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row(), posititon.col() - 1));
    }

    private boolean knightCanMove(Posititon posititon) {
        return isEmpty(new Posititon(posititon.row() - 2, posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row() -2 , posititon.col() - 1))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col() + 2))
                || isEmpty(new Posititon(posititon.row() - 1, posititon.col() - 2))
                || isEmpty(new Posititon(posititon.row() + 2, posititon.col() + 1))
                || isEmpty(new Posititon(posititon.row() + 2, posititon.col() - 1))
                || isEmpty(new Posititon(posititon.row() + 1, posititon.col() + 2))
                || isEmpty(new Posititon(posititon.row() + 1, posititon.col() - 2))

                || isEnemyPiece(new Posititon(posititon.row() - 2, posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row() -2 , posititon.col() - 1))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() + 2))
                || isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() - 2))
                || isEnemyPiece(new Posititon(posititon.row() + 2, posititon.col() + 1))
                || isEnemyPiece(new Posititon(posititon.row() + 2, posititon.col() - 1))
                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() + 2))
                || isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() - 2));
    }

    /**
     * {@return whether the move provided can be applied to the state}
     *
     * @param from represents where to move from
     * @param to   represents where to move to
     */
    @Override
    public boolean isLegalMove(Posititon from, Posititon to) {
        return isLegalToMoveFrom(from) && isOnBoard(to) && (isEmpty(to) || isEnemyPiece(to)) && isLegalToMoveWithPiece(from, to) && isLegalToMoveIfCheckMate(from, to);
    }

    private boolean isLegalToMoveIfCheckMate(Posititon from, Posititon to) {
        return moveToEndCheckMate(from, to);
    }

    private boolean isLegalToMoveWithPiece(Posititon from, Posititon to) {
        switch (getPiece(from.row(), from.col())) {
            case WHITE_PAWN -> {
                return isLegalMoveWithWhitePawn(from, to);
            }
            case BLACK_PAWN -> {
                return isLegalMoveWithBlackPawn(from, to);
            }
            case WHITE_KING, BLACK_KING -> {
                return isLegalMoveWithKing(from, to);
            }
            case WHITE_ROOK, BLACK_ROOK -> {
                return isLegalMoveWithRook(from, to);
            }
            case WHITE_KNIGHT, BLACK_KNIGHT -> {
                return isLegalMoveWithKnight(from, to);
            }
            case WHITE_BISHOP, BLACK_BISHOP -> {
                return isLegalMoveWithBishop(from, to);
            }
            case WHITE_QUEEN, BLACK_QUEEN -> {
                return isLegalMoveWithQueen(from, to);
            }
        }
        return false;
    }

    private boolean isLegalMoveWithQueen(Posititon from, Posititon to) {
        return isLegalMoveWithBishop(from, to) || isLegalMoveWithRook(from, to);
    }

    private boolean isLegalMoveWithBishop(Posititon from, Posititon to) {
        return isLegalToMoveWithBishopRightUp(from, to) || isLegalToMoveWithBishopRightDown(from, to)
                || isLegalToMoveWithBishopLeftUp(from, to) || isLegalToMoveWithBishopLeftDown(from, to);
    }

    private boolean isLegalToMoveWithBishopLeftUp(Posititon from, Posititon to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() + i && from.col() == to.col() + i) {
                for (var j = 1; j < (from.col() - to.col()); j++) {
                    if (!isEmpty(new Posititon(from.row() - j, from.col() - j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalToMoveWithBishopLeftDown(Posititon from, Posititon to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() - i && from.col() == to.col() + i) {
                for (var j = 1; j < (to.row()- from.row()); j++) {
                    if (!isEmpty(new Posititon(from.row() + j, from.col() - j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalToMoveWithBishopRightDown(Posititon from, Posititon to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() - i && from.col() == to.col() - i) {
                for (var j = 1; j < (to.row() - from.row()); j++) {
                    if (!isEmpty(new Posititon(from.row() + j, from.col() + j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalToMoveWithBishopRightUp(Posititon from, Posititon to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() + i && from.col() == to.col() - i) {
                for (var j = 1; j < (to.col() - from.col()); j++) {
                    if (!isEmpty(new Posititon(from.row() - j, from.col() + j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalMoveWithRook(Posititon from, Posititon to) {
        return isLegalToMoveWithRookRight(from, to) || isLegalToMoveWithRookLeft(from, to)
        || isLegalToMoveWithRookUp(from, to) || isLegalToMoveWithRookDown(from, to);
    }

    private boolean isLegalToMoveWithRookDown(Posititon from, Posititon to) {
        if (from.col() != to.col()) {
            return false;
        }
        if (from.row() > to.row()) {
            return false;
        }
        for (var i = from.row() + 1; i < to.row(); i++) {
            if (!isEmpty(new Posititon(i, from.col()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalToMoveWithRookUp(Posititon from, Posititon to) {
        if (from.col() != to.col()) {
            return false;
        }
        if (from.row() < to.row()) {
            return false;
        }
        for (var i = from.row() - 1; i > to.row(); i--) {
            if (!isEmpty(new Posititon(i, from.col()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalToMoveWithRookLeft(Posititon from, Posititon to) {
        if (from.row() != to.row()) {
            return false;
        }
        if (from.col() < to.col()) {
            return false;
        }
        for (var i = from.col() - 1; i > to.col(); i--) {
            if (!isEmpty(new Posititon(from.row(), i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalToMoveWithRookRight(Posititon from, Posititon to) {
        if (from.row() != to.row()) {
            return false;
        }
        if (from.col() > to.col()) {
            return false;
        }
        for (var i = from.col() + 1; i < to.col(); i++) {
            if (!isEmpty(new Posititon(from.row(), i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalMoveWithKnight(Posititon from, Posititon to) {
        return (from.row() == to.row() + 2 && from.col() == to.col() + 1)
                || (from.row() == to.row() + 2 && from.col() == to.col() - 1)
                || (from.row() == to.row() - 2 && from.col() == to.col() - 1)
                || (from.row() == to.row() - 2 && from.col() == to.col() + 1)
                || (from.row() == to.row() + 1 && from.col() == to.col() + 2)
                || (from.row() == to.row() + 1 && from.col() == to.col() - 2)
                || (from.row() == to.row() - 1 && from.col() == to.col() + 2)
                || (from.row() == to.row() - 1 && from.col() == to.col() - 2);
    }

    private boolean isLegalMoveWithKing(Posititon from, Posititon to) {
        return ((from.row() == to.row() - 1 && from.col() == to.col() - 1))
                || ((from.row() == to.row() - 1 && from.col() == to.col() + 1))
                || ((from.row() == to.row() - 1 && from.col() == to.col()))
                || ((from.row() == to.row() + 1 && from.col() == to.col()))
                || ((from.row() == to.row() && from.col() == to.col() - 1))
                || ((from.row() == to.row() && from.col() == to.col() + 1))
                || ((from.row() == to.row() + 1 && from.col() == to.col() + 1))
                || ((from.row() == to.row() + 1 && from.col() == to.col() - 1));
    }

    private boolean isLegalMoveWithBlackPawn(Posititon from, Posititon to) {
        return (from.row() == to.row() - 1 && from.col() == to.col() && isEmpty(to))
        || (from.row() == to.row() - 2 && from.col() == to.col() && from.row() == 1 && isEmpty(new Posititon(to.row() - 1, to.col())) && isEmpty(to))
        || (from.row() == to.row() - 1 && from.col() == to.col() - 1 && isEnemyPiece(to))
        || (from.row() == to.row() - 1 && from.col() == to.col() + 1 && isEnemyPiece(to));

    }

    private boolean isLegalMoveWithWhitePawn(Posititon from, Posititon to) {
        return (from.row() == to.row() + 1 && from.col() == to.col() && isEmpty(to))
                || (from.row() == to.row() + 2 && from.col() == to.col() && from.row() == 6 && isEmpty(new Posititon(to.row() + 1, to.col())) && isEmpty(to))
                || (from.row() == to.row() + 1 && from.col() == to.col() - 1 && isEnemyPiece(to))
                || (from.row() == to.row() + 1 && from.col() == to.col() + 1 && isEnemyPiece(to));
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
        board[to.row()][to.col()].set(getPiece(from.row(), from.col()));
        board[from.row()][from.col()].set(ChessPiece.EMPTY);
        player = player.opponent();
        createCheckMate(checkMateBoard, board, player);
    }
}
