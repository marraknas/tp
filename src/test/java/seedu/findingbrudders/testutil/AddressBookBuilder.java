package seedu.findingbrudders.testutil;

import seedu.findingbrudders.model.AddressBook;
import seedu.findingbrudders.model.udder.Udder;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Udder} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Udder udder) {
        addressBook.addPerson(udder);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
