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
import uk.co.haxyshideout.pixelgym.commands.GymLeaderCommand;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

import java.util.List;

public class HealCommand extends GymLeaderCommand implements GymLeaderCommand.IGymLeaderCommand {

    @Override
    public CommandResult executeGymLeaderCommand(Player player, CommandContext args) {
        if (!PixelGymConfig.getInstance().leadersCanHealOwnPokemon()) {
            player.sendMessage(Text.of(TextColors.RED, "Healing is disabled in the config"));
            return CommandResult.success();
        }
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal "+player.getName());
        return CommandResult.success();
    }

}
