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
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.utils.GymUtils;

import java.util.Optional;

public class NextCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) {//TODO use console too
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
        if(!gymDataEntry.isEnabled()) {
            src.sendMessage(Text.of("This gym is disabled in the config"));
            return CommandResult.empty();
        }

        if(!gymDataEntry.getOnlineLeaders().contains(player.getUniqueId())) {
            src.sendMessage(Text.of("You are not a leader of this gym, you can not use this command"));
            return CommandResult.empty();
        }

        if(!gymDataEntry.isCurrentlyOpen()) {
            src.sendMessage(Text.of("This gym is not open."));
            return CommandResult.empty();
        }

        if(!gymDataEntry.getPlayerQueue().isEmpty()) {
            Optional<Player> challengerOptional = Sponge.getServer().getPlayer(gymDataEntry.getPlayerQueue().get(0));
            if(challengerOptional.isPresent()) {
                Player challenger = challengerOptional.get();
                gymDataEntry.getInsideWarp().ifPresent(warpEntry -> warpEntry.attemptWarp(challenger));
                GymUtils.updateChallengedTime(challenger, gymDataEntry.getName());
                gymDataEntry.sendRules(challenger);
                src.sendMessage(Text.of(TextColors.GREEN, "Challenger ", challenger.getName(), " was teleported to the gym"));
            } else {
                src.sendMessage(Text.of(TextColors.RED, "Unable to get player, removing from queue."));
            }
            gymDataEntry.getPlayerQueue().remove(0);
        }

        return CommandResult.success();
    }

}
