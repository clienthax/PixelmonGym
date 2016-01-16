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

import java.util.List;
import java.util.Optional;

public abstract class GymLeaderCommand implements CommandExecutor {

    @Override
    public final CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player))
            return CommandResult.empty();

        /**
         * Check if the player is a gym leader at any gym
         */
        Player player = (Player) src;
        List<GymDataEntry> gymsForLeader = GymData.getInstance().getGymsForLeader(player.getUniqueId());
        if(gymsForLeader.isEmpty()) {
            player.sendMessage(Text.of(TextColors.RED, "You are not a gym leader, you can not use this command"));
            return CommandResult.empty();
        }
        if(this instanceof IGymLeaderCommand) {
            return ((IGymLeaderCommand)this).executeGymLeaderCommand(player, args);
        }

        /**
         * Check the targeted gym exists
         */
        String gymName = (String) args.getOne("gymName").get();
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData(gymName);
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, gymName, " Gym not found"));
            return CommandResult.empty();
        }

        if(!gymDataEntryOptional.get().isEnabled()) {
            player.sendMessage(Text.of(TextColors.RED, "This gym is disabled in the config"));
            return CommandResult.empty();
        }

        /**
         * Check the player is a gym leader for the gym that was targeted
         */
        if(!gymDataEntryOptional.get().getGymLeaders().contains(player.getUniqueId())) {
            player.sendMessage(Text.of(TextColors.RED, "You are not a gym leader of this gym."));
            return CommandResult.empty();
        }

        if(this instanceof IGymSpecificCommand) {
            return ((IGymSpecificCommand)this).executeGymSpecificCommand(player, gymDataEntryOptional.get(), args);
        }

        /**
         * Check the targeted player exists
         */
        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!targetPlayerOptional.isPresent()) {
            player.sendMessage(Text.of(TextColors.RED, "Player not found"));
            return CommandResult.empty();
        }

        if(this instanceof IGymSpecificPlayerTargetCommand) {
            return ((IGymSpecificPlayerTargetCommand)this).executeGymSpecificPlayerTargetCommand(player, targetPlayerOptional.get(), gymDataEntryOptional.get(), args);
        }

        return CommandResult.empty();
    }

    /**
     * Interface for any command that needs the executor to be a gym leader,
     * used for non targeted commands such as /quit where no player or specific gym is required
     */
    @SuppressWarnings("UnusedParameters")
    public interface IGymLeaderCommand {
        CommandResult executeGymLeaderCommand(Player gymLeader, CommandContext args);
    }

    /**
     * Interface for any command that needs the executor to be a gym leader for the specified gym
     */
    @SuppressWarnings("UnusedParameters")
    public interface IGymSpecificCommand {
        CommandResult executeGymSpecificCommand(Player gymLeader, GymDataEntry targetGym, CommandContext args);
    }

    /**
     * Interface for any command that needs the executor to be a gym leader for the specified gym and where a player target is needed
     */
    @SuppressWarnings("UnusedParameters")
    public interface IGymSpecificPlayerTargetCommand {
        CommandResult executeGymSpecificPlayerTargetCommand(Player gymLeader, Player targetPlayer, GymDataEntry targetGym, CommandContext args);
    }

}
