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

import java.util.Optional;

public class CloseGymCommand extends GymLeaderCommand implements GymLeaderCommand.IGymSpecificCommand {

    @Override
    public CommandResult executeGymSpecificCommand(Player gymLeader, GymDataEntry targetGym, CommandContext args) {
        targetGym.setCurrentlyOpen(false);
        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.RED, "The ", targetGym.getFormattedGymName(), " is now closed"));
        return CommandResult.success();
    }

}
