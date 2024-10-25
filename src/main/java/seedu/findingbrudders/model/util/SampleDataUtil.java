package seedu.findingbrudders.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.findingbrudders.model.AddressBook;
import seedu.findingbrudders.model.ReadOnlyAddressBook;
import seedu.findingbrudders.model.tag.Tag;
import seedu.findingbrudders.model.udder.Address;
import seedu.findingbrudders.model.udder.Email;
import seedu.findingbrudders.model.udder.Major;
import seedu.findingbrudders.model.udder.Name;
import seedu.findingbrudders.model.udder.Phone;
import seedu.findingbrudders.model.udder.Role;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Udder[] getSamplePersons() {
        return new Udder[] {
            new Udder(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Role("brUdder"), new Major("cs"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Udder(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Role("mUdder"), new Major("bza"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Udder(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Role("brUdder"), new Major("isys"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Udder(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Role("mUdder"), new Major("cs"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Udder(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Role("brUdder"), new Major("bza"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Udder(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Role("brUdder"), new Major("cs"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Udder sampleUdder : getSamplePersons()) {
            sampleAb.addPerson(sampleUdder);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
