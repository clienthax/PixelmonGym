package uk.co.haxyshideout.pixelgym.commands.all;

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

public class ListRulesCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData((String) args.getOne("gymName").get());
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of("Gym "+args.getOne("gymName")+" does not exist."));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();

        src.sendMessage(Text.of(TextColors.GOLD, src.getName()+", Make sure you read the rules before facing the ", gymDataEntry.getFormattedGymName()));
        for(String rule : gymDataEntry.getRules()) {
            src.sendMessage(Text.of(TextColors.AQUA, rule));
        }

        return CommandResult.success();
    }
}
