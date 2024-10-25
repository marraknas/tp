package seedu.findingbrudders.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.findingbrudders.logic.parser.Prefix;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The udder index provided is invalid";
    public static final String MESSAGE_INVALID_MEETING_INDEX = "The meeting index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d Udders listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code udder} for display to the user.
     */
    public static String format(Udder udder) {
        final StringBuilder builder = new StringBuilder();
        builder.append(udder.getName())
                .append("; Phone: ")
                .append(udder.getPhone())
                .append("; Email: ")
                .append(udder.getEmail())
                .append("; Role: ")
                .append(udder.getRole())
                .append("; Major: ")
                .append(udder.getMajor())
                .append("; Address: ")
                .append(udder.getAddress())
                .append("; Tags: ");
        udder.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code meeting} for display to the user.
     */
    public static String format(Meeting meeting) {
        final StringBuilder builder = new StringBuilder();
        builder.append(meeting.getLocation())
                .append("; Start Time: ")
                .append(meeting.getStartTime())
                .append("; End Time: ")
                .append(meeting.getEndTime());
        return builder.toString();
    }

}
