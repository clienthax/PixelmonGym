package uk.co.haxyshideout.pixelgym.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.common.data.util.DataQueries;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;

import java.util.Optional;

public class SetBadgeItemCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) {
            return CommandResult.empty();
        }

        Player player = (Player) src;
        Optional<GymDataEntry> gymDataEntryOptional = GymData.getInstance().getGymData((String) args.getOne("gymName").get());
        if(!gymDataEntryOptional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Gym "+args.getOne("gymName")+" does not exist."));
            return CommandResult.empty();
        }
        GymDataEntry gymDataEntry = gymDataEntryOptional.get();
        Optional<ItemStack> itemInHand = player.getItemInHand();
        if (!itemInHand.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "You are not holding a item."));
            return CommandResult.empty();
        }
        ItemStack itemStack = itemInHand.get();
        gymDataEntry.setBadgeItemType(itemStack.getItem());
        int itemDamage = (int) itemStack.toContainer().get(DataQueries.ITEM_DAMAGE_VALUE).get();
        gymDataEntry.setBadgeItemDamageValue(itemDamage);
        PixelGymConfig.getInstance().saveConfig();
        src.sendMessage(Text.of(TextColors.GREEN, "Set the gym badge to the item you are holding."));
        return CommandResult.success();
    }

}
