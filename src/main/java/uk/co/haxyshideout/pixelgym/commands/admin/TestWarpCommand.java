package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.commands.AdminCommand;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

public class TestWarpCommand extends AdminCommand implements AdminCommand.IGymSpecificAdminCommand, AdminCommand.IIngameOnlyCommand {

    @Override
    public CommandResult executeGymSpecificAdminCommand(CommandSource src, GymDataEntry targetGym, CommandContext args) {
        Player player = (Player) src;
        String warpName = (String) args.getOne("warpName").get();
        if(warpName.equalsIgnoreCase("inside")) {
            targetGym.getInsideWarp().ifPresent(warpEntry -> warpEntry.attemptWarp(player));
        } else if(warpName.equalsIgnoreCase("outside")) {
            targetGym.getOutsideWarp().ifPresent(warpEntry -> warpEntry.attemptWarp(player));
        } else {
            player.sendMessage(Text.of(TextColors.RED, "Invalid warp name, use inside/outside."));
            return CommandResult.success();
        }

        return CommandResult.success();
    }

}
