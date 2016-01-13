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
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

import java.util.List;
import java.util.Optional;

public class GiveBadgeCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData((String) args.getOne("gymName").get());
        Optional<Player> targetPlayerOptional = args.getOne("player");
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of("Gym "+args.getOne("gymName")+" does not exist."));
            return CommandResult.empty();
        }
        if(!targetPlayerOptional.isPresent()) {
            src.sendMessage(Text.of("Player not found"));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();
        Player targetPlayer = targetPlayerOptional.get();

        PlayerGymInfoData playerGymInfoData = targetPlayer.get(PlayerGymInfoData.class).get();
        List<String> gymsDefeated = playerGymInfoData.gymsDefeated().get();
        gymsDefeated.add(gymDataEntry.getName());
        playerGymInfoData.setGymsDefeated(gymsDefeated);
        List<ItemStackSnapshot> badges = playerGymInfoData.badgeItems().get();
        badges.add(gymDataEntry.makeBadge(Text.of("Admin Given")));
        playerGymInfoData.setBadgeItems(badges);
        targetPlayer.offer(playerGymInfoData);

        src.sendMessage(Text.of(TextColors.GREEN, "You gave "+targetPlayer.getName()+" the ", gymDataEntry.getFormattedBadgeName()));
        targetPlayer.sendMessage(Text.of(TextColors.GREEN, "You was given the ", gymDataEntry.getFormattedBadgeName()));

        return CommandResult.success();
    }

}
