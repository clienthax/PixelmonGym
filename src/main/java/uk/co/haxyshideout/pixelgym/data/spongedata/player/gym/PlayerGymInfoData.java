package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.BADGE_ITEMS;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.GYMS_DEFEATED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.LAST_TIME_CHALLANGED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.SCOREBOARD_ENABLED;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.interfaces.IPlayerGymInfoData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayerGymInfoData extends AbstractData<PlayerGymInfoData, ImmutablePlayerGymInfoData> implements IPlayerGymInfoData {

    /***
     * Needs to contain a list of gyms defeated, aswell as their defeated time + leader
     * Needs to also contain a list of data/times lost
     */

    public static final ValueFactory VALUEFACTORY = Sponge.getRegistry().getValueFactory();
    private List<String> gymsDefeated;
    private Map<String, String> lastTimeGymChallenged;
    private List<ItemStackSnapshot> badgeItems;
    private boolean scoreboardEnabled;

    public PlayerGymInfoData() {
        this(Lists.newArrayList(), Maps.newHashMap(), Lists.newArrayList(), true);
    }

    public PlayerGymInfoData(List<String> gymsDefeated, Map<String, String> lastTimeGymChallenged, List<ItemStackSnapshot> badgeItems, boolean scoreboardEnabled) {
        this.gymsDefeated = gymsDefeated;
        this.lastTimeGymChallenged = lastTimeGymChallenged;
        this.badgeItems = badgeItems;
        this.scoreboardEnabled = scoreboardEnabled;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(GYMS_DEFEATED, () -> this.gymsDefeated);
        registerFieldSetter(GYMS_DEFEATED, this::setGymsDefeated);
        registerKeyValue(GYMS_DEFEATED, this::gymsDefeated);

        registerFieldGetter(LAST_TIME_CHALLANGED, () -> this.lastTimeGymChallenged);
        registerFieldSetter(LAST_TIME_CHALLANGED, this::setLastTimeGymChallenged);
        registerKeyValue(LAST_TIME_CHALLANGED, this::lastTimeGymChallenged);

        registerFieldGetter(BADGE_ITEMS, () -> this.badgeItems);
        registerFieldSetter(BADGE_ITEMS, this::setBadgeItems);
        registerKeyValue(BADGE_ITEMS, this::badgeItems);

        registerFieldGetter(SCOREBOARD_ENABLED, () -> this.scoreboardEnabled);
        registerFieldSetter(SCOREBOARD_ENABLED, this::setScoreboardEnabled);
        registerKeyValue(SCOREBOARD_ENABLED, this::scoreboardEnabled);
    }

    public void setGymsDefeated(List<String> gymsDefeated) {
        this.gymsDefeated = gymsDefeated;
    }

    @Override
    public ListValue<String> gymsDefeated() {
        return VALUEFACTORY.createListValue(GYMS_DEFEATED, this.gymsDefeated);
    }

    public void setLastTimeGymChallenged(Map<String, String> lastTimeGymChallenged) {
        this.lastTimeGymChallenged = lastTimeGymChallenged;
    }

    @Override
    public MapValue<String, String> lastTimeGymChallenged() {
        return VALUEFACTORY.createMapValue(LAST_TIME_CHALLANGED, this.lastTimeGymChallenged);
    }

    public void setBadgeItems(List<ItemStackSnapshot> badgeItems) {
        this.badgeItems = badgeItems;
    }

    @Override
    public ListValue<ItemStackSnapshot> badgeItems() {
        return VALUEFACTORY.createListValue(BADGE_ITEMS, this.badgeItems);
    }

    public void setScoreboardEnabled(boolean enabled) {
        this.scoreboardEnabled = enabled;
    }

    @Override
    public Value<Boolean> scoreboardEnabled() {
        return VALUEFACTORY.createValue(SCOREBOARD_ENABLED, this.scoreboardEnabled);
    }

    @Override
    public Optional<PlayerGymInfoData> fill(DataHolder dataHolder, MergeFunction overlap) {
        PlayerGymInfoData playerGymInfoData = checkNotNull(checkNotNull(overlap).merge(copy(), from(dataHolder.toContainer()).orElse(null)));
        return Optional.of(playerGymInfoData
                .set(GYMS_DEFEATED, this.gymsDefeated)
                .set(LAST_TIME_CHALLANGED, this.lastTimeGymChallenged)
                .set(BADGE_ITEMS, this.badgeItems)
                .set(SCOREBOARD_ENABLED, this.scoreboardEnabled)
        );
    }

    @Override
    public Optional<PlayerGymInfoData> from(DataContainer container) {//TODO this shit is broken because .contains returns false when it should return true -.-
        PlayerGymInfoData playerGymInfoData = new PlayerGymInfoData();

        if(container.contains(GYMS_DEFEATED.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(GYMS_DEFEATED, container.getStringList(GYMS_DEFEATED.getQuery()).get());
        }
        if(container.contains(LAST_TIME_CHALLANGED.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(LAST_TIME_CHALLANGED, (Map<String, String>)container.getMap(LAST_TIME_CHALLANGED.getQuery()).get());
        }
        if(container.contains(BADGE_ITEMS.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(BADGE_ITEMS, (List<ItemStackSnapshot>)container.getList(BADGE_ITEMS.getQuery()).get());
        }
        if(container.contains(SCOREBOARD_ENABLED.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(SCOREBOARD_ENABLED, container.getBoolean(SCOREBOARD_ENABLED.getQuery()).get());
        }
        if(!container.contains(GYMS_DEFEATED.getQuery()) && !container.contains(LAST_TIME_CHALLANGED.getQuery()) && !container.contains(BADGE_ITEMS.getQuery()) && !container.contains(SCOREBOARD_ENABLED.getQuery())) {
            return Optional.empty();
        }

        return Optional.of(playerGymInfoData);
    }

    @Override
    public PlayerGymInfoData copy() {
        return new PlayerGymInfoData(this.gymsDefeated, this.lastTimeGymChallenged, this.badgeItems, this.scoreboardEnabled);
    }

    @Override
    public ImmutablePlayerGymInfoData asImmutable() {
        return new ImmutablePlayerGymInfoData(this.gymsDefeated, this.lastTimeGymChallenged, this.badgeItems, this.scoreboardEnabled);
    }

    @Override
    public int compareTo(PlayerGymInfoData o) {
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
