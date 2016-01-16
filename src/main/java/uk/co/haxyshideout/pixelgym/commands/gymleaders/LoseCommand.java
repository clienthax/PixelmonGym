package uk.co.haxyshideout.pixelgym.commands.gymleaders;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.commands.GymLeaderCommand;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.utils.GymUtils;

import java.util.Optional;

public class LoseCommand extends GymLeaderCommand implements GymLeaderCommand.IGymSpecificPlayerTargetCommand {

    @Override
    public CommandResult executeGymSpecificPlayerTargetCommand(Player gymLeader, Player targetPlayer, GymDataEntry targetGym, CommandContext args) {
        Optional<Integer> minutesSincePlayerChallengedGym = GymUtils.getMinutesSinceLastChallenge(targetPlayer, targetGym.getName());
        if(!minutesSincePlayerChallengedGym.isPresent() || minutesSincePlayerChallengedGym.get() > 20) {
            gymLeader.sendMessage(Text.of(TextColors.RED, "You can not add losses to players who have not battled within the last 20 minutes"));//TODO log
            return CommandResult.empty();
        }

        targetGym.getOutsideWarp().ifPresent(warpEntry -> warpEntry.attemptWarp(targetPlayer));
        gymLeader.sendMessage(Text.of(TextColors.GREEN, "You teleported ", targetPlayer.getName(), " outside the ", targetGym.getFormattedGymName()));
        return CommandResult.success();
    }

}
