package uk.co.haxyshideout.pixelgym.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

import java.util.Collections;
import java.util.List;

public class TestCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;


        GymDataEntry gymDataEntry = GymData.getInstance().getGymData("Rock").get();

        ItemStackSnapshot stackSnapshot = gymDataEntry.makeBadge(Text.of("Haxy"));

        PlayerGymInfoData playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        playerGymInfoData.setBadgeItems(Collections.singletonList(stackSnapshot));
        player.offer(playerGymInfoData);

        playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        List<ItemStackSnapshot> itemStackSnapshots = playerGymInfoData.badgeItems().get();
        InventoryBasic inventoryBasic = new InventoryBasic("Badges", true, 54);

        for(int i = 0; i < itemStackSnapshots.size(); i++) {
            inventoryBasic.setInventorySlotContents(i, (net.minecraft.item.ItemStack) itemStackSnapshots.get(i).createStack());
        }
        ((EntityPlayerMP)player).displayGUIChest(inventoryBasic);



        return CommandResult.success();
    }

}
