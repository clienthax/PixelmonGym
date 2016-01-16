package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.commands.AdminCommand;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;

public class ReloadCommand extends AdminCommand implements AdminCommand.IAdminCommand {

    @Override
    public CommandResult executeAdminCommand(CommandSource src, CommandContext args) {
        PixelGymConfig.getInstance().loadConfig();
        src.sendMessage(Text.of(TextColors.GREEN, "Reloaded config"));
        return CommandResult.success();
    }

}
