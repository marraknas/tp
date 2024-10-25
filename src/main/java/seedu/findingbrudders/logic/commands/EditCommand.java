package seedu.findingbrudders.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.findingbrudders.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.findingbrudders.commons.core.index.Index;
import seedu.findingbrudders.commons.util.CollectionUtil;
import seedu.findingbrudders.commons.util.ToStringBuilder;
import seedu.findingbrudders.logic.Messages;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.tag.Tag;
import seedu.findingbrudders.model.udder.Address;
import seedu.findingbrudders.model.udder.Email;
import seedu.findingbrudders.model.udder.Major;
import seedu.findingbrudders.model.udder.Name;
import seedu.findingbrudders.model.udder.Phone;
import seedu.findingbrudders.model.udder.Role;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Edits the details of an existing udder in the findingbrudders book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the udder identified "
            + "by the index number used in the displayed udder list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_MAJOR + "MAJOR] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Udder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This udder already exists in the findingbrudders book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the udder in the filtered udder list to edit
     * @param editPersonDescriptor details to edit the udder with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Udder> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            System.out.println("Print from Edit Command Class: " + index.getZeroBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Udder udderToEdit = lastShownList.get(index.getZeroBased());
        Udder editedUdder = createEditedPerson(udderToEdit, editPersonDescriptor);

        if (!udderToEdit.isSamePerson(editedUdder) && model.hasPerson(editedUdder)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(udderToEdit, editedUdder);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedUdder)));
    }

    /**
     * Creates and returns a {@code Udder} with the details of {@code udderToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Udder createEditedPerson(Udder udderToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert udderToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(udderToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(udderToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(udderToEdit.getEmail());
        Role updatedRole = editPersonDescriptor.getRole().orElse(udderToEdit.getRole());
        Major updatedMajor = editPersonDescriptor.getMajor().orElse(udderToEdit.getMajor());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(udderToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(udderToEdit.getTags());

        return new Udder(updatedName, updatedPhone, updatedEmail, updatedRole, updatedMajor,
                updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the udder with. Each non-empty field value will replace the
     * corresponding field value of the udder.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Role role;
        private Major major;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setRole(toCopy.role);
            setMajor(toCopy.major);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, role, major, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setMajor(Major major) {
            this.major = major;
        }

        public Optional<Major> getMajor() {
            return Optional.ofNullable(major);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(role, otherEditPersonDescriptor.role)
                    && Objects.equals(major, otherEditPersonDescriptor.major)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("role", role)
                    .add("major", major)
                    .add("findingbrudders", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
