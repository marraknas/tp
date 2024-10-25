package seedu.findingbrudders.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.findingbrudders.commons.util.ToStringBuilder;
import seedu.findingbrudders.logic.Messages;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Adds a udder to the findingbrudders book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a udder to the findingbrudders book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_MAJOR + "MAJOR "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ROLE + "brUdder "
            + PREFIX_MAJOR + "cs "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New udder added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This udder already exists in the findingbrudders book";

    private final Udder toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Udder}
     */
    public AddCommand(Udder udder) {
        requireNonNull(udder);
        toAdd = udder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
