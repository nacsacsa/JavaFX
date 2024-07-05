package chess.controller;

import chess.model.Chess;
import chess.model.ChessPiece;
import chess.model.Posititon;
import chess.model.utils.EnumImageStorage;
import chess.model.utils.ImageStorage;
import chess.model.utils.TwoPhaseMoveSelector;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ChessController {

    @FXML
    private GridPane board;
    private final Chess model = new Chess();
    private final TwoPhaseMoveSelector<Posititon> selector = new TwoPhaseMoveSelector<>(model);
    private final ImageStorage<ChessPiece> imageStorage = new EnumImageStorage<>(ChessPiece.class);
    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square,j, i);
            }
        }
        selector.phaseProperty().addListener(this::showSelectionPhaseChange);
    }

    private StackPane createSquare(int row, int col) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        if (row % 2 == 0) {
            if (col % 2 == 0) {
                square.getStyleClass().add("white");
            }
            else square.getStyleClass().add("black");
        } else if (row % 2 != 0) {
            if (col % 2 == 0) {
                square.getStyleClass().add("black");
            }
            else square.getStyleClass().add("white");
        }
        var imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.imageProperty().bind(
                new ObjectBinding<Image>() {
                    {
                        super.bind(model.getProperty(row, col));
                    }
                    @Override
                    protected Image computeValue() {
                        return imageStorage.get(model.getPiece(row, col));
                    }
                }
        );
        square.getChildren().add(imageView);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        selector.select(new Posititon(row, col));
        if (selector.isReadyToMove()) {
            selector.makeMove();
            if (model.isGameOver()) {
                gameOverAlertAndExit();
            }
        }
        if (model.isCheckMate(model.checkMateBoard)) {
            switch (model.getNextPlayer()) {
                case PLAYER_1 -> colorWhiteKing();
                case PLAYER_2 -> colorBlackKing();
            }
        }
        else {
            noCheckMateColor();
        }
    }

    private void noCheckMateColor() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                    var square = getSquare(new Posititon(i, j));
                    square.getStyleClass().remove("checkMate");
                }
            }
        }

    private void gameOverAlertAndExit() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game Over");
        alert.setContentText("Game Over");
        alert.showAndWait();
        Platform.exit();
    }

    private void showSelectionPhaseChange(ObservableValue<? extends TwoPhaseMoveSelector.Phase> value, TwoPhaseMoveSelector.Phase oldPhase, TwoPhaseMoveSelector.Phase newPhase) {
        switch (newPhase) {
            case SELECT_FROM -> {}
            case SELECT_TO -> showSelection(selector.getFrom());
            case READY_TO_MOVE -> hideSelection(selector.getFrom(), selector.getTo());
        }
    }

    private void showSelection(Posititon position) {
        var square = getSquare(position);
        square.getStyleClass().add("selected");
        showWhereIsPossibleToMove(position);
    }

    private void showWhereIsPossibleToMove(Posititon posititon) {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                if (model.isLegalMove(posititon, new Posititon(i, j))) {
                    var square = getSquare(new Posititon(i, j));
                    square.getStyleClass().add("possibleMoves");
                }
            }
        }
    }

    private void notShowWhereIsPossibleToMove(Posititon posititon) {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                if (model.isLegalMove(posititon, new Posititon(i, j))) {
                    var square = getSquare(new Posititon(i, j));
                    square.getStyleClass().remove("possibleMoves");
                }
            }
        }
    }

    private void hideSelection(Posititon from, Posititon to) {
        hideLastMoves();
        var square = getSquare(from);
        var squareTo = getSquare(to);
        square.getStyleClass().remove("selected");
        notShowWhereIsPossibleToMove(from);
        square.getStyleClass().add("lastMoveTo");
        squareTo.getStyleClass().add("lastMoveTo");
        if (model.isCheckMate(model.checkMateBoard))  {
            noCheckMateColor();
        }
    }

    private void colorBlackKing() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                if (model.getPiece(i, j) == ChessPiece.BLACK_KING) {
                    var square = getSquare(new Posititon(i, j));
                    square.getStyleClass().add("checkMate");
                }
            }
        }
    }

    private void colorWhiteKing() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                if (model.getPiece(i, j) == ChessPiece.WHITE_KING) {
                    var square = getSquare(new Posititon(i, j));
                    square.getStyleClass().add("checkMate");
                }
            }
        }
    }

    private void hideLastMoves() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = getSquare(new Posititon(i, j));
                square.getStyleClass().remove("lastMoveTo");
                }
            }
        }

    private StackPane getSquare(Posititon position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

}
