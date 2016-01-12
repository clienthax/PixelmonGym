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
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JoinCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.empty();
        }
        String gymName = (String) args.getOne("gymName").get();
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData(gymName);
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of(gymName+" Gym not found"));
            return CommandResult.empty();
        }

        Player player = (Player) src;
        PlayerGymInfoData playerGymData = player.getOrCreate(PlayerGymInfoData.class).get();
        List<String> completedGyms = playerGymData.getValue(PlayerGymInfoKeys.GYMS_DEFEATED).get().get();
        if (completedGyms.contains(gymName)) {
            player.sendMessage(Text.of(TextColors.RED, "You have already completed this gym! You may not do it again!"));
            return CommandResult.empty();
        }
        //TODO cooldown check (use localdata/time) to avoid task bs - store last challanged gym names / times onto the playergymdata

        GymDataEntry gymDataEntry = gymDataEntryOptional.get();

        Optional<String> dependsOnCompleting = gymDataEntry.getDependsOnCompletingName();
        if(dependsOnCompleting.isPresent()) {
            if(!completedGyms.contains(dependsOnCompleting.get())) {
                player.sendMessage(Text.of(TextColors.RED, "You can not challenge this gym as you have not beat the "+dependsOnCompleting.get()+" Gym"));
                return CommandResult.empty();
            }
        }

        if(gymDataEntry.getPlayerQueue().contains(player.getUniqueId())) {
            player.sendMessage(Text.of(TextColors.RED, "You are already in this queue, please wait to be teleported."));
            return CommandResult.empty();
        }

        gymDataEntry.addPlayerToQueue(player.getUniqueId());

        player.sendMessage(Text.of(TextColors.GREEN, "Added to queue for ", TextColors.YELLOW, TextStyles.BOLD, gymName+" Gym"));
        player.sendMessage(Text.of(TextColors.GOLD, "You are in position " + gymDataEntry.getPositionInQueue(player.getUniqueId()) + " for the " + gymName + " Gym"));
        player.sendMessage(Text.of(TextColors.GREEN + "Notified gym leaders of "+gymName+" Gym", TextColors.BLACK, " that you are waiting to be battled!"));

        for (UUID uuid : gymDataEntry.getOnlineLeaders()) {
            Optional<Player> playerOptional = Sponge.getServer().getPlayer(uuid);
            if(playerOptional.isPresent()) {
                playerOptional.get().sendMessage(Text.of(TextColors.BLUE, "A challenger has joined the queue for ", TextColors.YELLOW, TextStyles.BOLD, gymName + " Gym"));
                playerOptional.get().sendMessage(Text.of(TextColors.BLUE, "Type /gym next "+gymName+" to teleport them to your gym"));
            }
        }


        return CommandResult.success();
    }
}
