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

import java.util.List;

public class QuitCommand extends GymLeaderCommand implements GymLeaderCommand.IGymLeaderCommand {

    @Override
    public CommandResult executeGymLeaderCommand(Player gymLeader, CommandContext args) {
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "endbattle " + gymLeader.getName());
        gymLeader.sendMessage(Text.of(TextColors.GREEN, "You have successfully quit the battle!"));
        return CommandResult.success();
    }

}
