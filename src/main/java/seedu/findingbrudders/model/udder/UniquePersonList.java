package seedu.findingbrudders.model.udder;

import static java.util.Objects.requireNonNull;
import static seedu.findingbrudders.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.findingbrudders.model.udder.exceptions.DuplicatePersonException;
import seedu.findingbrudders.model.udder.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A udder is considered unique by comparing using {@code Udder#isSamePerson(Udder)}. As such, adding and updating of
 * persons uses Udder#isSamePerson(Udder) for equality so as to ensure that the udder being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a udder uses Udder#equals(Object) so
 * as to ensure that the udder with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Udder#isSamePerson(Udder)
 */
public class UniquePersonList implements Iterable<Udder> {

    private final ObservableList<Udder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Udder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent udder as the given argument.
     */
    public boolean contains(Udder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a udder to the list.
     * The udder must not already exist in the list.
     */
    public void add(Udder toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the udder {@code target} in the list with {@code editedUdder}.
     * {@code target} must exist in the list.
     * The udder identity of {@code editedUdder} must not be the same as another existing udder in the list.
     */
    public void setPerson(Udder target, Udder editedUdder) {
        requireAllNonNull(target, editedUdder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedUdder) && contains(editedUdder)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedUdder);
    }

    /**
     * Removes the equivalent udder from the list.
     * The udder must exist in the list.
     */
    public void remove(Udder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code udders}.
     * {@code udders} must not contain duplicate udders.
     */
    public void setPersons(List<Udder> udders) {
        requireAllNonNull(udders);
        if (!personsAreUnique(udders)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(udders);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Udder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Udder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code udders} contains only unique udders.
     */
    private boolean personsAreUnique(List<Udder> udders) {
        for (int i = 0; i < udders.size() - 1; i++) {
            for (int j = i + 1; j < udders.size(); j++) {
                if (udders.get(i).isSamePerson(udders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
