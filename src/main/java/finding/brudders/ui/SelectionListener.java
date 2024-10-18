package finding.brudders.ui;

import finding.brudders.model.udder.Person;

/**
 * A listener for selection events in the application.
 * <p>
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
     * @param person The {@link Person} that was selected. This object contains
     *               the data of the selected udder.
     * @param index  The index at which the udder was selected in the list or collection.
     *               This can be used for reference or to manage ordered collections.
     */
    void onPersonSelected(Person person, int index);
}
