package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.List;
import java.util.Optional;

public class PlayerGymDataBuilder implements DataManipulatorBuilder<PlayerGymData, ImmutablePlayerGymData> {

    @Override
    public PlayerGymData create() {
        return new PlayerGymData();
    }

    @Override
    public Optional<PlayerGymData> createFrom(DataHolder dataHolder) {
        return create().fill(dataHolder);
    }

    @Override
    public Optional<PlayerGymData> build(DataView container) throws InvalidDataException {
        if(!container.contains(PlayerGymKeys.GYMDATA.getQuery())) {
            return Optional.empty();
        }
        PlayerGymData gymData = create();
        gymData = gymData.setValue(container.getStringList(PlayerGymKeys.GYMDATA.getQuery()).get());
        return Optional.of(gymData);
    }
}
