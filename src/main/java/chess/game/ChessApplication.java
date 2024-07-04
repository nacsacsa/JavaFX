package chess.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApplication extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chess.fxml"));
        stage.setTitle("Chess");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
