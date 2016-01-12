package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import static org.spongepowered.api.data.DataQuery.of;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public class PlayerGymInfoKeys {

    public static final Key<ListValue<String>> GYMS_DEFEATED = KeyFactory.makeListKey(String.class, of("gymsDefeated"));
    public static final Key<MapValue<String, String>> LAST_TIME_CHALLANGED = KeyFactory.makeMapKey(String.class, String.class, of("gymsDefeated"));
    public static final Key<ListValue<ItemStackSnapshot>> BADGE_ITEMS = KeyFactory.makeListKey(ItemStackSnapshot.class, of("badgeItems"));
    public static final Key<Value<Boolean>> SCOREBOARD_ENABLED = KeyFactory.makeSingleKey(Boolean.class, Value.class, of("scoreboardEnabled"));

}
