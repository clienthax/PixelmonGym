package uk.co.haxyshideout.pixelgym.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

import java.util.Optional;
import java.util.UUID;

public class LeadersCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of(TextColors.GOLD, "----- Online Gym Leaders -----"));
        src.sendMessage(Text.NEW_LINE);
        for (GymDataEntry gymDataEntry : GymData.getInstance().getGymData()) {
            for (UUID uuid : gymDataEntry.getOnlineLeaders()) {
                Optional<Player> playerOptional = Sponge.getServer().getPlayer(uuid);
                if(playerOptional.isPresent()) {
                    src.sendMessage(Text.of(TextColors.GREEN, playerOptional.get().getName(), TextColors.BLACK, " - ", TextStyles.BOLD, gymDataEntry.getName()+" Gym"));
                }
            }
        }
        return CommandResult.success();
    }
}
