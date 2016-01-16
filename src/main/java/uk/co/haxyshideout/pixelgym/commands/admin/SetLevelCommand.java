package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import uk.co.haxyshideout.pixelgym.commands.AdminCommand;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

public class SetLevelCommand extends AdminCommand implements AdminCommand.IGymSpecificAdminCommand {

    @Override
    public CommandResult executeGymSpecificAdminCommand(CommandSource src, GymDataEntry targetGym, CommandContext args) {
        targetGym.setLevelCap((Integer) args.getOne("levelCap").get());
        PixelGymConfig.getInstance().saveConfig();
        return CommandResult.success();
    }

}
