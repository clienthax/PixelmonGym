package uk.co.haxyshideout.pixelgym.config.serializers;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.format.TextColor;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.GymPokemonEntry;

import java.util.List;
import java.util.UUID;

public class GymDataEntryTypeSerializer implements TypeSerializer<GymDataEntry> {

    @Override
    public GymDataEntry deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        GymDataEntry gymDataEntry = new GymDataEntry();
        /**
         * Required config vars
         */
        gymDataEntry.setName(value.getNode("Name").getString());
        gymDataEntry.setGymColour(value.getNode("Colour").getValue(new TypeToken<TextColor>() {}));
        gymDataEntry.setEnabled(value.getNode("Enabled").getBoolean());
        gymDataEntry.setBadgeItemType(value.getNode("BadgeItem").getValue(new TypeToken<ItemType>() {}));
        gymDataEntry.setBadgeItemDamageValue(value.getNode("BadgeItemDamageValue").getInt());
        gymDataEntry.setLevelCap(value.getNode("LevelCap").getInt());
        gymDataEntry.setRules(value.getNode("Rules").getList(new TypeToken<String>() {}));
        gymDataEntry.setGymPokemon(value.getNode("Pokemon").getList(new TypeToken<GymPokemonEntry>() {}));
        gymDataEntry.setGymLeaders(value.getNode("GymLeaders").getList(new TypeToken<UUID>() {}));
        /**
         * Optional config vars
         */
        ConfigurationNode coolDown = value.getNode("CoolDown");
        if(!coolDown.isVirtual()) {
            gymDataEntry.setCoolDown(coolDown.getInt());
        }
        ConfigurationNode entryFee = value.getNode("EntryFee");
        if(!entryFee.isVirtual()) {
            gymDataEntry.setEntryFee(entryFee.getInt());
        }
        ConfigurationNode previousGym = value.getNode("PreviousGym");
        if(!previousGym.isVirtual()) {
            gymDataEntry.setPreviousGymNames(previousGym.getList(new TypeToken<String>() {}));
        }
        return gymDataEntry;
    }

    @Override
    public void serialize(TypeToken<?> type, GymDataEntry obj, ConfigurationNode value) throws ObjectMappingException {
        CommentedConfigurationNode commentedValue = (CommentedConfigurationNode) value;//Naughty but should be fine as we should be the only plugin using this..
        /**
         * Required config vars
         */
        commentedValue.getNode("Name").setComment("The name of the gym, do not add \" Gym\" onto the end as this is done automatically.").setValue(obj.getName());
        commentedValue.getNode("Colour").setComment("The colour you want the gym's name to appear as.\n Possible values: NONE, BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE").setValue(new TypeToken<TextColor>() {}, obj.getColour());//TODO should we use TextColours for this instead, would prob be much easier
        commentedValue.getNode("Enabled").setComment("Enables the gym.").setValue(obj.isEnabled());
        commentedValue.getNode("BadgeItem").setComment("The name of the item to reward winners, in the format \"minecraft:chicken\".").setValue(new TypeToken<ItemType>() {}, obj.getBadgeItemType());
        commentedValue.getNode("BadgeItemDamageValue").setComment("The damage value of the item to reward winners.").setValue(obj.getBadgeItemDamageValue());
        commentedValue.getNode("LevelCap").setComment("The level cap for this gym.").setValue(obj.getLevelCap());
        commentedValue.getNode("Rules").setComment("The list of rules for this gym.").setValue(obj.getRules());
        commentedValue.getNode("Pokemon").setComment("The list of pokemon for this gym.").setValue(new TypeToken<List<GymPokemonEntry>>() {}, obj.getGymPokemon());
        commentedValue.getNode("GymLeaders").setComment("The list of Gym Leader player UUID's.").setValue(new TypeToken<List<UUID>>() {}, obj.getGymLeaders());
        /**
         * Optional config vars
         */
        commentedValue.getNode("CoolDown").setComment("The time in minutes that a player must wait to challenge the gym after losing.").setValue(obj.getCoolDownTime().orElse(null));
        commentedValue.getNode("EntryFee").setComment("The entry fee for the gym that the player must pay.").setValue(obj.getEntryFee().orElse(null));
        commentedValue.getNode("PreviousGym").setComment("The names of the gyms that the player must of beaten to challenge this gym.").setValue(obj.getDependsOnCompletingNames().orElse(null));
    }

}
