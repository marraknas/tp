package finding.brudders.testutil;

import static finding.brudders.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_EMAIL;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_MAJOR;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_NAME;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_PHONE;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_ROLE;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import finding.brudders.logic.commands.AddCommand;
import finding.brudders.logic.commands.EditCommand.EditPersonDescriptor;
import finding.brudders.model.tag.Tag;
import finding.brudders.model.udder.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code udder}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code udder}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ROLE + person.getRole().toString() + " ");
        sb.append(PREFIX_MAJOR + person.getMajor().toString() + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.toString()).append(" "));
        descriptor.getMajor().ifPresent(major -> sb.append(PREFIX_MAJOR).append(major.toString()).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
