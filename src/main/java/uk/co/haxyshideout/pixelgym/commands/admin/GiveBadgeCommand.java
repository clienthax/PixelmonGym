package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.commands.AdminCommand;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

import java.util.List;
import java.util.Optional;

public class GiveBadgeCommand extends AdminCommand implements AdminCommand.IGymSpecificPlayerTargetAdminCommand {

    @Override
    public CommandResult executeGymSpecificPlayerTargetAdminCommand(CommandSource src, Player targetPlayer, GymDataEntry targetGym, CommandContext args) {
        PlayerGymInfoData playerGymInfoData = targetPlayer.get(PlayerGymInfoData.class).get();
        List<String> gymsDefeated = playerGymInfoData.gymsDefeated().get();
        gymsDefeated.add(targetGym.getName());
        playerGymInfoData.setGymsDefeated(gymsDefeated);
        List<ItemStackSnapshot> badges = playerGymInfoData.badgeItems().get();
        badges.add(targetGym.makeBadge(Text.of("Admin Given by "+src.getName())));
        playerGymInfoData.setBadgeItems(badges);
        targetPlayer.offer(playerGymInfoData);

        src.sendMessage(Text.of(TextColors.GREEN, "You gave "+targetPlayer.getName()+" the ", targetGym.getFormattedBadgeName()));
        targetPlayer.sendMessage(Text.of(TextColors.GREEN, "You was given the ", targetGym.getFormattedBadgeName()));

        return CommandResult.success();
    }

}
