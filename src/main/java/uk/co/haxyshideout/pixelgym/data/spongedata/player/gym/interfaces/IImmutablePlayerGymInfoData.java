package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.interfaces;

import org.spongepowered.api.data.value.immutable.ImmutableListValue;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public interface IImmutablePlayerGymInfoData {

    ImmutableListValue<String> gymsDefeated();//<gymName>
    ImmutableMapValue<String, String> lastTimeGymChallenged();//gymname, datetime
    ImmutableListValue<ItemStackSnapshot> badgeItems();
    ImmutableValue<Boolean> scoreboardEnabled();

}
