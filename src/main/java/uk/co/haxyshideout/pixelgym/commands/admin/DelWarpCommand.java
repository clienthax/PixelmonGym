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
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.WarpEntry;

import java.util.Optional;

public class DelWarpCommand extends AdminCommand implements AdminCommand.IGymSpecificAdminCommand {

    @Override
    public CommandResult executeGymSpecificAdminCommand(CommandSource src, GymDataEntry targetGym, CommandContext args) {
        String warpName = (String) args.getOne("warpName").get();
        if(warpName.equalsIgnoreCase("inside")) {
            targetGym.setInsideWarp((WarpEntry)null);
        } else if(warpName.equalsIgnoreCase("outside")) {
            targetGym.setOutsideWarp((WarpEntry)null);
        } else {
            src.sendMessage(Text.of(TextColors.RED, "Invalid warp name, use inside/outside."));
            return CommandResult.success();
        }

        src.sendMessage(Text.of(TextColors.GREEN, "Warp deleted, saving config."));
        PixelGymConfig.getInstance().saveConfig();
        return CommandResult.success();
    }

}
