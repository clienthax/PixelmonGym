package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.commands.AdminCommand;
import uk.co.haxyshideout.pixelgym.data.GymData;

public class CloseAllCommand extends AdminCommand implements AdminCommand.IAdminCommand {

    @Override
    public CommandResult executeAdminCommand(CommandSource src, CommandContext args) {
        GymData.getInstance().getGymData().forEach(GymDataEntry -> GymDataEntry.setCurrentlyOpen(false));
        src.sendMessage(Text.of(TextColors.GREEN, "All Gyms closed"));
        return CommandResult.success();
    }

}
