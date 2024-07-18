package chess.model;

import chess.model.utils.ChessTypeGameState;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class ChessState implements ChessTypeGameState<Position> {

    /**
     * The size of the board.
     */
    public static final int BOARD_SIZE = 8;
    private Player player;
    private final ReadOnlyObjectWrapper<ChessPiece>[][] board;
    private ChessPiece[][] tempBoard;

    /**
     * The checkmate board, which checks the enemy player pieces hit zones.
     */
    public boolean[][] checkMateBoard = new boolean[BOARD_SIZE][BOARD_SIZE];
    private boolean[][] tempCheckMateBoard = new boolean[BOARD_SIZE][BOARD_SIZE];

    /**
     * The constructor of the model.
     */
    public ChessState() {
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
                if (whiteEnemyTemp(new Position(row, col))) {
                    switch (tempBoard[row][col]) {
                        case WHITE_PAWN -> whitePawnHitZone(new Position(row, col), tempCheckMateBoard);
                        case WHITE_KNIGHT -> knightHitZone(new Position(row, col), tempCheckMateBoard);
                        case WHITE_ROOK -> rookHitZoneTemp(new Position(row, col), tempCheckMateBoard);
                        case WHITE_BISHOP -> bishopHitZoneTemp(new Position(row, col), tempCheckMateBoard);
                        case WHITE_KING -> kingHitZone(new Position(row, col), tempCheckMateBoard);
                        case WHITE_QUEEN -> queenHitZoneTemp(new Position(row, col), tempCheckMateBoard);
                    }
                }
            }
        }
    }

    private void fillWhiteCheckMateTable(boolean[][] checkMateBoard, ReadOnlyObjectWrapper<ChessPiece>[][] board) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (whiteEnemy(new Position(row, col))) {
                    switch (board[row][col].get()) {
                        case WHITE_PAWN -> whitePawnHitZone(new Position(row, col), checkMateBoard);
                        case WHITE_KNIGHT -> knightHitZone(new Position(row, col), checkMateBoard);
                        case WHITE_ROOK -> rookHitZone(new Position(row, col), checkMateBoard);
                        case WHITE_BISHOP -> bishopHitZone(new Position(row, col), checkMateBoard);
                        case WHITE_KING -> kingHitZone(new Position(row, col), checkMateBoard);
                        case WHITE_QUEEN -> queenHitZone(new Position(row, col), checkMateBoard);
                    }
                }
            }
        }
    }

    private void fillBlackCheckMateTable(boolean[][] tempCheckMateBoard, ChessPiece[][] tempBoard) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (blackEnemyTemp(new Position(row, col))) {
                    switch (tempBoard[row][col]) {
                        case BLACK_PAWN -> blackPawnHitZone(new Position(row, col), tempCheckMateBoard);
                        case BLACK_KNIGHT -> knightHitZone(new Position(row, col), tempCheckMateBoard);
                        case BLACK_ROOK -> rookHitZoneTemp(new Position(row, col), tempCheckMateBoard);
                        case BLACK_BISHOP -> bishopHitZoneTemp(new Position(row, col), tempCheckMateBoard);
                        case BLACK_KING -> kingHitZone(new Position(row, col), tempCheckMateBoard);
                        case BLACK_QUEEN -> queenHitZoneTemp(new Position(row, col), tempCheckMateBoard);
                    }
                }
            }
        }
    }

    private void fillBlackCheckMateTable(boolean[][] checkMateBoard, ReadOnlyObjectWrapper<ChessPiece>[][] board) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (blackEnemy(new Position(row, col))) {
                    switch (board[row][col].get()) {
                        case BLACK_PAWN -> blackPawnHitZone(new Position(row, col), checkMateBoard);
                        case BLACK_KNIGHT -> knightHitZone(new Position(row, col), checkMateBoard);
                        case BLACK_ROOK -> rookHitZone(new Position(row, col), checkMateBoard);
                        case BLACK_BISHOP -> bishopHitZone(new Position(row, col), checkMateBoard);
                        case BLACK_KING -> kingHitZone(new Position(row, col), checkMateBoard);
                        case BLACK_QUEEN -> queenHitZone(new Position(row, col), checkMateBoard);
                    }
                }
            }
        }
    }

    private void whitePawnHitZone(Position position, boolean[][] checkMateBoard) {
        if (isOnBoard(new Position(position.row() - 1, position.col() + 1))) {
            checkMateBoard[position.row() - 1][position.col() + 1] = true;
        }
        if (isOnBoard(new Position(position.row() - 1, position.col() - 1))) {
            checkMateBoard[position.row() - 1][position.col() - 1] = true;
        }
    }

    private void queenHitZone(Position position, boolean[][] checkMateBoard) {
        bishopHitZone(position, checkMateBoard);
        rookHitZone(position, checkMateBoard);
    }

    private void queenHitZoneTemp(Position position, boolean[][] checkMateBoard) {
        bishopHitZoneTemp(position, checkMateBoard);
        rookHitZoneTemp(position, checkMateBoard);
    }

    private void bishopHitZone(Position position, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() + j, position.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() + i][position.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() - i, position.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() - j, position.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() - i][position.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() + j, position.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() + i][position.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() - i, position.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() - j, position.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() - i][position.col() + i] = true;
            }
            legalMove = false;
        }
    }

    private void bishopHitZoneTemp(Position position, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row() + j, position.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() + i][position.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() - i, position.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row() - j, position.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() - i][position.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row() + j, position.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() + i][position.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() - i, position.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row() - j, position.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() - i][position.col() + i] = true;
            }
            legalMove = false;
        }
    }

    private void rookHitZone(Position position, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row(), position.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row(), position.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row()][position.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() + j, position.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() + i][position.col()] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row(), position.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row(), position.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row()][position.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() - i, position.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() - j, position.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() - i][position.col()] = true;
            }
            legalMove = false;
        }
    }

    private void rookHitZoneTemp(Position position, boolean[][] checkMateBoard) {
        boolean legalMove = false;

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row(), position.col() + i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row(), position.col() + j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row()][position.col() + i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row() + j, position.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() + i][position.col()] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row(), position.col() - i))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row(), position.col() - j) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row()][position.col() - i] = true;
            }
            legalMove = false;
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() - i, position.col()))) {
                if (i == 1) {
                    legalMove = true;
                }
                for (var j = 1; j < i; j++) {
                    if (!(getTempPiece(position.row() - j, position.col()) == ChessPiece.EMPTY)) {
                        legalMove = false;
                        break;
                    }
                    legalMove = true;
                }
            }
            if (legalMove) {
                checkMateBoard[position.row() - i][position.col()] = true;
            }
            legalMove = false;
        }
    }

    private void kingHitZone(Position position, boolean[][] checkMateBoard) {
        if (isOnBoard(new Position(position.row() + 1, position.col() + 1))) {
            checkMateBoard[position.row() + 1][position.col() + 1] = true;
        }
        if (isOnBoard(new Position(position.row() + 1, position.col() - 1))) {
            checkMateBoard[position.row() + 1][position.col() - 1] = true;
        }
        if (isOnBoard(new Position(position.row() + 1, position.col()))) {
            checkMateBoard[position.row() + 1][position.col()] = true;
        }
        if (isOnBoard(new Position(position.row() - 1, position.col()))) {
            checkMateBoard[position.row() - 1][position.col()] = true;
        }
        if (isOnBoard(new Position(position.row() - 1, position.col() - 1))) {
            checkMateBoard[position.row() - 1][position.col() - 1] = true;
        }
        if (isOnBoard(new Position(position.row() - 1, position.col() + 1))) {
            checkMateBoard[position.row() - 1][position.col() + 1] = true;
        }
        if (isOnBoard(new Position(position.row(), position.col() + 1))) {
            checkMateBoard[position.row()][position.col() + 1] = true;
        }
        if (isOnBoard(new Position(position.row(), position.col() - 1))) {
            checkMateBoard[position.row()][position.col() - 1] = true;
        }
    }

    private void blackPawnHitZone(Position position, boolean[][] checkMateBoard) {
            if (isOnBoard(new Position(position.row() + 1, position.col() + 1))) {
                checkMateBoard[position.row() + 1][position.col() + 1] = true;
            }
            if (isOnBoard(new Position(position.row() + 1, position.col() - 1))) {
                checkMateBoard[position.row() + 1][position.col() - 1] = true;
            }
    }

    private void knightHitZone(Position position, boolean[][] checkMateBoard) {
        if (isOnBoard(new Position(position.row() + 1, position.col() + 2))) {
            checkMateBoard[position.row() + 1][position.col() + 2] = true;
        }
        if (isOnBoard(new Position(position.row() + 1, position.col() - 2))) {
            checkMateBoard[position.row() + 1][position.col() - 2] = true;
        }
        if (isOnBoard(new Position(position.row() + 2, position.col() + 1))) {
            checkMateBoard[position.row() + 2][position.col() + 1] = true;
        }
        if (isOnBoard(new Position(position.row() + 2, position.col() - 1))) {
            checkMateBoard[position.row() + 2][position.col() - 1] = true;
        }
        if (isOnBoard(new Position(position.row() - 1, position.col() + 2))) {
            checkMateBoard[position.row() - 1][position.col() + 2] = true;
        }
        if (isOnBoard(new Position(position.row() - 1, position.col() - 2))) {
            checkMateBoard[position.row() - 1][position.col() - 2] = true;
        }
        if (isOnBoard(new Position(position.row() - 2, position.col() + 1))) {
            checkMateBoard[position.row() - 2][position.col() + 1] = true;
        }
        if (isOnBoard(new Position(position.row() - 2, position.col() - 1))) {
            checkMateBoard[position.row() - 2][position.col() - 1] = true;
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

    /**
     * {@return the piece from the given position as a property}
     * @param row the row of the position
     * @param col the column of the position
     */
    public ReadOnlyObjectProperty<ChessPiece> getProperty(int row, int col) {
        return board[row][col].getReadOnlyProperty();
    }

    /**
     * {@return the piece from the given position}
     * @param row the row of the position
     * @param col the column of the position
     */
    public ChessPiece getPiece(int row, int col) {
        if (isOnBoard(new Position(row, col))) {
            return board[row][col].get();
        }
        return ChessPiece.NONE;
    }

    private ChessPiece getTempPiece(int row, int col) {
        if (isOnBoard(new Position(row, col))) {
            return tempBoard[row][col];
        }
        return ChessPiece.NONE;
    }

    /**
     * {@return if the {@code player} king is in checkmate or not}
     *
     * @param checkMateTable the table which contains the enemy player's pieces hit radius
     */
    @Override
    public boolean isCheckMate(boolean[][] checkMateTable) {
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
                if (isPlayerPiece(new Position(i, j))) {
                    if (isLegalToMoveFrom(new Position(i, j))) {
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
    public boolean isLegalToMoveFrom(Position from) {
        return isOnBoard(from) && !isEmpty(from) && isPlayerPiece(from) && pieceCanMove(from) && isCheckMateEvasionMove(from) && isNotACheckMateStep(from) && isNotCausesCheckMate(from);
    }

    private boolean isKingCanMove(Position from) {
        boolean move1 = false;
        boolean move2 = false;
        boolean move3 = false;
        boolean move4 = false;
        boolean move5 = false;
        boolean move6 = false;
        boolean move7 = false;
        boolean move8 = false;

        if (isOnBoard(new Position(from.row() + 1, from.col() + 1)) && (isEmpty(new Position(from.row() + 1, from.col() + 1)) || isEnemyPiece(new Position(from.row() + 1, from.col() + 1)))) {
            move1 = !checkMateBoard[from.row() + 1][from.col() + 1];
        }
        if (isOnBoard(new Position(from.row() + 1, from.col() - 1)) && (isEmpty(new Position(from.row() + 1, from.col() - 1)) || isEnemyPiece(new Position(from.row() + 1, from.col() - 1)))) {
            move2 = !checkMateBoard[from.row() + 1][from.col() - 1];
        }
        if (isOnBoard(new Position(from.row() + 1, from.col())) && (isEmpty(new Position(from.row() + 1, from.col())) || isEnemyPiece(new Position(from.row() + 1, from.col())))) {
            move3 = !checkMateBoard[from.row() + 1][from.col()];
        }
        if (isOnBoard(new Position(from.row(), from.col() + 1)) && (isEmpty(new Position(from.row(), from.col() + 1)) || isEnemyPiece(new Position(from.row(), from.col() + 1)))) {
            move4 = !checkMateBoard[from.row()][from.col() + 1];
        }
        if (isOnBoard(new Position(from.row() - 1, from.col() - 1)) && (isEmpty(new Position(from.row() - 1, from.col() - 1)) || isEnemyPiece(new Position(from.row() - 1, from.col() - 1)))) {
            move5 = !checkMateBoard[from.row() - 1][from.col() - 1];
        }
        if (isOnBoard(new Position(from.row() - 1, from.col() + 1)) && (isEmpty(new Position(from.row() - 1, from.col() + 1)) || isEnemyPiece(new Position(from.row() - 1, from.col() + 1)))) {
            move6 = !checkMateBoard[from.row() - 1][from.col() + 1];
        }
        if (isOnBoard(new Position(from.row() - 1, from.col())) && (isEmpty(new Position(from.row() - 1, from.col())) || isEnemyPiece(new Position(from.row() - 1, from.col())))) {
            move7 = !checkMateBoard[from.row() - 1][from.col()];
        }
        if (isOnBoard(new Position(from.row(), from.col() - 1)) && (isEmpty(new Position(from.row(), from.col() - 1)) || isEnemyPiece(new Position(from.row(), from.col() - 1)))) {
            move8 = !checkMateBoard[from.row()][from.col() - 1];
        }
        return move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    private boolean isNotACheckMateStep(Position from) {
        if (!isCheckMateOver(from)) {
            return false;
        }
        return true;
    }

    private boolean isNotCausesCheckMate(Position position) {
        ChessPiece piece = board[position.row()][position.col()].get();
        switch (piece) {
            case WHITE_KING, BLACK_KING -> {
                return isKingCanMove(position);
            }
            case WHITE_QUEEN, BLACK_QUEEN -> {
                return isQueenNotMoveToCheckMate(position);
            }
            case WHITE_BISHOP, BLACK_BISHOP -> {
                return isBishopNotMoveMoveToCheckMate(position);
            }
            case WHITE_ROOK, BLACK_ROOK -> {
                return isRookNotMoveMoveToCheckMate(position);
            }
            case WHITE_KNIGHT, BLACK_KNIGHT -> {
                return isKnightNotMoveToCheckMate(position);
            }
            case WHITE_PAWN -> {
                return isWhitePawnNotMoveToCheckMate(position);
            }
            case BLACK_PAWN -> {
                return isBlackPawnNotMoveToCheckMate(position);
            }
        }
        return false;
    }

    private boolean isQueenNotMoveToCheckMate(Position position) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithQueen(position, new Position(row, col))) {
                    if (moveToEndCheckMate(position, new Position(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBlackPawnNotMoveToCheckMate(Position position) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithBlackPawn(position, new Position(row, col))) {
                    if (moveToEndCheckMate(position, new Position(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isWhitePawnNotMoveToCheckMate(Position position) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithWhitePawn(position, new Position(row, col))) {
                    if (moveToEndCheckMate(position, new Position(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isKnightNotMoveToCheckMate(Position position) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithKnight(position, new Position(row, col))) {
                    if (moveToEndCheckMate(position, new Position(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isRookNotMoveMoveToCheckMate(Position position) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithRook(position, new Position(row, col))) {
                    if (moveToEndCheckMate(position, new Position(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBishopNotMoveMoveToCheckMate(Position position) {
        for (var row = 0; row < BOARD_SIZE; row++) {
            for (var col = 0; col < BOARD_SIZE; col++) {
                if (isLegalMoveWithBishop(position, new Position(row, col))) {
                    if (moveToEndCheckMate(position, new Position(row, col)) && getPiece(row, col) != ChessPiece.WHITE_KING && getPiece(row, col) != ChessPiece.BLACK_KING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheckMateEvasionMove(Position from) {
        if (!isCheckMate(checkMateBoard)) {
            return true;
        }
        return isCheckMateOver(from);
    }

    private boolean isCheckMateOver(Position position) {
        ChessPiece piece = board[position.row()][position.col()].get();
        switch (piece) {
            case WHITE_KING, BLACK_KING -> {
                return isKingMoveEndCheckMate(position);
            }
            case WHITE_QUEEN, BLACK_QUEEN -> {
                return isQueenMoveEndCheckMate(position);
            }
            case WHITE_BISHOP, BLACK_BISHOP -> {
                return isBishopMoveEndCheckMate(position);
            }
            case WHITE_ROOK, BLACK_ROOK -> {
                return isRookMoveEndCheckMate(position);
            }
            case WHITE_KNIGHT, BLACK_KNIGHT -> {
                return isKnightMoveEndCheckMate(position);
            }
            case WHITE_PAWN -> {
                return isWhitePawnMoveEndCheckMate(position);
            }
            case BLACK_PAWN -> {
                return isBlackPawnMoveEndCheckMate(position);
            }
        }
        return false;
    }

    private boolean isBlackPawnMoveEndCheckMate(Position position) {
        boolean move1 = false;
        boolean move2 = false;
        boolean move3 = false;
        boolean move4 = false;
        if (position.row() == 1 && getPiece(position.row() + 1, position.col()) == ChessPiece.EMPTY && getPiece(position.row() + 2, position.col()) == ChessPiece.EMPTY) {
           move1 = moveToEndCheckMate(position, new Position(position.row() + 2, position.col()));
        }
        if  (getPiece(position.row() + 1, position.col()) == ChessPiece.EMPTY ) {
            move2 = moveToEndCheckMate(position, new Position(position.row() + 1, position.col()));
        }
        if (isEnemyPiece(new Position(position.row() + 1, position.col() + 1))) {
            move3 = moveToEndCheckMate(position, new Position(position.row() + 1, position.col() + 1));
        }
        if (isEnemyPiece(new Position(position.row() + 1, position.col() - 1))) {
            move4 = moveToEndCheckMate(position, new Position(position.row() + 1, position.col() - 1));
        }
        return move1 || move2 || move3 || move4;
    }

    private boolean isWhitePawnMoveEndCheckMate(Position position) {
        boolean move1 = false;
        boolean move2 = false;
        boolean move3 = false;
        boolean move4 = false;
        if (position.row() == 6 && getPiece(position.row() - 1, position.col()) == ChessPiece.EMPTY && getPiece(position.row() - 2, position.col()) == ChessPiece.EMPTY) {
            move1 = moveToEndCheckMate(position, new Position(position.row() - 2, position.col()));
        }
        if (getPiece(position.row() - 1, position.col()) == ChessPiece.EMPTY) {
            move2 = moveToEndCheckMate(position, new Position(position.row() - 1, position.col()));
        }
        if (isEnemyPiece(new Position(position.row() - 1, position.col() + 1))) {
            move3 = moveToEndCheckMate(position, new Position(position.row() - 1, position.col() + 1));
        }
        if (isEnemyPiece(new Position(position.row() - 1, position.col() - 1))) {
            move4 = moveToEndCheckMate(position, new Position(position.row() - 1, position.col() - 1));
        }
        return move1 || move2 || move3 || move4;
    }

    private boolean isBishopMoveEndCheckMate(Position position) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col() + i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row() + i, position.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() + j, position.col() + j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row() + i, position.col() + i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() - i, position.col() - i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row() - i, position.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() - j, position.col() - j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row() - i, position.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col() - i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row() + i, position.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() + j, position.col() - j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row() + i, position.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() -  i, position.col() + i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row() - i, position.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() - j, position.col() + j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row() - i, position.col() + i))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isRookMoveEndCheckMate(Position position) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row(), position.col() + i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row(), position.col() + i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row(), position.col() + j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row(), position.col() + i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() + i, position.col()))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row() + i, position.col()))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() + j, position.col()) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row() + i, position.col()))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row(), position.col() - i))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row(), position.col() - i))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row(), position.col() - j) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row(), position.col() - i))) {
                        return true;
                    }
                }
            }
        }

        for (var i = 1; i < BOARD_SIZE; i++) {
            if (isOnBoard(new Position(position.row() -  i, position.col()))) {
                if (i == 1) {
                    if (moveToEndCheckMate(position, new Position(position.row() - i, position.col()))) {
                        return true;
                    }
                }
                for (var j = 1; j < i; j++) {
                    if (!(getPiece(position.row() - j, position.col()) == ChessPiece.EMPTY)) {
                        break;
                    }
                    if (moveToEndCheckMate(position, new Position(position.row() - i, position.col()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isQueenMoveEndCheckMate(Position position) {
        return isBishopMoveEndCheckMate(position) || isRookMoveEndCheckMate(position);
    }

    private boolean isKingMoveEndCheckMate(Position position) {
        Position pos1 = new Position(position.row() + 1, position.col() + 1);
        Position pos2 = new Position(position.row() + 1, position.col() - 1);
        Position pos3 = new Position(position.row() + 1, position.col());
        Position pos4 = new Position(position.row(), position.col() + 1);
        Position pos5 = new Position(position.row(), position.col() - 1);
        Position pos6 = new Position(position.row() - 1, position.col() - 1);
        Position pos7 = new Position(position.row() - 1, position.col() + 1);
        Position pos8 = new Position(position.row() - 1, position.col());
        return moveToEndCheckMate(position, pos1) || moveToEndCheckMate(position, pos2) ||
                moveToEndCheckMate(position, pos3) || moveToEndCheckMate(position, pos4) ||
                moveToEndCheckMate(position, pos5) || moveToEndCheckMate(position, pos6) ||
                moveToEndCheckMate(position, pos7) || moveToEndCheckMate(position, pos8);
    }

    private boolean isKnightMoveEndCheckMate(Position position) {
        Position pos1 = new Position(position.row() + 1, position.col() + 2);
        Position pos2 = new Position(position.row() + 1, position.col() - 2);
        Position pos3 = new Position(position.row() + 2, position.col() + 1);
        Position pos4 = new Position(position.row() + 2, position.col() - 1);
        Position pos5 = new Position(position.row() - 1, position.col() + 2);
        Position pos6 = new Position(position.row() - 1, position.col() - 2);
        Position pos7 = new Position(position.row() - 2, position.col() + 1);
        Position pos8 = new Position(position.row() - 2, position.col() - 1);
        return moveToEndCheckMate(position, pos1) || moveToEndCheckMate(position, pos2) ||
                moveToEndCheckMate(position, pos3) || moveToEndCheckMate(position, pos4) ||
                moveToEndCheckMate(position, pos5) || moveToEndCheckMate(position, pos6) ||
                moveToEndCheckMate(position, pos7) || moveToEndCheckMate(position, pos8);
    }

    private boolean moveToEndCheckMate(Position from, Position to) {
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

    private boolean isOnBoard(Position position) {
        return position.row() >= 0 && position.row() < BOARD_SIZE && position.col() >= 0 && position.col() < BOARD_SIZE;
    }

    private boolean isEmpty(Position position) {
        if (isOnBoard(position)) {
            return getPiece(position.row(), position.col()) == ChessPiece.EMPTY;
        }
        return false;
    }

    private boolean isPlayerPiece(Position position) {
        if (Player.PLAYER_1 == player) {
            return getPiece(position.row(), position.col()) == ChessPiece.WHITE_PAWN ||
                    getPiece(position.row(), position.col()) == ChessPiece.WHITE_BISHOP ||
                    getPiece(position.row(), position.col()) == ChessPiece.WHITE_KNIGHT ||
                    getPiece(position.row(), position.col()) == ChessPiece.WHITE_ROOK ||
                    getPiece(position.row(), position.col()) == ChessPiece.WHITE_KING ||
                    getPiece(position.row(), position.col()) == ChessPiece.WHITE_QUEEN;
        }
        else
            return getPiece(position.row(), position.col()) == ChessPiece.BLACK_PAWN ||
                    getPiece(position.row(), position.col()) == ChessPiece.BLACK_BISHOP ||
                    getPiece(position.row(), position.col()) == ChessPiece.BLACK_KNIGHT ||
                    getPiece(position.row(), position.col()) == ChessPiece.BLACK_ROOK ||
                    getPiece(position.row(), position.col()) == ChessPiece.BLACK_KING ||
                    getPiece(position.row(), position.col()) == ChessPiece.BLACK_QUEEN;
    }

    private boolean pieceCanMove(Position position) {
        ChessPiece piece = getPiece(position.row(), position.col());
        switch (piece) {
            case BLACK_KNIGHT, WHITE_KNIGHT -> {
                return knightCanMove(position);
            }
            case BLACK_KING, WHITE_KING -> {
                return kingCanMove(position);
            }
            case BLACK_ROOK, WHITE_ROOK -> {
                return rookCanMove(position);
            }
            case BLACK_BISHOP, WHITE_BISHOP -> {
                return bishopCanMove(position);
            }
            case BLACK_QUEEN, WHITE_QUEEN -> {
                return queenCanMove(position);
            }
            case BLACK_PAWN, WHITE_PAWN -> {
                return pawnCanMove(position);
            }
            default -> {
                return false;
            }
        }
    }

    private boolean pawnCanMove(Position position) {
        switch (getPiece(position.row(), position.col())) {
            case WHITE_PAWN -> {
                return whitePieceCanMove(position);
            }
            case BLACK_PAWN -> {
                return blackPieceCanMove(position);
            }
        }
        return false;
    }

    private boolean blackPieceCanMove(Position position) {
        return isEmpty(new Position(position.row() + 1, position.col()))
                || (isEmpty(new Position(position.row() + 2, position.col())) && isEmpty(new Position(position.row() + 1, position.col())) && position.row() == 1)
                || isEnemyPiece(new Position(position.row() + 1, position.col() - 1))
                || isEnemyPiece(new Position(position.row() + 1, position.col() + 1));
    }

    private boolean isEnemyPiece(Position position) {
        switch (player) {
            case PLAYER_1 -> {
                return blackEnemy(position);
            }
            case PLAYER_2 -> {
                return whiteEnemy(position);
            }
        }
        throw new ArithmeticException("No player found");
    }

    private boolean whiteEnemy(Position position) {
        return getPiece(position.row(), position.col()) == ChessPiece.WHITE_PAWN
                || getPiece(position.row(), position.col()) == ChessPiece.WHITE_ROOK
                || getPiece(position.row(), position.col()) == ChessPiece.WHITE_KNIGHT
                || getPiece(position.row(), position.col()) == ChessPiece.WHITE_BISHOP
                || getPiece(position.row(), position.col()) == ChessPiece.WHITE_QUEEN
                || getPiece(position.row(), position.col()) == ChessPiece.WHITE_KING;
    }

    private boolean whiteEnemyTemp(Position position) {
        return getTempPiece(position.row(), position.col()) == ChessPiece.WHITE_PAWN
                || getTempPiece(position.row(), position.col()) == ChessPiece.WHITE_ROOK
                || getTempPiece(position.row(), position.col()) == ChessPiece.WHITE_KNIGHT
                || getTempPiece(position.row(), position.col()) == ChessPiece.WHITE_BISHOP
                || getTempPiece(position.row(), position.col()) == ChessPiece.WHITE_QUEEN
                || getTempPiece(position.row(), position.col()) == ChessPiece.WHITE_KING;
    }

    private boolean blackEnemy(Position position) {
        return getPiece(position.row(), position.col()) == ChessPiece.BLACK_PAWN
                || getPiece(position.row(), position.col()) == ChessPiece.BLACK_ROOK
                || getPiece(position.row(), position.col()) == ChessPiece.BLACK_KNIGHT
                || getPiece(position.row(), position.col()) == ChessPiece.BLACK_BISHOP
                || getPiece(position.row(), position.col()) == ChessPiece.BLACK_QUEEN
                || getPiece(position.row(), position.col()) == ChessPiece.BLACK_KING;
    }

    private boolean blackEnemyTemp(Position position) {
        return getTempPiece(position.row(), position.col()) == ChessPiece.BLACK_PAWN
                || getTempPiece(position.row(), position.col()) == ChessPiece.BLACK_ROOK
                || getTempPiece(position.row(), position.col()) == ChessPiece.BLACK_KNIGHT
                || getTempPiece(position.row(), position.col()) == ChessPiece.BLACK_BISHOP
                || getTempPiece(position.row(), position.col()) == ChessPiece.BLACK_QUEEN
                || getTempPiece(position.row(), position.col()) == ChessPiece.BLACK_KING;
    }

    private boolean whitePieceCanMove(Position position) {
        return isEmpty(new Position(position.row() - 1, position.col()))
                || (isEmpty(new Position(position.row() - 2, position.col())) && isEmpty(new Position(position.row() - 1, position.col())) && position.row() == 6)
                || isEnemyPiece(new Position(position.row() - 1, position.col() - 1))
                || isEnemyPiece(new Position(position.row() - 1, position.col() + 1));
    }

    private boolean queenCanMove(Position position) {
        return kingCanMove(position);
    }

    private boolean bishopCanMove(Position position) {
        return isEmpty(new Position(position.row() + 1, position.col() + 1))
                || isEmpty(new Position(position.row() - 1, position.col() - 1))
                || isEmpty(new Position(position.row() - 1, position.col() + 1))
                || isEmpty(new Position(position.row() + 1, position.col() - 1))

                || isEnemyPiece(new Position(position.row() + 1, position.col() + 1))
                || isEnemyPiece(new Position(position.row() - 1, position.col() - 1))
                || isEnemyPiece(new Position(position.row() - 1, position.col() + 1))
                || isEnemyPiece(new Position(position.row() + 1, position.col() - 1));
    }

    private boolean rookCanMove(Position position) {
        return isEmpty(new Position(position.row(), position.col()))
                || isEmpty(new Position(position.row() + 1, position.col()))
                || isEmpty(new Position(position.row() - 1, position.col()))
                || isEmpty(new Position(position.row(), position.col() + 1))
                || isEmpty(new Position(position.row(), position.col() - 1))

                || isEnemyPiece(new Position(position.row() + 1, position.col()))
                || isEnemyPiece(new Position(position.row() - 1, position.col()))
                || isEnemyPiece(new Position(position.row(), position.col() + 1))
                || isEnemyPiece(new Position(position.row(), position.col() - 1));
    }

    private boolean kingCanMove(Position position) {
        return isEmpty(new Position(position.row() + 1, position.col() + 1))
                || isEmpty(new Position(position.row() + 1, position.col() - 1))
                || isEmpty(new Position(position.row() + 1, position.col()))
                || isEmpty(new Position(position.row() - 1, position.col()))
                || isEmpty(new Position(position.row() - 1, position.col() - 1))
                || isEmpty(new Position(position.row() - 1, position.col() + 1))
                || isEmpty(new Position(position.row(), position.col() + 1))
                || isEmpty(new Position(position.row(), position.col() - 1))

                || isEnemyPiece(new Position(position.row() + 1, position.col() + 1))
                || isEnemyPiece(new Position(position.row() + 1, position.col() - 1))
                || isEnemyPiece(new Position(position.row() + 1, position.col()))
                || isEnemyPiece(new Position(position.row() - 1, position.col()))
                || isEnemyPiece(new Position(position.row() - 1, position.col() - 1))
                || isEnemyPiece(new Position(position.row() - 1, position.col() + 1))
                || isEnemyPiece(new Position(position.row(), position.col() + 1))
                || isEnemyPiece(new Position(position.row(), position.col() - 1));
    }

    private boolean knightCanMove(Position position) {
        return isEmpty(new Position(position.row() - 2, position.col() + 1))
                || isEmpty(new Position(position.row() -2 , position.col() - 1))
                || isEmpty(new Position(position.row() - 1, position.col() + 2))
                || isEmpty(new Position(position.row() - 1, position.col() - 2))
                || isEmpty(new Position(position.row() + 2, position.col() + 1))
                || isEmpty(new Position(position.row() + 2, position.col() - 1))
                || isEmpty(new Position(position.row() + 1, position.col() + 2))
                || isEmpty(new Position(position.row() + 1, position.col() - 2))

                || isEnemyPiece(new Position(position.row() - 2, position.col() + 1))
                || isEnemyPiece(new Position(position.row() -2 , position.col() - 1))
                || isEnemyPiece(new Position(position.row() - 1, position.col() + 2))
                || isEnemyPiece(new Position(position.row() - 1, position.col() - 2))
                || isEnemyPiece(new Position(position.row() + 2, position.col() + 1))
                || isEnemyPiece(new Position(position.row() + 2, position.col() - 1))
                || isEnemyPiece(new Position(position.row() + 1, position.col() + 2))
                || isEnemyPiece(new Position(position.row() + 1, position.col() - 2));
    }

    /**
     * {@return whether the move provided can be applied to the state}
     *
     * @param from represents where to move from
     * @param to   represents where to move to
     */
    @Override
    public boolean isLegalMove(Position from, Position to) {
        return isLegalToMoveFrom(from) && isOnBoard(to) && (isEmpty(to) || isEnemyPiece(to)) && isLegalToMoveWithPiece(from, to) && isLegalToMoveIfCheckMate(from, to);
    }

    private boolean isLegalToMoveIfCheckMate(Position from, Position to) {
        return moveToEndCheckMate(from, to);
    }

    private boolean isLegalToMoveWithPiece(Position from, Position to) {
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

    private boolean isLegalMoveWithQueen(Position from, Position to) {
        return isLegalMoveWithBishop(from, to) || isLegalMoveWithRook(from, to);
    }

    private boolean isLegalMoveWithBishop(Position from, Position to) {
        return isLegalToMoveWithBishopRightUp(from, to) || isLegalToMoveWithBishopRightDown(from, to)
                || isLegalToMoveWithBishopLeftUp(from, to) || isLegalToMoveWithBishopLeftDown(from, to);
    }

    private boolean isLegalToMoveWithBishopLeftUp(Position from, Position to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() + i && from.col() == to.col() + i) {
                for (var j = 1; j < (from.col() - to.col()); j++) {
                    if (!isEmpty(new Position(from.row() - j, from.col() - j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalToMoveWithBishopLeftDown(Position from, Position to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() - i && from.col() == to.col() + i) {
                for (var j = 1; j < (to.row()- from.row()); j++) {
                    if (!isEmpty(new Position(from.row() + j, from.col() - j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalToMoveWithBishopRightDown(Position from, Position to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() - i && from.col() == to.col() - i) {
                for (var j = 1; j < (to.row() - from.row()); j++) {
                    if (!isEmpty(new Position(from.row() + j, from.col() + j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalToMoveWithBishopRightUp(Position from, Position to) {
        for (var i = 1; i < BOARD_SIZE; i++) {
            if (from.row() == to.row() + i && from.col() == to.col() - i) {
                for (var j = 1; j < (to.col() - from.col()); j++) {
                    if (!isEmpty(new Position(from.row() - j, from.col() + j))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean isLegalMoveWithRook(Position from, Position to) {
        return isLegalToMoveWithRookRight(from, to) || isLegalToMoveWithRookLeft(from, to)
        || isLegalToMoveWithRookUp(from, to) || isLegalToMoveWithRookDown(from, to);
    }

    private boolean isLegalToMoveWithRookDown(Position from, Position to) {
        if (from.col() != to.col()) {
            return false;
        }
        if (from.row() > to.row()) {
            return false;
        }
        for (var i = from.row() + 1; i < to.row(); i++) {
            if (!isEmpty(new Position(i, from.col()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalToMoveWithRookUp(Position from, Position to) {
        if (from.col() != to.col()) {
            return false;
        }
        if (from.row() < to.row()) {
            return false;
        }
        for (var i = from.row() - 1; i > to.row(); i--) {
            if (!isEmpty(new Position(i, from.col()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalToMoveWithRookLeft(Position from, Position to) {
        if (from.row() != to.row()) {
            return false;
        }
        if (from.col() < to.col()) {
            return false;
        }
        for (var i = from.col() - 1; i > to.col(); i--) {
            if (!isEmpty(new Position(from.row(), i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalToMoveWithRookRight(Position from, Position to) {
        if (from.row() != to.row()) {
            return false;
        }
        if (from.col() > to.col()) {
            return false;
        }
        for (var i = from.col() + 1; i < to.col(); i++) {
            if (!isEmpty(new Position(from.row(), i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegalMoveWithKnight(Position from, Position to) {
        return (from.row() == to.row() + 2 && from.col() == to.col() + 1)
                || (from.row() == to.row() + 2 && from.col() == to.col() - 1)
                || (from.row() == to.row() - 2 && from.col() == to.col() - 1)
                || (from.row() == to.row() - 2 && from.col() == to.col() + 1)
                || (from.row() == to.row() + 1 && from.col() == to.col() + 2)
                || (from.row() == to.row() + 1 && from.col() == to.col() - 2)
                || (from.row() == to.row() - 1 && from.col() == to.col() + 2)
                || (from.row() == to.row() - 1 && from.col() == to.col() - 2);
    }

    private boolean isLegalMoveWithKing(Position from, Position to) {
        return ((from.row() == to.row() - 1 && from.col() == to.col() - 1))
                || ((from.row() == to.row() - 1 && from.col() == to.col() + 1))
                || ((from.row() == to.row() - 1 && from.col() == to.col()))
                || ((from.row() == to.row() + 1 && from.col() == to.col()))
                || ((from.row() == to.row() && from.col() == to.col() - 1))
                || ((from.row() == to.row() && from.col() == to.col() + 1))
                || ((from.row() == to.row() + 1 && from.col() == to.col() + 1))
                || ((from.row() == to.row() + 1 && from.col() == to.col() - 1));
    }

    private boolean isLegalMoveWithBlackPawn(Position from, Position to) {
        return (from.row() == to.row() - 1 && from.col() == to.col() && isEmpty(to))
        || (from.row() == to.row() - 2 && from.col() == to.col() && from.row() == 1 && isEmpty(new Position(to.row() - 1, to.col())) && isEmpty(to))
        || (from.row() == to.row() - 1 && from.col() == to.col() - 1 && isEnemyPiece(to))
        || (from.row() == to.row() - 1 && from.col() == to.col() + 1 && isEnemyPiece(to));

    }

    private boolean isLegalMoveWithWhitePawn(Position from, Position to) {
        return (from.row() == to.row() + 1 && from.col() == to.col() && isEmpty(to))
                || (from.row() == to.row() + 2 && from.col() == to.col() && from.row() == 6 && isEmpty(new Position(to.row() + 1, to.col())) && isEmpty(to))
                || (from.row() == to.row() + 1 && from.col() == to.col() - 1 && isEnemyPiece(to))
                || (from.row() == to.row() + 1 && from.col() == to.col() + 1 && isEnemyPiece(to));
    }

    /**
     * Applies the move provided to the state. This method should be called if
     * and only if {@link #isLegalMove(Position, Position)} returns {@code true}.
     *
     * @param from represents where to move from
     * @param to   represents where to move to
     */
    @Override
    public void makeMove(Position from, Position to) {
        if  (getPiece(from.row(), from.col()) == ChessPiece.WHITE_PAWN && to.row() == 0) {
            board[to.row()][to.col()].set(ChessPiece.WHITE_QUEEN);
        }
        else if (getPiece(from.row(), from.col()) == ChessPiece.BLACK_PAWN && to.row() == 7) {
            board[to.row()][to.col()].set(ChessPiece.BLACK_QUEEN);
        }
        else {
            board[to.row()][to.col()].set(getPiece(from.row(), from.col()));
        }
        board[from.row()][from.col()].set(ChessPiece.EMPTY);
        player = player.opponent();
        createCheckMate(checkMateBoard, board, player);
    }
}
