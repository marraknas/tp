package seedu.findingbrudders.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.findingbrudders.model.udder.Udder;

/**
 * An UI component that displays information of a {@code Udder}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Udder udder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label role;
    @FXML
    private Label major;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Udder} and index to display.
     */
    public PersonCard(Udder udder, int displayedIndex) {
        super(FXML);
        this.udder = udder;
        id.setText(displayedIndex + ". ");
        name.setText(udder.getName().fullName);
        phone.setText(udder.getPhone().value);
        address.setText(udder.getAddress().value);
        email.setText(udder.getEmail().value);
        role.setText(udder.getRole().toString());
        major.setText(udder.getMajor().toString());
        udder.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
