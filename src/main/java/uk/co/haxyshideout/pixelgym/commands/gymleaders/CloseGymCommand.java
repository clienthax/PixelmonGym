package uk.co.haxyshideout.pixelgym.commands.gymleaders;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import uk.co.haxyshideout.pixelgym.commands.GymLeaderCommand;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

public class CloseGymCommand extends GymLeaderCommand implements GymLeaderCommand.IGymSpecificCommand {

    @Override
    public CommandResult executeGymSpecificCommand(Player gymLeader, GymDataEntry targetGym, CommandContext args) {
        targetGym.setCurrentlyOpen(false);
        return CommandResult.success();
    }

}
