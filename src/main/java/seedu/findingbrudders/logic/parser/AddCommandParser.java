package seedu.findingbrudders.logic.parser;

import static seedu.findingbrudders.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.findingbrudders.logic.commands.AddCommand;
import seedu.findingbrudders.logic.parser.exceptions.ParseException;
import seedu.findingbrudders.model.tag.Tag;
import seedu.findingbrudders.model.udder.Address;
import seedu.findingbrudders.model.udder.Email;
import seedu.findingbrudders.model.udder.Major;
import seedu.findingbrudders.model.udder.Name;
import seedu.findingbrudders.model.udder.Phone;
import seedu.findingbrudders.model.udder.Role;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE, PREFIX_MAJOR,
                        PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                 PREFIX_ROLE, PREFIX_MAJOR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE, PREFIX_MAJOR,
                PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Major major = ParserUtil.parseMajor(argMultimap.getValue(PREFIX_MAJOR).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Udder udder = new Udder(name, phone, email, role, major, address, tagList);

        return new AddCommand(udder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
