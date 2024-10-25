package seedu.findingbrudders.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.findingbrudders.commons.util.ToStringBuilder;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Meetings;
import seedu.findingbrudders.model.udder.Udder;
import seedu.findingbrudders.model.udder.UniquePersonList;

/**
 * Wraps all data at the findingbrudders-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final Meetings meetings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        meetings = new Meetings();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the udder list with {@code udders}.
     * {@code udders} must not contain duplicate udders.
     */
    public void setPersons(List<Udder> udders) {
        this.persons.setPersons(udders);
    }

    /**
     * Replaces the contents of the udder list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setMeetings(List<Meeting> meetings) {
        this.meetings.setInternalList(meetings);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setMeetings(newData.getMeetingList());
    }

    //// udder-level operations

    /**
     * Returns true if a udder with the same identity as {@code udder} exists in the findingbrudders book.
     */
    public boolean hasPerson(Udder udder) {
        requireNonNull(udder);
        return persons.contains(udder);
    }

    /**
     * Adds a udder to the findingbrudders book.
     * The udder must not already exist in the findingbrudders book.
     */
    public void addPerson(Udder p) {
        persons.add(p);
    }

    /**
     * Replaces the given udder {@code target} in the list with {@code editedUdder}.
     * {@code target} must exist in the findingbrudders book.
     * The udder identity of {@code editedUdder} must not be the same as another existing udder in the
     * findingbrudders book.
     */
    public void setPerson(Udder target, Udder editedUdder) {
        requireNonNull(editedUdder);

        persons.setPerson(target, editedUdder);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the findingbrudders book.
     */
    public void removePerson(Udder key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("udders", persons)
                .toString();
    }

    @Override
    public ObservableList<Udder> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    public void addMeeting(Meeting m) {
        meetings.addMeeting(m);
    }

    public void deleteMeeting(Meeting m) {
        meetings.deleteMeeting(m);
    }

    public Meeting getMeeting(int index) {
        return meetings.getMeeting(index);
    }

    /**
     * Returns true if addressbook already contains (@code meeting).
     */
    public boolean hasMeeting(Meeting m) {
        requireNonNull(m);
        return meetings.contains(m);
    }

    /**
     * Replaces the given meeting {@code target} in the list with {@code editedMeeting}.
     * {@code target} must exist in the findingbrudders book.
     * The meeting identity of {@code editedMeeting} must not be the same as another existing meeting in
     * findingbrudders book.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireNonNull(editedMeeting);
        meetings.setMeeting(target, editedMeeting);
    }

    public String listMeetings() {
        return meetings.toString();
    }

    public int getMeetingSize() {
        return meetings.getMeetingsCount();
    }

    public ObservableList<Meeting> getMeetingList() {
        return meetings.getInternalList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
