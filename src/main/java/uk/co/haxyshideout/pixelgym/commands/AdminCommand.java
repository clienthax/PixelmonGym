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

import java.util.Optional;

public abstract class AdminCommand implements CommandExecutor {

    @Override
    public final CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(this instanceof IIngameOnlyCommand && !(src instanceof Player)) {
            src.sendMessage(Text.of(TextColors.RED, "This command can only be used in game."));
            return CommandResult.empty();
        }

        if(this instanceof IAdminCommand) {
            return ((IAdminCommand)this).executeAdminCommand(src, args);
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
            src.sendMessage(Text.of(TextColors.RED, "This gym is disabled in the config"));
            return CommandResult.empty();
        }

        if(this instanceof IGymSpecificAdminCommand) {
            return ((IGymSpecificAdminCommand)this).executeGymSpecificAdminCommand(src, gymDataEntryOptional.get(), args);
        }

        /**
         * Check the targeted player exists
         */
        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!targetPlayerOptional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player not found"));
            return CommandResult.empty();
        }

        if(this instanceof IGymSpecificPlayerTargetAdminCommand) {
            return ((IGymSpecificPlayerTargetAdminCommand)this).executeGymSpecificPlayerTargetAdminCommand(src, targetPlayerOptional.get(), gymDataEntryOptional.get(), args);
        }

        return CommandResult.empty();
    }

    /**
     * Interface for any admin commands that require a ingame player to be the executor
     */
    public interface IIngameOnlyCommand {

    }

    /**
     * Interface for any command that doesn't need a target
     */
    @SuppressWarnings("UnusedParameters")
    public interface IAdminCommand {
        CommandResult executeAdminCommand(CommandSource src, CommandContext args);
    }

    /**
     * Interface for any command that targets a specific gym
     */
    @SuppressWarnings("UnusedParameters")
    public interface IGymSpecificAdminCommand {
        CommandResult executeGymSpecificAdminCommand(CommandSource src, GymDataEntry targetGym, CommandContext args);
    }

    /**
     * Interface for any command that needs a player target
     */
    @SuppressWarnings("UnusedParameters")
    public interface IGymSpecificPlayerTargetAdminCommand {
        CommandResult executeGymSpecificPlayerTargetAdminCommand(CommandSource src, Player targetPlayer, GymDataEntry targetGym, CommandContext args);
    }

}
