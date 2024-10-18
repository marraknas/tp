package finding.brudders.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import finding.brudders.commons.core.GuiSettings;
import finding.brudders.model.udder.Meeting;
import finding.brudders.model.udder.Person;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a udder with the same identity as {@code udder} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given udder.
     * The udder must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given udder.
     * {@code udder} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given udder {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The udder identity of {@code editedPerson} must not be the same as another existing udder in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered udder list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered udder list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds the given meeting with a udder.
     */
    void addMeeting(Person target, Meeting meeting);

    String listMeetings();
}
