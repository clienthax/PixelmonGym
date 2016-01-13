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
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

import java.util.List;

public class HealCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player))
            return CommandResult.empty();

        if (!PixelGymConfig.getInstance().leadersCanHealOwnPokemon()) {
            src.sendMessage(Text.of(TextColors.RED, "Healing is disabled in the config"));
            return CommandResult.success();
        }

        Player player = (Player) src;
        List<GymDataEntry> gymsForLeader = GymData.getInstance().getGymsForLeader(player.getUniqueId());
        if(gymsForLeader.isEmpty()) {
            player.sendMessage(Text.of(TextColors.RED, "You are not a gym leader, you can not use this command"));
        } else {
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokeheal "+player.getName());
        }
        return CommandResult.success();
    }

}
