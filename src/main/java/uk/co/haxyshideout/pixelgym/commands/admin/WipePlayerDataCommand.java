package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.commands.AdminCommand;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

import java.util.Optional;

public class WipePlayerDataCommand extends AdminCommand implements AdminCommand.IAdminCommand {

    @Override
    public CommandResult executeAdminCommand(CommandSource src, CommandContext args) {//TODO make this need a confirm later.
        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!targetPlayerOptional.isPresent()) {
            src.sendMessage(Text.of("Player not found"));
            return CommandResult.empty();
        }

        Player targetPlayer = targetPlayerOptional.get();
        targetPlayer.offer(new PlayerGymInfoData());
        src.sendMessage(Text.of("Wiped all pixelgym data for player "+targetPlayer.getName()));
        //TODO logging

        return CommandResult.success();
    }

}
