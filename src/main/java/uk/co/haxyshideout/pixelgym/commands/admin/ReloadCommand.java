package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;

public class ReloadCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        PixelGymConfig.getInstance().loadConfig();
        src.sendMessage(Text.of("Reloaded config"));
        return CommandResult.success();
    }

}
