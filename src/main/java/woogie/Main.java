package woogie;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Woogie woogie = new Woogie();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            URL cssResource = getClass().getResource("/view/style.css");
            if (cssResource == null) {
                System.out.println("Error: style.css not found!");
            } else {
                scene.getStylesheets().add(cssResource.toExternalForm());
            }
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setWoogie(woogie);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
