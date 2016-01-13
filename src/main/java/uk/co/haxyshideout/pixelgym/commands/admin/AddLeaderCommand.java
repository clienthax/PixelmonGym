package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

import java.util.Optional;

public class AddLeaderCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData((String) args.getOne("gymName").get());
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Gym "+args.getOne("gymName")+" does not exist."));
            return CommandResult.empty();
        }
        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!targetPlayerOptional.isPresent()) {
            src.sendMessage(Text.of("Player not found"));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();
        Player targetPlayer = targetPlayerOptional.get();
        gymDataEntry.addLeader(targetPlayer.getUniqueId());
        gymDataEntry.addOnlineLeader(targetPlayer.getUniqueId());
        PixelGymConfig.getInstance().saveConfig();

        return CommandResult.success();
    }

}
