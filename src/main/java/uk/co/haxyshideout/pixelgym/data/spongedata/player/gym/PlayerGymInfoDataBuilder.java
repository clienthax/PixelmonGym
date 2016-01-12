package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.BADGE_ITEMS;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.GYMS_DEFEATED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.LAST_TIME_CHALLANGED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.SCOREBOARD_ENABLED;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.Map;
import java.util.Optional;

public class PlayerGymInfoDataBuilder implements DataManipulatorBuilder<PlayerGymInfoData, ImmutablePlayerGymInfoData> {

    @Override
    public PlayerGymInfoData create() {
        return new PlayerGymInfoData();
    }

    @Override
    public Optional<PlayerGymInfoData> createFrom(DataHolder dataHolder) {
        return create().fill(dataHolder);
    }

    @Override
    public Optional<PlayerGymInfoData> build(DataView container) throws InvalidDataException {
        PlayerGymInfoData playerGymInfoData = new PlayerGymInfoData();

        if(container.contains(GYMS_DEFEATED.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(GYMS_DEFEATED, container.getStringList(GYMS_DEFEATED.getQuery()).get());
        }
        if(container.contains(LAST_TIME_CHALLANGED.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(LAST_TIME_CHALLANGED, (Map<String, String>)container.getMap(LAST_TIME_CHALLANGED.getQuery()).get());
        }
        if(container.contains(BADGE_ITEMS.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(BADGE_ITEMS, container.getSerializableList(BADGE_ITEMS.getQuery(), ItemStackSnapshot.class).get());
        }
        if(container.contains(SCOREBOARD_ENABLED.getQuery())) {
            playerGymInfoData = playerGymInfoData.set(SCOREBOARD_ENABLED, container.getBoolean(SCOREBOARD_ENABLED.getQuery()).get());
        }
        if(!container.contains(GYMS_DEFEATED.getQuery()) && !container.contains(LAST_TIME_CHALLANGED.getQuery()) && !container.contains(BADGE_ITEMS.getQuery()) && !container.contains(SCOREBOARD_ENABLED.getQuery())) {
            return Optional.empty();
        }

        return Optional.of(playerGymInfoData);
    }

}
