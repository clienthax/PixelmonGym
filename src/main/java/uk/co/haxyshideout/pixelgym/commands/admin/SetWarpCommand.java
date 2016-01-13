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

public class SetWarpCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player))
            return CommandResult.empty();
        Player player = (Player) src;
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData((String) args.getOne("gymName").get());
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Gym "+args.getOne("gymName")+" does not exist."));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();
        String warpName = (String) args.getOne("warpName").get();
        if(warpName.equalsIgnoreCase("inside")) {
            gymDataEntry.setInsideWarp(player);
        } else if(warpName.equalsIgnoreCase("outside")) {
            gymDataEntry.setOutsideWarp(player);
        } else {
            player.sendMessage(Text.of(TextColors.RED, "Invalid warp name, use inside/outside."));
            return CommandResult.success();
        }

        player.sendMessage(Text.of("Warp set, saving config."));
        PixelGymConfig.getInstance().saveConfig();
        return CommandResult.success();
    }

}
