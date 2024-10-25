package seedu.findingbrudders.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.findingbrudders.commons.exceptions.IllegalValueException;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.tag.Tag;
import seedu.findingbrudders.model.udder.Address;
import seedu.findingbrudders.model.udder.Email;
import seedu.findingbrudders.model.udder.Major;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Name;
import seedu.findingbrudders.model.udder.Phone;
import seedu.findingbrudders.model.udder.Role;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Jackson-friendly version of {@link Udder}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Udder's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String role;
    private final String major;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedMeeting> meetings = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given udder details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("role") String role,
            @JsonProperty("major") String major, @JsonProperty("findingbrudders") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("meetings") List<JsonAdaptedMeeting> meetings) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.major = major;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (meetings != null) {
            this.meetings.addAll(meetings);
        }
    }

    /**
     * Converts a given {@code Udder} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Udder source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        role = source.getRole().toString();
        major = source.getMajor().toString();
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        meetings.addAll(source.getMeetings().getInternalList().stream()
                .map(JsonAdaptedMeeting::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted udder object into the model's {@code Udder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted udder.
     */
    public Udder toModelType() throws IllegalValueException, CommandException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final List<Meeting> personMeetings = new ArrayList<>();
        for (JsonAdaptedMeeting meeting : meetings) {
            personMeetings.add(meeting.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        if (major == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName()));
        }
        if (!Major.isValidMajor(major)) {
            throw new IllegalValueException(Major.MESSAGE_CONSTRAINTS);
        }
        final Major modelMajor = new Major(major);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        Udder toAdd = new Udder(modelName, modelPhone, modelEmail, modelRole, modelMajor, modelAddress, modelTags);

        for (Meeting meeting : personMeetings) {
            toAdd.getMeetings().addMeeting(meeting);
        }

        return toAdd;
    }

}
