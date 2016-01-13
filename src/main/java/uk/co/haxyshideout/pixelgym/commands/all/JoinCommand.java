package uk.co.haxyshideout.pixelgym.commands.all;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.PixelGym;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys;
import uk.co.haxyshideout.pixelgym.utils.GymUtils;

import java.math.BigDecimal;
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

        /**
         * Check if the player has already completed this gym
         */
        Player player = (Player) src;
        PlayerGymInfoData playerGymData = player.getOrCreate(PlayerGymInfoData.class).get();
        List<String> completedGyms = playerGymData.getValue(PlayerGymInfoKeys.GYMS_DEFEATED).get().get();
        if (completedGyms.contains(gymName)) {
            player.sendMessage(Text.of(TextColors.RED, "You have already completed this gym! You may not do it again!"));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();

        /**
         * Check if the player has to wait before challenging this gym again
         */
        Optional<Integer> remainingCooldownMinutes = GymUtils.getRemainingCooldownMinutes(playerGymData, gymName);
        if(remainingCooldownMinutes.isPresent()) {
            player.sendMessage(Text.of("You must wait "+remainingCooldownMinutes.get()+" minutes before you can challenge the ", gymDataEntry.getColour(), gymName +" Gym", TextColors.RESET, " again."));
            return CommandResult.empty();
        }

        /**
         * Check that the player has enough money to join this queue (if they leave the game before challenge it will be refunded)
         */
        Optional<EconomyService> economyServiceOptional = PixelGym.getInstance().getEconomy();
        if(economyServiceOptional.isPresent()) {
            if(gymDataEntry.getEntryFee().isPresent()) {
                EconomyService economyService = economyServiceOptional.get();
                Optional<UniqueAccount> account = economyService.getAccount(player.getUniqueId());
                if(account.isPresent()) {
                    TransactionResult result = account.get().withdraw(economyService.getDefaultCurrency(), new BigDecimal(gymDataEntry.getEntryFee().get()), Cause.of(PixelGym.getInstance()));
                    if(result.getResult() == ResultType.SUCCESS) {
                        player.sendMessage(Text.of("You paid ", economyService.getDefaultCurrency().getSymbol(), gymDataEntry.getEntryFee().get(), " to challenge the ", gymDataEntry.getFormattedGymName(), "."));
                        player.sendMessage(Text.of("This will be refunded to you if you disconnect from the game before you battle the Gym Leader"));
                    } else {
                        player.sendMessage(Text.of("You do not have enough money you need ", economyService.getDefaultCurrency().getSymbol(), gymDataEntry.getEntryFee().get(), " to challenge the ", gymDataEntry.getFormattedGymName(), "."));
                        return CommandResult.empty();
                    }
                }
            }
        }

        /**
         * Check that the player has defeated all the required gyms
         */
        Optional<List<String>> dependsOnCompleting = gymDataEntry.getDependsOnCompletingNames();
        if(dependsOnCompleting.isPresent()) {
            if(!completedGyms.containsAll(dependsOnCompleting.get())) {
                List<String> missingGyms = dependsOnCompleting.get();
                missingGyms.removeAll(completedGyms);
                player.sendMessage(Text.of(TextColors.RED, "You can not challenge this gym as you have not beat the "+missingGyms.toString()+" Gyms"));//TODO test with more than one gym
                return CommandResult.empty();
            }
        }

        /**
         * Check if the player is already in the queue for the gym
         */
        if(gymDataEntry.getPlayerQueue().contains(player.getUniqueId())) {
            player.sendMessage(Text.of(TextColors.RED, "You are already in this queue, please wait to be teleported."));
            return CommandResult.empty();
        }

        gymDataEntry.addPlayerToQueue(player.getUniqueId());

        player.sendMessage(Text.of(TextColors.GREEN, "Added to queue for ", gymDataEntry.getFormattedGymName()));
        player.sendMessage(Text.of(TextColors.GOLD, "You are in position " + gymDataEntry.getPositionInQueue(player.getUniqueId()) + " for the ", gymDataEntry.getFormattedGymName()));
        player.sendMessage(Text.of(TextColors.GREEN + "Notified gym leaders of ", gymDataEntry.getFormattedGymName(), TextColors.GREEN, " that you are waiting to be battled!"));

        for (UUID uuid : gymDataEntry.getOnlineLeaders()) {
            Optional<Player> playerOptional = Sponge.getServer().getPlayer(uuid);
            if(playerOptional.isPresent()) {
                playerOptional.get().sendMessage(Text.of(TextColors.BLUE, "A challenger has joined the queue for the ", gymDataEntry.getFormattedGymName()));
                playerOptional.get().sendMessage(Text.of(TextColors.BLUE, "Type /gym next "+gymName+" to teleport them to your gym"));
            }
        }


        return CommandResult.success();
    }
}
