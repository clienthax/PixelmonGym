package uk.co.haxyshideout.pixelgym.commands.all;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.data.ScoreboardData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys;

public class ScoreboardCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player))
            return CommandResult.empty();

        Player player = (Player) src;
        PlayerGymInfoData playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        playerGymInfoData = playerGymInfoData.set(PlayerGymInfoKeys.SCOREBOARD_ENABLED, !playerGymInfoData.get(PlayerGymInfoKeys.SCOREBOARD_ENABLED).get());
        player.offer(playerGymInfoData);
        boolean enabled = playerGymInfoData.get(PlayerGymInfoKeys.SCOREBOARD_ENABLED).get();
        if(!enabled) {
            player.setScoreboard(null);
        } else {
            ScoreboardData.refreshPlayerScoreboard(player);
        }
        player.sendMessage(Text.of("Gym scoreboard "+(enabled ? "Enabled" : "Disabled")));

        return CommandResult.success();
    }
}
