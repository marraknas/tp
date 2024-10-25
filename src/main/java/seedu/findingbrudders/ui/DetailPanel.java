package seedu.findingbrudders.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.findingbrudders.model.udder.Major;
import seedu.findingbrudders.model.udder.Udder;

/**
 * A Detail Panel that displays individual udder details in the UI.
 * This class is responsible for handling the display of personal information such as name, email, phone number, etc.
 *
 * <p>The FXML file 'DetailPanel.fxml' is used to define the layout of the panel, including labels for displaying the
 * udder's name and email.
 *
 * <p>Usage example:
 * <pre>
 *     DetailPanel detailPanel = new DetailPanel();
 *     detailPanel.setPerson(new Udder("John Doe", "john.doe@example.com"));
 * </pre>
 */
public class DetailPanel extends UiPart<Region> implements SelectionListener {
    /**
     * The FXML file that represents the layout of the DetailPanel.
     */
    private static final String FXML = "DetailPanel.fxml";

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
    private Label tagStart;
    @FXML
    private FlowPane tagDetails;
    @FXML
    private Label meetings;

    private Udder udder;
    private int displayedIndex;

    /**
     * Constructs a new DetailPanel.
     * This constructor loads the layout from the specified FXML file and initializes
     * the UI components configured in the FXML.
     */
    public DetailPanel() {
        super(FXML);
    }

    /**
     * Sets the udder details on the DetailPanel.
     * This method updates the text of the labels with the provided udder's details.
     *
     */
    public void updateDetails() {
        name.setText(udder.getName().fullName);
        id.setText("ID\t\t: " + displayedIndex);
        phone.setText("Phone\t: " + udder.getPhone().value);
        address.setText("Address\t: " + udder.getAddress().value);
        email.setText("Email\t: " + udder.getEmail().value);
        role.setText("Role\t\t: " + udder.getRole());
        major.setText("Major\t: " + getMajorFullName(udder.getMajor()));
        tagStart.setText("Tags\t\t: ");
        tagDetails.getChildren().clear(); // necessary to clear existing tags, cus flowpane keeps memory
        udder.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tagDetails.getChildren().add(new Label(tag.tagName)));

        meetings.setText("Meetings\t:\n" + (udder.getMeetings().toDetailPanelString()));
    }


    /**
     * Event that triggers when a (new) udder is selected by user.
     * @param udder that user selected from the {@code PersonListPanel}
     * @param index index of the udder selected to be displayed in the DetailPanel
     */
    @Override
    public void onPersonSelected(Udder udder, int index) {
        setPerson(udder, index);
    }

    /**
     * Sets the udder to be displayed in the {@code DetailPanel}
     * @param udder that user selected from the {@code PersonListPanel}
     * @param index index of the udder selected to be displayed in the DetailPanel
     */
    public void setPerson(Udder udder, int index) {
        this.udder = udder;
        this.displayedIndex = index + 1;
        updateDetails();
    }

    /**
     * Returns the full name of a udder's major e.g. cs = Computer Science.
     *
     * @param major the major object to be translated into its full name
     * @return full name of major String
     */
    private String getMajorFullName(Major major) {
        switch (major.toString()) {
        case "cs":
            return "Computer Science";
        case "bza":
            return "Business Analytics";
        case "isys":
            return "Information System";
        case "isec":
            return "Information Security";
        case "ceg":
            return "Computer Engineering";
        default:
            return "Others"; // Default case if the major code doesn't match known ones
        }
    }
}
