package uk.co.haxyshideout.pixelgym.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.common.data.builder.item.SpongeItemStackSnapshotBuilder;
import org.spongepowered.common.data.util.DataQueries;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

/*
        PlayerGymInfoData playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        List<ItemStackSnapshot> itemStackSnapshots = playerGymInfoData.badgeItems().get();
        InventoryBasic inventoryBasic = new InventoryBasic("Badges", true, 54);

        for(int i = 0; i < itemStackSnapshots.size(); i++) {
            if(i < inventoryBasic.getSizeInventory()) {
                ItemStackSnapshot snapshot = itemStackSnapshots.get(i);
                ItemStack stack = snapshot.createStack();
                inventoryBasic.setInventorySlotContents(i, (net.minecraft.item.ItemStack) stack);
            }
        }
        ((EntityPlayerMP)player).displayGUIChest(inventoryBasic);

        */


        return CommandResult.success();
    }

}
