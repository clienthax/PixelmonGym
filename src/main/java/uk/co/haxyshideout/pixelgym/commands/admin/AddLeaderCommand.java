package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.commands.AdminCommand;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.utils.GymUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class AddLeaderCommand extends AdminCommand implements AdminCommand.IGymSpecificPlayerTargetAdminCommand {

    //TODO uuid lookup via service..
    @Override
    public CommandResult executeGymSpecificPlayerTargetAdminCommand(CommandSource src, Player targetPlayer, GymDataEntry targetGym, CommandContext args) {
        targetGym.addLeader(targetPlayer.getUniqueId());
        targetGym.addOnlineLeader(targetPlayer.getUniqueId());
        PixelGymConfig.getInstance().saveConfig();
        src.sendMessage(Text.of(TextColors.GREEN, "Added ", targetPlayer.getName(), " as a gym leader for ", targetGym.getFormattedGymName(), "."));
        return CommandResult.success();
    }

}
