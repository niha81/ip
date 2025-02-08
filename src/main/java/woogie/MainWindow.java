package woogie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Woogie woogie;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/samhani.jpg"));
    private Image woogieImage = new Image(this.getClass().getResourceAsStream("/images/woonagi.jpg"));

    /**
     * Initializes the main window components.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Display greeting message when GUI starts
        String greeting = "* Greetings! I'm Woogie *･ﾟ✧\n" + " How can I help you?";
        dialogContainer.getChildren().add(
                DialogBox.getWoogieDialog(greeting, woogieImage)
        );
    }


    /** Injects the Duke instance */
    public void setWoogie(Woogie w) {
        woogie = w;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = woogie.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getWoogieDialog(response, woogieImage)
        );
        userInput.clear();
    }
}
