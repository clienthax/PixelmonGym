package uk.co.haxyshideout.pixelgym.commands.gymleaders;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

import java.util.Optional;

public class RemoveCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) {//TODO use console too
            return CommandResult.empty();
        }
        Player gymLeader = (Player) src;
        String gymName = (String) args.getOne("gymName").get();
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData(gymName);
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of(gymName+" Gym not found"));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();

        if(!gymDataEntry.getOnlineLeaders().contains(gymLeader.getUniqueId())) {
            src.sendMessage(Text.of("You are not a leader of this gym, you can not remove players from its queue"));
            return CommandResult.empty();
        }

        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!targetPlayerOptional.isPresent()) {
            src.sendMessage(Text.of("Player not found"));
            return CommandResult.empty();
        }

        Player targetPlayer = targetPlayerOptional.get();

        gymDataEntry.removePlayerFromQueue(targetPlayer.getUniqueId());//TODO Log dis


        return CommandResult.success();
    }

}
