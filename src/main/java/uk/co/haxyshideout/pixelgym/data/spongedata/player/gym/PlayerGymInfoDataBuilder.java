package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.BADGE_ITEMS;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.GYMS_DEFEATED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.LAST_TIME_CHALLANGED;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoKeys.SCOREBOARD_ENABLED;

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
        if(!container.contains(GYMS_DEFEATED.getQuery()) && !container.contains(LAST_TIME_CHALLANGED.getQuery()) && !container.contains(BADGE_ITEMS.getQuery()) && !container.contains(SCOREBOARD_ENABLED.getQuery())) {
            return Optional.empty();
        }
        return create().from(container.getContainer());
    }
}
