package uk.co.haxyshideout.pixelgym.commands.gymleaders;

import org.spongepowered.api.Sponge;
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

public class NextCommand extends GymLeaderCommand implements GymLeaderCommand.IGymSpecificCommand {

    @Override
    public CommandResult executeGymSpecificCommand(Player gymLeader, GymDataEntry targetGym, CommandContext args) {
        if(!targetGym.isCurrentlyOpen()) {
            gymLeader.sendMessage(Text.of("This gym is not open."));
            return CommandResult.empty();
        }
        if(!targetGym.getPlayerQueue().isEmpty()) {
            Optional<Player> challengerOptional = Sponge.getServer().getPlayer(targetGym.getPlayerQueue().get(0));
            if(challengerOptional.isPresent()) {
                Player challenger = challengerOptional.get();
                targetGym.getInsideWarp().ifPresent(warpEntry -> warpEntry.attemptWarp(challenger));
                GymUtils.updateChallengedTime(challenger, targetGym.getName());
                targetGym.sendRules(challenger);
                gymLeader.sendMessage(Text.of(TextColors.GREEN, "Challenger ", challenger.getName(), " was teleported to the gym"));
            } else {
                gymLeader.sendMessage(Text.of(TextColors.RED, "Unable to get player, removing from queue."));
            }
            targetGym.getPlayerQueue().remove(0);
        }
        return CommandResult.success();
    }

}
