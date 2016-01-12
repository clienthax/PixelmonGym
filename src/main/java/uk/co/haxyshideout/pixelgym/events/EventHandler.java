package uk.co.haxyshideout.pixelgym.events;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.ScoreboardData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

public class EventHandler {

    /**
     * Change this to use the inv impl in sponge once its ready
     * @param event
     */
    @Listener
    public void onInvClick(ClickInventoryEvent event) {
        Container container = (Container) event.getTargetInventory();
        if(container instanceof ContainerChest) {
            ContainerChest containerChest = (ContainerChest) container;
            if (containerChest.getLowerChestInventory().getDisplayName().getUnformattedText().equals("Badges")) {
                event.setCancelled(true);
            }
        }
    }

    @Listener
    public void onLogin(ClientConnectionEvent.Join event) {
        Player player = event.getTargetEntity();

        //Setup data on entity
        PlayerGymInfoData playerGymInfoData2 = player.getOrCreate(PlayerGymInfoData.class).orElse(new PlayerGymInfoData());
        DataTransactionResult offer = player.offer(playerGymInfoData2);

        if(PixelGymConfig.getInstance().showJoinMessages()) {
            for (String joinMessage : PixelGymConfig.getInstance().getJoinMessages()) {
                player.sendMessage(Text.of(joinMessage));
            }
        }

        ScoreboardData.refreshPlayerScoreboard(player);

        //If the player is a gym leader notify them of pending battles
        GymData.getInstance().getGymData().stream().filter(gymDataEntry -> gymDataEntry.isEnabled() && gymDataEntry.getGymLeaders().contains(player.getUniqueId()))
                .forEach(
                        gymDataEntry ->
                        {
                            if (gymDataEntry.getPlayerQueue().size() > 0) {
                                player.sendMessage(Text.of(TextColors.BLUE, "There are " + gymDataEntry.getPlayerQueue().size() + " players in the queue for the " + gymDataEntry.getName() + " Gym"));
                                player.sendMessage(Text.of(TextColors.BLUE, "Type /gym next " + gymDataEntry.getName() + " when you are ready to start taking battle\'s"));
                            }
                            gymDataEntry.addOnlineLeader(player.getUniqueId());
                            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_GRAY, "[", TextColors.AQUA, "PixelmonGymSponge", TextColors.DARK_GRAY, "] ", TextColors.GOLD, "A Gym Leader has come online! ("+player.getName()+")"));
                        });


    }

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect event) {
        Player player = event.getTargetEntity();
        for (GymDataEntry gymDataEntry : GymData.getInstance().getGymData()) {
            gymDataEntry.removeOnlineLeader(player.getUniqueId());
        }
    }

}
