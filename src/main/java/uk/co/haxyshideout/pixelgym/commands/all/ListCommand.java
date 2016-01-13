package uk.co.haxyshideout.pixelgym.commands.all;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

public class ListCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        GymData.getInstance().getGymData().stream().filter(GymDataEntry::isEnabled).forEach(
                gymDataEntry -> src.sendMessage(Text.of(gymDataEntry.getFormattedGymName()," is: ", gymDataEntry.isCurrentlyOpen() ? TextColors.DARK_GREEN : TextColors.RED, gymDataEntry.isCurrentlyOpen() ? "Open" : "Closed", TextColors.BLUE, " - Level Cap = " + gymDataEntry.getLevelCap()))
        );
        return CommandResult.success();
    }
}
