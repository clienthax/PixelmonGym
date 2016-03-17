package uk.co.haxyshideout.pixelgym.commands.all;

import com.google.common.collect.Lists;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.PixelGym;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymData;

import java.util.ArrayList;

public class HelpCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();

        ArrayList<Text> helpText = Lists.newArrayList();
        /**
         * If the executor has the admin node list the admin only commands
         */
        if(src.hasPermission(PixelGym.getAdminPermString())) {
            helpText.add(Text.of(PixelGym.getPluginPrefix(), TextColors.GOLD, "Admin Commands."));
            helpText.add(Text.of(TextColors.GREEN, "/gym addleader <playerName> <gymName> ",TextColors.YELLOW, "Adds the specified player to the Gym Leaders of the specified Gym."));
            helpText.add(Text.of(TextColors.GREEN, "/gym delleader <playerName> <gymName> ",TextColors.YELLOW, "Deletes the specified player to the Gym Leaders of the specified Gym."));
            helpText.add(Text.of(TextColors.GREEN, "/gym closeall -",TextColors.YELLOW, " Closes all Gyms."));
            helpText.add(Text.of(TextColors.GREEN, "/gym setwarp <gymName> <inside/outside> - ",TextColors.YELLOW, "Sets the warp for the specified Gym at the point you are standing and facing."));
            helpText.add(Text.of(TextColors.GREEN, "/gym delwarp <gymName> <inside/outside> - ",TextColors.YELLOW, "Removes the warp for the specified Gym."));
            helpText.add(Text.of(TextColors.GREEN, "/gym testwarp <gymName> <inside/outside> - ",TextColors.YELLOW, "Teleports you to the specified Gym warp."));
            helpText.add(Text.of(TextColors.GREEN, "/gym givebadge <playerName> <gymName> - ",TextColors.YELLOW, "Gives the specified player the badge for the Gym specified."));
            helpText.add(Text.of(TextColors.GREEN, "/gym reload - ",TextColors.YELLOW, "Reloads the ",PixelGym.getPluginPrefix(), "config."));
            helpText.add(Text.of(TextColors.GREEN, "/gym setbadgeitem <gymName> - ",TextColors.YELLOW, "Sets the badge item for the specified Gym to the item in your hand."));
            helpText.add(Text.of(TextColors.GREEN, "/gym setlevelcap <gymName> <levelCap> - ",TextColors.YELLOW, "Sets the level cap for the specified Gym."));
            helpText.add(Text.of(TextColors.DARK_RED, "/gym wipeplayerdata <playerName> - ",TextColors.DARK_RED, "Wipes all ",PixelGym.getPluginPrefix(), "data from the specified player."));
            helpText.add(Text.EMPTY);
        }
        /**
         * If the executor is a gym leader include the gym leader commands
         */
        if(src instanceof Player && !GymData.getInstance().getGymsForLeader(((Player)src).getUniqueId()).isEmpty()) {
            helpText.add(Text.of(PixelGym.getPluginPrefix(), TextColors.GOLD, "Gym Leader Commands."));
            helpText.add(Text.of(TextColors.GREEN, "/gym opengym <gymName> - ",TextColors.YELLOW, "Opens the Gym ready for challengers."));
            helpText.add(Text.of(TextColors.GREEN, "/gym closegym <gymName> - ",TextColors.YELLOW, "Closes the Gym."));
            if(PixelGymConfig.getInstance().leadersCanHealOwnPokemon())
                helpText.add(Text.of(TextColors.GREEN, "/gym heal - ",TextColors.YELLOW, "Heals your party."));
            helpText.add(Text.of(TextColors.GREEN, "/gym quit - ",TextColors.YELLOW, "Force quit the battle you are currently in."));
            helpText.add(Text.of(TextColors.GREEN, "/gym remove <gymName> <playerName> - ",TextColors.YELLOW, "Removes the specified player from the queue of challengers."));
            helpText.add(Text.of(TextColors.GREEN, "/gym next <gymName> - ",TextColors.YELLOW, "Teleports the next queued challenger to the gym."));
            helpText.add(Text.of(TextColors.GREEN, "/gym setwinner <gymName> <playerName> - ",TextColors.YELLOW, "Sets the specified player as a winner."));
            helpText.add(Text.of(TextColors.GREEN, "/gym setloser <gymName> <playerName> - ",TextColors.YELLOW, "Sets the specified player as a loser."));
            helpText.add(Text.of(TextColors.GREEN, "/gym sendrules <gymName> <playerName> - ",TextColors.YELLOW, "Sends the rules for the specified Gym to the player."));
            helpText.add(Text.EMPTY);
        }
        helpText.add(Text.of(PixelGym.getPluginPrefix(), TextColors.GOLD, "Commands."));
        helpText.add(Text.of(TextColors.GREEN, "/gym join <gymName> - ",TextColors.YELLOW, "Join the queue for the specified Gym."));
        helpText.add(Text.of(TextColors.GREEN, "/gym leave <gymName> - ",TextColors.YELLOW, "Leave the queue for the specified Gym."));
        helpText.add(Text.of(TextColors.GREEN, "/gym leaders - ",TextColors.YELLOW, "Lists the currently active Gym Leaders."));
        helpText.add(Text.of(TextColors.GREEN, "/gym listgyms - ",TextColors.YELLOW, "Lists the currently enabled Gym's information."));
        helpText.add(Text.of(TextColors.GREEN, "/gym check/position <gymName> - ",TextColors.YELLOW, "Tells you your current place in the Gym queue."));
        helpText.add(Text.of(TextColors.GREEN, "/gym scoreboard <gymName> - ",TextColors.YELLOW, "Toggles the Gym scoreboard."));

        paginationService.builder().contents(helpText).sendTo(src);
        return CommandResult.success();
    }

}
