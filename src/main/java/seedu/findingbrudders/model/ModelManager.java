package seedu.findingbrudders.model;

import static java.util.Objects.requireNonNull;
import static seedu.findingbrudders.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.findingbrudders.commons.core.GuiSettings;
import seedu.findingbrudders.commons.core.LogsCenter;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;


/**
 * Represents the in-memory model of the findingbrudders book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Udder> filteredUdders;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with findingbrudders book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredUdders = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Udder udder) {
        requireNonNull(udder);
        return addressBook.hasPerson(udder);
    }

    @Override
    public void deletePerson(Udder target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Udder udder) {
        addressBook.addPerson(udder);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Udder target, Udder editedUdder) {
        requireAllNonNull(target, editedUdder);

        addressBook.setPerson(target, editedUdder);
    }

    @Override
    public void addMeeting(Udder target, Meeting meeting) {
        // Change in main meeting list first to check for any time clash exception in all meetings
        addressBook.addMeeting(meeting);
        target.getMeetings().addMeeting(meeting);
    }

    @Override
    public void deleteMeeting(Udder target, Meeting meeting) {
        // Delete meeting for anUdder
        target.getMeetings().deleteMeeting(meeting);
        // Delete meeting for user
        addressBook.deleteMeeting(meeting);
    }

    @Override
    public Meeting getMeeting(int index) {
        return addressBook.getMeeting(index);
    }

    @Override
    public boolean hasMeeting(Meeting meeting) {
        requireNonNull(meeting);
        return addressBook.hasMeeting(meeting);
    }

    @Override
    public void setMeeting(Udder udder, Meeting target, Meeting editedMeeting) {
        // Change in main meeting list first to check for any time clash exception in all meetings
        requireAllNonNull(udder, target, editedMeeting);
        addressBook.setMeeting(target, editedMeeting);
        udder.getMeetings().setMeeting(target, editedMeeting);
    }

    @Override
    public String listMeetings() {
        return addressBook.listMeetings();
    }

    @Override
    public int getMeetingSize() {
        return addressBook.getMeetingSize();
    }

    //=========== Filtered Udder List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Udder} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Udder> getFilteredPersonList() {
        return filteredUdders;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Udder> predicate) {
        requireNonNull(predicate);
        filteredUdders.setPredicate(predicate);
    }



    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredUdders.equals(otherModelManager.filteredUdders);
    }

}
