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
import uk.co.haxyshideout.pixelgym.data.GymPokemonEntry;

import java.util.Optional;

public class SendRulesCommand extends GymLeaderCommand implements GymLeaderCommand.IGymSpecificPlayerTargetCommand {

    @Override
    public CommandResult executeGymSpecificPlayerTargetCommand(Player gymLeader, Player targetPlayer, GymDataEntry targetGym, CommandContext args) {
        targetGym.sendRules(targetPlayer);
        gymLeader.sendMessage(Text.of("Sent rules for ", targetGym.getFormattedGymName(), " to "+targetPlayer.getName()));
        return CommandResult.success();
    }

}
