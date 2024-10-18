package finding.brudders.logic.parser;

import static finding.brudders.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_EMAIL;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_MAJOR;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_NAME;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_PHONE;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_ROLE;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import finding.brudders.logic.commands.AddCommand;
import finding.brudders.logic.parser.exceptions.ParseException;
import finding.brudders.model.tag.Tag;
import finding.brudders.model.udder.Address;
import finding.brudders.model.udder.Email;
import finding.brudders.model.udder.Major;
import finding.brudders.model.udder.Name;
import finding.brudders.model.udder.Person;
import finding.brudders.model.udder.Phone;
import finding.brudders.model.udder.Role;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
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

        Person person = new Person(name, phone, email, role, major, address, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
