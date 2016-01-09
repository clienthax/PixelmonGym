package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymKeys.GYMDATA;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.collection.AbstractImmutableSingleListData;
import org.spongepowered.api.data.value.BaseValue;

import java.util.List;
import java.util.Optional;

public class ImmutablePlayerGymData extends AbstractImmutableSingleListData<String, ImmutablePlayerGymData, PlayerGymData> {

    protected ImmutablePlayerGymData(List<String> value) {
        super(value, GYMDATA);
    }

    @Override public <E> Optional<ImmutablePlayerGymData> with(Key<? extends BaseValue<E>> key, E value) {
        if(this.supports(key)) {
            return Optional.of(asMutable().set(key, value).asImmutable());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public PlayerGymData asMutable() {
        return new PlayerGymData(this.getValue());
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(GYMDATA.getQuery(), this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }
}
