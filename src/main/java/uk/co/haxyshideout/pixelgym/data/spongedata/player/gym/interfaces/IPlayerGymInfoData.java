package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.interfaces;

import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public interface IPlayerGymInfoData {

    ListValue<String> gymsDefeated();//<gymName>
    MapValue<String, String> lastTimeGymChallenged();//gymname, zoneddatetime
    ListValue<ItemStackSnapshot> badgeItems();
    Value<Boolean> scoreboardEnabled();

}
