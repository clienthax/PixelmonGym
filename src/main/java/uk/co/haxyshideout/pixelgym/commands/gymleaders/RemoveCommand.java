package uk.co.haxyshideout.pixelgym.commands.gymleaders;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import uk.co.haxyshideout.pixelgym.commands.GymLeaderCommand;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

public class RemoveCommand extends GymLeaderCommand implements GymLeaderCommand.IGymSpecificPlayerTargetCommand {

    @Override
    public CommandResult executeGymSpecificPlayerTargetCommand(Player gymLeader, Player targetPlayer, GymDataEntry targetGym, CommandContext args) {
        targetGym.removePlayerFromQueue(targetPlayer.getUniqueId());//TODO Log dis
        return CommandResult.success();
    }

}
