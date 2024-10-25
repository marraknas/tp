package seedu.findingbrudders.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.findingbrudders.model.tag.Tag;
import seedu.findingbrudders.model.udder.Address;
import seedu.findingbrudders.model.udder.Email;
import seedu.findingbrudders.model.udder.Major;
import seedu.findingbrudders.model.udder.Name;
import seedu.findingbrudders.model.udder.Phone;
import seedu.findingbrudders.model.udder.Role;
import seedu.findingbrudders.model.udder.Udder;
import seedu.findingbrudders.model.util.SampleDataUtil;

/**
 * A utility class to help with building Udder objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ROLE = "mUdder";
    public static final String DEFAULT_MAJOR = "cs";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Role role;
    private Major major;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        role = new Role(DEFAULT_ROLE);
        major = new Major(DEFAULT_MAJOR);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code udderToCopy}.
     */
    public PersonBuilder(Udder udderToCopy) {
        name = udderToCopy.getName();
        phone = udderToCopy.getPhone();
        email = udderToCopy.getEmail();
        role = udderToCopy.getRole();
        major = udderToCopy.getMajor();
        address = udderToCopy.getAddress();
        tags = new HashSet<>(udderToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Udder} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Udder} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Udder} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Udder} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Udder} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Udder} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code Udder} that we are building.
     */
    public PersonBuilder withMajor(String major) {
        this.major = new Major(major);
        return this;
    }

    public Udder build() {
        return new Udder(name, phone, email, role, major, address, tags);
    }

}
