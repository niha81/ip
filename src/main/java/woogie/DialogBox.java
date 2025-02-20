package woogie;
import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // Reduce profile picture size
        displayPicture.setFitWidth(75);
        displayPicture.setFitHeight(75);

        displayPicture.getStyleClass().add("profile-pic");

        displayPicture.setClip(new Circle(displayPicture.getFitWidth() / 2,
                displayPicture.getFitHeight() / 2,
                displayPicture.getFitWidth() / 2));

        // Adjust padding and spacing for better alignment
        this.setSpacing(10);
        this.setPadding(new Insets(5, 10, 5, 10));

        // Ensure text wraps properly
        dialog.setWrapText(true);
        dialog.setMaxWidth(250); // Set max width before wrapping

        dialog.getStyleClass().add("woogie-dialog");
        this.getStyleClass().add("dialog-container");
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setAlignment(Pos.CENTER_RIGHT);
        return db;
    }

    public static DialogBox getWoogieDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.setAlignment(Pos.CENTER_LEFT);
        return db;
    }
}
