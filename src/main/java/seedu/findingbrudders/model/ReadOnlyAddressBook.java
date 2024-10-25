package seedu.findingbrudders.model;

import javafx.collections.ObservableList;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Unmodifiable view of an findingbrudders book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Udder> getPersonList();

    ObservableList<Meeting> getMeetingList();
}
