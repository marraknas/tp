package seedu.findingbrudders.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.findingbrudders.commons.core.GuiSettings;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Udder> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' findingbrudders book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' findingbrudders book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces findingbrudders book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a udder with the same identity as {@code udder} exists in the findingbrudders book.
     */
    boolean hasPerson(Udder udder);

    /**
     * Deletes the given udder.
     * The udder must exist in the findingbrudders book.
     */
    void deletePerson(Udder target);

    /**
     * Adds the given udder.
     * {@code udder} must not already exist in the findingbrudders book.
     */
    void addPerson(Udder udder);

    /**
     * Replaces the given udder {@code target} with {@code editedUdder}.
     * {@code target} must exist in the findingbrudders book.
     * The udder identity of {@code editedUdder} must not be the same as another existing udder in the
     * findingbrudders book.
     */
    void setPerson(Udder target, Udder editedUdder);

    /** Returns an unmodifiable view of the filtered udder list */
    ObservableList<Udder> getFilteredPersonList();

    /**
     * Updates the filter of the filtered udder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Udder> predicate);

    /**
     * Adds the given meeting with a udder.
     */
    void addMeeting(Udder target, Meeting meeting) throws CommandException;

    /**
     * Deletes a given meeting with a udder.
     */
    void deleteMeeting(Udder target, Meeting meeting);

    /**
     * Gets a Meeting object given the index.
     *
     * @return Meeting object.
     */
    Meeting getMeeting(int index);

    /**
     * Returns true if a meeting with the same identity as {@code meeting} exists in the findingbrudders book.
     */
    boolean hasMeeting(Meeting meeting);

    /**
     * Replaces the given meeting {@code target} with {@code editedMeeting}.
     * {@code target} must exist in the findingbrudders book.
     * The meeting identity of {@code editedMeeting} must not be the same as another existing meeting in
     * findingbrudders book.
     */
    void setMeeting(Udder udder, Meeting target, Meeting editedMeeting);

    int getMeetingSize();

    String listMeetings();
}
