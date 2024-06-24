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
        createCheckMate();
    }

    private void checkMateReset() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                checkMateBoard[i][j] = false;
            }
        }
    }

    private void createCheckMate() {
        checkMateReset();
        switch (player) {
            case PLAYER_1 -> fillBlackCheckMateTable();
            case PLAYER_2 -> fillWhiteCheckMateTable();
        }
    }

    private void fillWhiteCheckMateTable() {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (blackEnemy(new Posititon(row, col))) {
                    switch (board[row][col].get()) {
                        case WHITE_PAWN -> whitePawnHitZone(new Posititon(row, col));
                        case WHITE_KNIGHT -> knightHitZone(new Posititon(row, col));
                        case WHITE_ROOK -> rookHitZone(new Posititon(row, col));
                        case WHITE_BISHOP -> bishopHitZone(new Posititon(row, col));
                        case WHITE_KING -> kingHitZone(new Posititon(row, col));
                        case WHITE_QUEEN -> queenHitZone(new Posititon(row, col));
                    }
                }
            }
        }
    }

    private void fillBlackCheckMateTable() {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (blackEnemy(new Posititon(row, col))) {
                    switch (board[row][col].get()) {
                        case BLACK_PAWN -> blackPawnHitZone(new Posititon(row, col));
                        case BLACK_KNIGHT -> knightHitZone(new Posititon(row, col));
                        case BLACK_ROOK -> rookHitZone(new Posititon(row, col));
                        case BLACK_BISHOP -> bishopHitZone(new Posititon(row, col));
                        case BLACK_KING -> kingHitZone(new Posititon(row, col));
                        case BLACK_QUEEN -> queenHitZone(new Posititon(row, col));
                    }
                }
            }
        }
    }

    private void whitePawnHitZone(Posititon posititon) {
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() + 1))) {
            checkMateBoard[posititon.row() - 1][posititon.col() + 1] = true;
        }
        if (isOnBoard(new Posititon(posititon.row() - 1, posititon.col() - 1))) {
            checkMateBoard[posititon.row() - 1][posititon.col() - 1] = true;
        }
    }

    private void queenHitZone(Posititon posititon) {
        bishopHitZone(posititon);
        rookHitZone(posititon);
    }

    private void bishopHitZone(Posititon posititon) {
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

    private void rookHitZone(Posititon posititon) {
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

    private void kingHitZone(Posititon posititon) {
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

    private void blackPawnHitZone(Posititon posititon) {
            if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() + 1))) {
                checkMateBoard[posititon.row() + 1][posititon.col() + 1] = true;
            }
            if (isOnBoard(new Posititon(posititon.row() + 1, posititon.col() - 1))) {
                checkMateBoard[posititon.row() + 1][posititon.col() - 1] = true;
            }
    }

    private void knightHitZone(Posititon posititon) {
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
    public boolean isCheckMate() {
        switch (player) {
            case PLAYER_1 -> {
                return isWhiteKingInCheckMate();
            }
            case PLAYER_2 -> {
                return isBlackKingInCheckMate();
            }
        }
        throw new ArithmeticException("No player found!");
    }

    private boolean isWhiteKingInCheckMate() {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (getPiece(row, col) == ChessPieces.WHITE_KING) {
                    return checkMateBoard[row][col];
                }
            }
        }
        return false;
    }

    private boolean isBlackKingInCheckMate() {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (getPiece(row, col) == ChessPieces.BLACK_KING) {
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
        return isOnBoard(from) && !isEmpty(from) && isPlayerPiece(from) && pieceCanMove(from);
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
        createCheckMate();
    }
}
