package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        String preamble = argMultimap.getPreamble();
        boolean hasNamePrefix = argMultimap.getValue(PREFIX_NAME).isPresent();

        // Ensure the user didn't type both an index AND a name
        ParserUtil.verifyIndexOrNamePresent(preamble, hasNamePrefix, ViewCommand.MESSAGE_USAGE);

        Index index = preamble.isEmpty() ? null : ParserUtil.parseIndex(preamble);
        Name name = hasNamePrefix ? ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()) : null;

        return new ViewCommand(index, name);
    }
}
