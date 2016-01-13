package uk.co.haxyshideout.pixelgym.commands.gymleaders;

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

public class LoseCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player))
            return CommandResult.empty();

        Player gymLeader = (Player) src;

        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData((String) args.getOne("gymName").get());
        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!gymDataEntryOptional.isPresent()) {
            gymLeader.sendMessage(Text.of("Gym "+args.getOne("gymName")+" does not exist."));
            return CommandResult.empty();
        }
        if(!targetPlayerOptional.isPresent()) {
            gymLeader.sendMessage(Text.of("Player not found"));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();
        Player targetPlayer = targetPlayerOptional.get();

        if(!gymDataEntry.getOnlineLeaders().contains(gymLeader.getUniqueId())) {
            gymLeader.sendMessage(Text.of("You are not a leader for this gym, you can not add losses to a player"));
            return CommandResult.empty();
        }

        Optional<Integer> minutesSincePlayerChallengedGym = GymUtils.getMinutesSinceLastChallenge(targetPlayer, gymDataEntry.getName());
        if(!minutesSincePlayerChallengedGym.isPresent() || minutesSincePlayerChallengedGym.get() > 20) {
            gymLeader.sendMessage(Text.of("You can not add losses to players who have not battled within the last 20 minutes"));//TODO log
            return CommandResult.empty();
        }

        gymDataEntry.getOutsideWarp().ifPresent(warpEntry -> warpEntry.attemptWarp(targetPlayer));
        gymLeader.sendMessage(Text.of(TextColors.GREEN, "You teleported ", targetPlayer.getName(), " outside the ", gymDataEntry.getFormattedGymName()));


        return CommandResult.success();
    }

}
