package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Views the full details of a specific contact in the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the full details of a contact using either their index OR their full name.\n"
            + "Parameters (by Index): INDEX (must be a positive integer)\n"
            + "Parameters (by Name): " + PREFIX_NAME + "CONTACT_NAME\n"
            + "Example 1: " + COMMAND_WORD + " 1\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_NAME + "Zi Xuan";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Viewing Contact: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Error: Contact does not exist.";

    private final Index targetIndex;
    private final Name targetName;

    /**
     * Constructor for viewCommand class
     */
    public ViewCommand(Index targetIndex, Name targetName) {
        this.targetIndex = targetIndex;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToView = null;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToView = lastShownList.get(targetIndex.getZeroBased());
        } else if (targetName != null) {
            Optional<Person> personOptional = lastShownList.stream()
                    .filter(person -> person.getName().fullName.equalsIgnoreCase(targetName.fullName))
                    .findFirst();

            if (personOptional.isEmpty()) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            }
            personToView = personOptional.get();
        }

        if (personToView == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        // We pass the personToView into the upgraded CommandResult!
        return new CommandResult(
                String.format(MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName().fullName),
                false, false, personToView);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewCommand)) {
            return false;
        }
        ViewCommand e = (ViewCommand) other;

        boolean isSameIndex = (targetIndex == null && e.targetIndex == null)
                || (targetIndex != null && targetIndex.equals(e.targetIndex));
        boolean isSameName = (targetName == null && e.targetName == null)
                || (targetName != null && targetName.equals(e.targetName));

        return isSameIndex && isSameName;
    }
}
