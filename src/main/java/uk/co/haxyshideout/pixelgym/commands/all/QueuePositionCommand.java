package uk.co.haxyshideout.pixelgym.commands.all;

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

public class QueuePositionCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.empty();
        }
        Player player = (Player) src;
        String gymName = (String) args.getOne("gymName").get();
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData(gymName);
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of(gymName+" Gym not found"));
            return CommandResult.empty();
        }

        GymDataEntry gymDataEntry = gymDataEntryOptional.get();
        Optional<Integer> positionInQueue = gymDataEntry.getPositionInQueue(player.getUniqueId());
        if (positionInQueue.isPresent()) {
            player.sendMessage(Text.of("You are position "+positionInQueue.get()+" in the queue for ", gymDataEntry.getFormattedGymName()));
        } else {
            player.sendMessage(Text.of("You are not in the queue for "+gymDataEntry.getFormattedGymName()));
        }

        return CommandResult.success();
    }
}
