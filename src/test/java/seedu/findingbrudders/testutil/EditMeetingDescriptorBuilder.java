package seedu.findingbrudders.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.findingbrudders.logic.commands.EditMeetingCommand;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Name;

/**
 * A utility class to help with building EditMeetingDescriptor objects.
 */
public class EditMeetingDescriptorBuilder {
    private EditMeetingCommand.EditMeetingDescriptor descriptor;

    public EditMeetingDescriptorBuilder() {
        descriptor = new EditMeetingCommand.EditMeetingDescriptor();
    }

    public EditMeetingDescriptorBuilder(EditMeetingCommand.EditMeetingDescriptor descriptor) {
        this.descriptor = new EditMeetingCommand.EditMeetingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code udder}'s details
     */
    public EditMeetingDescriptorBuilder(Meeting meeting) {
        descriptor = new EditMeetingCommand.EditMeetingDescriptor();
        descriptor.setName(meeting.getPersonToMeet());
        descriptor.setStartTime(meeting.getStartTime());
        descriptor.setEndTime(meeting.getEndTime());
        descriptor.setLocation(meeting.getLocation());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withStartTime(String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        descriptor.setStartTime(LocalDateTime.parse(startTime, formatter));
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withEndTime(String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        descriptor.setStartTime(LocalDateTime.parse(endTime, formatter));
        return this;
    }

    /**
     * Sets the {@code location} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(location);
        return this;
    }

    public EditMeetingCommand.EditMeetingDescriptor build() {
        return descriptor;
    }
}
