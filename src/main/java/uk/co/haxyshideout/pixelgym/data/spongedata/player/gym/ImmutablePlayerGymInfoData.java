package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import com.google.common.collect.ComparisonChain;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.immutable.ImmutableListValue;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.interfaces.IImmutablePlayerGymInfoData;

import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.BADGE_ITEMS;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.GYMS_DEFEATED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.LAST_TIME_CHALLANGED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.SCOREBOARD_ENABLED;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ImmutablePlayerGymInfoData extends AbstractImmutableData<ImmutablePlayerGymInfoData,PlayerGymInfoData> implements IImmutablePlayerGymInfoData {

    public static final ValueFactory VALUEFACTORY = Sponge.getRegistry().getValueFactory();

    private final List<String> gymsDefeated;
    private final Map<String, String> lastTimeGymChallenged;
    private final List<ItemStackSnapshot> badgeItems;
    private final boolean scoreboardEnabled;

    ImmutableListValue<String> gymsDefeatedValue;
    ImmutableMapValue<String, String> lastTimeGymChallengedValue;
    ImmutableListValue<ItemStackSnapshot> badgeItemsValue;
    ImmutableValue<Boolean> scoreboardEnabledValue;

    public ImmutablePlayerGymInfoData(List<String> gymsDefeated, Map<String, String> lastTimeGymChallenged, List<ItemStackSnapshot> badgeItems, boolean scoreboardEnabled) {
        this.gymsDefeated = gymsDefeated;
        this.lastTimeGymChallenged = lastTimeGymChallenged;
        this.badgeItems = badgeItems;
        this.scoreboardEnabled = scoreboardEnabled;
        this.gymsDefeatedValue = VALUEFACTORY.createListValue(GYMS_DEFEATED, gymsDefeated).asImmutable();
        this.lastTimeGymChallengedValue = VALUEFACTORY.createMapValue(LAST_TIME_CHALLANGED, lastTimeGymChallenged).asImmutable();
        this.badgeItemsValue = VALUEFACTORY.createListValue(BADGE_ITEMS, badgeItems).asImmutable();
        this.scoreboardEnabledValue = VALUEFACTORY.createValue(SCOREBOARD_ENABLED, scoreboardEnabled).asImmutable();
        registerGetters();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(GYMS_DEFEATED, () -> this.gymsDefeated);
        registerKeyValue(GYMS_DEFEATED, this::gymsDefeated);

        registerFieldGetter(LAST_TIME_CHALLANGED, () -> this.lastTimeGymChallenged);
        registerKeyValue(LAST_TIME_CHALLANGED, this::lastTimeGymChallenged);

        registerFieldGetter(BADGE_ITEMS, () -> this.badgeItems);
        registerKeyValue(BADGE_ITEMS, this::badgeItems);

        registerFieldGetter(SCOREBOARD_ENABLED, () -> this.scoreboardEnabled);
        registerKeyValue(SCOREBOARD_ENABLED, this::scoreboardEnabled);
    }

    @Override
    public ImmutableListValue<String> gymsDefeated() {
        return gymsDefeatedValue;
    }

    @Override
    public ImmutableMapValue<String, String> lastTimeGymChallenged() {
        return lastTimeGymChallengedValue;
    }

    @Override
    public ImmutableListValue<ItemStackSnapshot> badgeItems() {
        return badgeItemsValue;
    }

    @Override
    public ImmutableValue<Boolean> scoreboardEnabled() {
        return scoreboardEnabledValue;
    }

    @Override
    public <E> Optional<ImmutablePlayerGymInfoData> with(Key<? extends BaseValue<E>> key, E value) {
        if(this.supports(key)) {
            return Optional.of(asMutable().set(key, value).asImmutable());
        }
        return Optional.empty();
    }

    @Override
    public PlayerGymInfoData asMutable() {
        return new PlayerGymInfoData(this.gymsDefeated, this.lastTimeGymChallenged, this.badgeItems, this.scoreboardEnabled);
    }

    @Override
    public int compareTo(ImmutablePlayerGymInfoData o) {
        return ComparisonChain.start()
                .compare(o.gymsDefeated.containsAll(this.gymsDefeated), this.gymsDefeated.containsAll(o.gymsDefeated))
                .compare(o.lastTimeGymChallenged.hashCode(), this.lastTimeGymChallenged.hashCode())
                .compare(o.badgeItems.containsAll(this.badgeItems), this.badgeItems.containsAll(o.badgeItems))
                .compare(o.scoreboardEnabled, this.scoreboardEnabled)
                .result();
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(GYMS_DEFEATED.getQuery(), this.gymsDefeated)
                .set(LAST_TIME_CHALLANGED.getQuery(), this.lastTimeGymChallenged)
                .set(BADGE_ITEMS.getQuery(), this.badgeItems)
                .set(SCOREBOARD_ENABLED.getQuery(), this.scoreboardEnabled);
    }

}
