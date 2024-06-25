package chess.model;

import chess.model.utils.TwoPhaseMoveState;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class Chess implements TwoPhaseMoveState<Posititon> {

    public static final int BOARD_SIZE = 8;
    private Player player;
    private final ReadOnlyObjectWrapper<ChessPieces>[][] board;
    public boolean[][] checkMateBoard = new boolean[BOARD_SIZE][BOARD_SIZE];

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
        createCheckMate(checkMateBoard ,board, player);
    }

    private void checkMateReset(boolean[][] board) {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = false;
            }
        }
    }

    private void createCheckMate(boolean[][] checkMateBoard, ReadOnlyObjectWrapper<ChessPieces>[][] board, Player player) {
        checkMateReset(checkMateBoard);
        switch (player) {
            case PLAYER_1 -> fillBlackCheckMateTable(checkMateBoard, board);
            case PLAYER_2 -> fillWhiteCheckMateTable(checkMateBoard, board);
        }
    }

    private void createCheckMate(boolean[][] checkMateBoard, ChessPieces[][] board, Player player) {
        checkMateReset(checkMateBoard);
        switch (player) {
            case PLAYER_1 -> fillBlackCheckMateTable(checkMateBoard, board);
            case PLAYER_2 -> fillWhiteCheckMateTable(checkMateBoard, board);
        }
    }

    private void fillWhiteCheckMateTable(boolean[][] checkMateBoard, ChessPieces[][] board) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (whiteEnemy(new Posititon(row, col))) {
                    switch (board[row][col]) {
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


    private void fillWhiteCheckMateTable(boolean[][] checkMateBoard, ReadOnlyObjectWrapper<ChessPieces>[][] board) {
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

    private void fillBlackCheckMateTable(boolean[][] checkMateBoard, ChessPieces[][] board) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (blackEnemy(new Posititon(row, col))) {
                    switch (board[row][col]) {
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

    private void fillBlackCheckMateTable(boolean[][] checkMateBoard,  ReadOnlyObjectWrapper<ChessPieces>[][] board) {
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

    private void bishopHitZone(Posititon posititon, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col() + j) == ChessPieces.EMPTY)) {
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
                    if (!(getPiece(posititon.row() - j, posititon.col() - j) == ChessPieces.EMPTY)) {
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
                    if (!(getPiece(posititon.row() + j, posititon.col() - j) == ChessPieces.EMPTY)) {
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
                    if (!(getPiece(posititon.row() - j, posititon.col() + j) == ChessPieces.EMPTY)) {
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
                    if (!(getPiece(posititon.row(), posititon.col() + j) == ChessPieces.EMPTY)) {
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
                    if (!(getPiece(posititon.row() + j, posititon.col()) == ChessPieces.EMPTY)) {
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
                    if (!(getPiece(posititon.row(), posititon.col() - j) == ChessPieces.EMPTY)) {
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
                    if (!(getPiece(posititon.row() - j, posititon.col()) == ChessPieces.EMPTY)) {
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



    private ChessPieces blackBackLine(int col) {
        ChessPieces chessPiece;
        switch (col) {
            case 0, 7 -> chessPiece = ChessPieces.BLACK_ROOK;
            case 1, 6 -> chessPiece = ChessPieces.BLACK_KNIGHT;
            case 2, 5 -> chessPiece = ChessPieces.BLACK_BISHOP;
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

    public ChessPieces getPiece(int row, int col) {
        if (isOnBoard(new Posititon(row, col))) {
            return board[row][col].get();
        }
        return ChessPieces.NONE;
    }

    /**
     * {@return whether it is checkmate}
     */
    public boolean isCheckMate(boolean[][] checkMateBoard) {
        switch (getNextPlayer()) {
            case PLAYER_1 -> {
                return isKingInCheckMate(ChessPieces.WHITE_KING, checkMateBoard);
            }
            case PLAYER_2 -> {
                return isKingInCheckMate(ChessPieces.BLACK_KING, checkMateBoard);
            }
        }
        throw new ArithmeticException("No player found!");
    }

    private boolean isKingInCheckMate(ChessPieces piece, boolean[][] checkMateBoard) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (getPiece(row, col) == piece) {
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
        return isOnBoard(from) && !isEmpty(from) && isPlayerPiece(from) && pieceCanMove(from) && isCheckMateEvasionMove(from);
    }

    private boolean isCheckMateEvasionMove(Posititon from) {
        if (!isCheckMate(checkMateBoard)) {
            return true;
        }
        return isCheckMateOver(from);
    }

    private boolean isCheckMateOver(Posititon posititon) {
        ChessPieces piece = board[posititon.row()][posititon.col()].get();
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
        boolean move2;
        boolean move3 = false;
        boolean move4 = false;
        if (posititon.row() == 1 && getPiece(posititon.row() + 1, posititon.col()) == ChessPieces.EMPTY && getPiece(posititon.row() + 2, posititon.col()) == ChessPieces.EMPTY) {
           move1 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() + 2, posititon.col()));
        }
        move2 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() + 1, posititon.col()));
        if (isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() + 1))) {
            move3 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() + 1, posititon.col() + 1));
        }
        if (isEnemyPiece(new Posititon(posititon.row() + 1, posititon.col() - 1))) {
            move4 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() + 1, posititon.col() - 1));
        }
        return move1 || move2 || move3 || move4;
    }

    private boolean isWhitePawnMoveEndCheckMate(Posititon posititon) {
        boolean move1 = false;
        boolean move2;
        boolean move3 = false;
        boolean move4 = false;
        if (posititon.row() == 6 && getPiece(posititon.row() - 1, posititon.col()) == ChessPieces.EMPTY && getPiece(posititon.row() - 2, posititon.col()) == ChessPieces.EMPTY) {
            move1 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() - 2, posititon.col()));
        }
        move2 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() - 1, posititon.col()));
        if (isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() + 1))) {
            move3 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() - 1, posititon.col() + 1));
        }
        if (isEnemyPiece(new Posititon(posititon.row() - 1, posititon.col() - 1))) {
            move4 = MoveToEndCheckMate(posititon, new Posititon(posititon.row() - 1, posititon.col() - 1));
        }
        return move1 || move2 || move3 || move4;
    }

    private boolean isBishopMoveEndCheckMate(Posititon posititon) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() + i))) {
                if (i == 1) {
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col() + j) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() + i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() - i, posititon.col() - i))) {
                if (i == 1) {
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col() - j) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col() - i))) {
                if (i == 1) {
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col() - j) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() -  i, posititon.col() + i))) {
                if (i == 1) {
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col() + j) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col() + i))) {
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
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row(), posititon.col() + j) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() + i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() + i, posititon.col()))) {
                if (i == 1) {
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col()))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() + j, posititon.col()) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() + i, posititon.col()))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row(), posititon.col() - i))) {
                if (i == 1) {
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row(), posititon.col() - j) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row(), posititon.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Posititon(posititon.row() -  i, posititon.col()))) {
                if (i == 1) {
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col()))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(posititon.row() - j, posititon.col()) == ChessPieces.EMPTY)) {
                        break;
                    }
                    if (MoveToEndCheckMate(posititon, new Posititon(posititon.row() - i, posititon.col()))) {
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
        Posititon pos5 = new Posititon(posititon.row(), posititon.col() -1);
        Posititon pos6 = new Posititon(posititon.row() - 1, posititon.col() - 1);
        Posititon pos7 = new Posititon(posititon.row() - 1, posititon.col() + 1);
        Posititon pos8 = new Posititon(posititon.row() - 1, posititon.col());
        return MoveToEndCheckMate(posititon, pos1) || MoveToEndCheckMate(posititon, pos2) ||
                MoveToEndCheckMate(posititon, pos3) || MoveToEndCheckMate(posititon, pos4) ||
                MoveToEndCheckMate(posititon, pos5) || MoveToEndCheckMate(posititon, pos6) ||
                MoveToEndCheckMate(posititon, pos7) || MoveToEndCheckMate(posititon, pos8);
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
        return MoveToEndCheckMate(posititon, pos1) || MoveToEndCheckMate(posititon, pos2) ||
                MoveToEndCheckMate(posititon, pos3) || MoveToEndCheckMate(posititon, pos4) ||
                MoveToEndCheckMate(posititon, pos5) || MoveToEndCheckMate(posititon, pos6) ||
                MoveToEndCheckMate(posititon, pos7) || MoveToEndCheckMate(posititon, pos8);
    }

    private boolean MoveToEndCheckMate(Posititon from, Posititon to) {
        boolean[][] tempCheckMateBoard = copyCheckMateBoard();
        ChessPieces[][] tempBoard = copyBoard();
        if (isOnBoard(to) || isEnemyPiece(to) || isEmpty(to)) {
            tempBoard[to.row()][to.col()] = (getPiece(from.row(), from.col()));
            tempBoard[from.row()][from.col()] = (ChessPieces.EMPTY);
            createCheckMate(tempCheckMateBoard, tempBoard, player);
            return !isCheckMate(tempCheckMateBoard);
        }
        return false;
    }

    private boolean[][] copyCheckMateBoard() {
        boolean[][] tempBoard = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                tempBoard[i][j] = checkMateBoard[i][j];
            }
        }
        return tempBoard;
    }

    private ChessPieces[][] copyBoard() {
        ChessPieces[][] tempBoard = new ChessPieces[BOARD_SIZE][BOARD_SIZE];
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
            return getPiece(posititon.row(), posititon.col()) == ChessPieces.EMPTY;
        }
        return false;
    }

    private boolean isPlayerPiece(Posititon posititon) {
        if (Player.PLAYER_1 == player) {
            return getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_PAWN ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_BISHOP ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_KNIGHT ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_ROOK ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_KING ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_QUEEN;
        }
        else
            return getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_PAWN ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_BISHOP ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_KNIGHT ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_ROOK ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_KING ||
                    getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_QUEEN;
    }
    private boolean pieceCanMove(Posititon posititon) {
        ChessPieces piece = getPiece(posititon.row(), posititon.col());
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
        return getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_PAWN
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_ROOK
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_KNIGHT
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_BISHOP
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_QUEEN
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.WHITE_KING;
    }

    private boolean blackEnemy(Posititon posititon) {
        return getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_PAWN
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_ROOK
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_KNIGHT
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_BISHOP
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_QUEEN
                || getPiece(posititon.row(), posititon.col()) == ChessPieces.BLACK_KING;
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
        board[to.row()][to.col()].set(getPiece(from.row(), from.col()));
        board[from.row()][from.col()].set(ChessPieces.EMPTY);
        player = player.opponent();
        createCheckMate(checkMateBoard, board, player);
    }
}
