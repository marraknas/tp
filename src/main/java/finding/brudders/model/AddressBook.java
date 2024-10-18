package finding.brudders.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import finding.brudders.commons.util.ToStringBuilder;
import finding.brudders.model.udder.Meeting;
import finding.brudders.model.udder.Meetings;
import finding.brudders.model.udder.Person;
import finding.brudders.model.udder.UniquePersonList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
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
    */ {
        persons = new UniquePersonList();
        meetings = new Meetings();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the udder list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
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
     * Returns true if a udder with the same identity as {@code udder} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a udder to the address book.
     * The udder must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given udder {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The udder identity of {@code editedPerson} must not be the same as another existing udder in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    public void addMeeting(Meeting m) {
        meetings.addMeeting(m);
    }

    public String listMeetings() {
        return meetings.toString();
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
