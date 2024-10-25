package seedu.findingbrudders.ui;

import seedu.findingbrudders.model.udder.Udder;

/**
 * A listener for selection events in the application.
 *
 * The listener receives updates when there is any change made to the components that have a {@code SelectionListener}.
 * In this context, the SelectionListener class is implemented to handle the event where a udder is selected from the
 * {@code PersonListPanel}.
 */
public interface SelectionListener {
    /**
     * Called when a udder is selected by the user.
     * This method provides a mechanism for handling the event that occurs
     * when a user selects a udder from {@code PersonListPanel}.
     *
     * @param udder The {@link Udder} that was selected. This object contains
     *               the data of the selected udder.
     * @param index  The index at which the udder was selected in the list or collection.
     *               This can be used for reference or to manage ordered collections.
     */
    void onPersonSelected(Udder udder, int index);
}
