package uk.co.haxyshideout.pixelgym.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.GymPokemonEntry;

import java.util.Optional;

public class SendRulesCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player))
            return CommandResult.empty();

        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData((String) args.getOne("gymName").get());
        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of("Gym "+args.getOne("gymName")+" does not exist."));
            return CommandResult.empty();
        }
        if(!targetPlayerOptional.isPresent()) {
            src.sendMessage(Text.of("Player not found"));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();
        Player targetPlayer = targetPlayerOptional.get();

        if(!gymDataEntry.getOnlineLeaders().contains(((Player) src).getUniqueId())) {
            src.sendMessage(Text.of("You are not a leader for this gym, you can not send the rules to a player"));
            return CommandResult.empty();
        }

        targetPlayer.sendMessage(Text.of(TextColors.GOLD, targetPlayer.getName()+", Make sure you read the rules before facing the "+gymDataEntry.getName()+" Gym"));
        for(String rule : gymDataEntry.getRules()) {
            targetPlayer.sendMessage(Text.of(TextColors.AQUA, rule));
        }
        src.sendMessage(Text.of("Sent rules for "+gymDataEntry.getName()+" Gym to "+targetPlayer.getName()));

        return CommandResult.success();
    }
}
