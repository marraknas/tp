package seedu.findingbrudders.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.findingbrudders.commons.core.LogsCenter;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private SelectionListener listener;

    @FXML
    private ListView<Udder> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Udder> udderList) {
        super(FXML);
        personListView.setItems(udderList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setupSelectionModel();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Udder} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Udder> {
        @Override
        protected void updateItem(Udder udder, boolean empty) {
            super.updateItem(udder, empty);

            if (empty || udder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(udder, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Attaches a {@code SelectionListener} to the entire list's members.
     * @param listener a {@code SelectionListener} object/instance.
     * @see PersonListPanel#setupSelectionModel() for details of what this listener does.
     */
    public void setSelectionListener(SelectionListener listener) {
        this.listener = listener;
    }

    /**
     * Implements the logic that the {@code PersonListPanel} members' listener will have.
     * The logic here is to call the DetailPanel's {@code onPersonSelected} method to display the selected contact's
     * details on the DetailPanel.
     */
    private void setupSelectionModel() {
        personListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && listener != null) {
                listener.onPersonSelected(newSelection, personListView.getSelectionModel().getSelectedIndex());
            }
        });
    }

}
