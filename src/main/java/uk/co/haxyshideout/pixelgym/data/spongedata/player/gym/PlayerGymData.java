package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import static com.google.common.base.Preconditions.checkNotNull;
import static uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymKeys.GYMDATA;

import com.google.common.collect.Lists;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.collection.AbstractSingleListData;
import org.spongepowered.api.data.merge.MergeFunction;

import java.util.List;
import java.util.Optional;

public class PlayerGymData extends AbstractSingleListData<String, PlayerGymData, ImmutablePlayerGymData> {

    public PlayerGymData() {
        this(Lists.newArrayList());
    }

    public PlayerGymData(List<String> value) {
        super(value, GYMDATA);
    }

    @Override
    public Optional<PlayerGymData> fill(DataHolder dataHolder, MergeFunction overlap) {
        PlayerGymData gymData = checkNotNull(overlap).merge(copy(), from(dataHolder.toContainer()).orElse(null));
        return Optional.of(set(GYMDATA, gymData.get(GYMDATA).get()));
    }

    @Override
    public Optional<PlayerGymData> from(DataContainer container) {
        if(container.contains(GYMDATA.getQuery())) {
            return Optional.of(set(GYMDATA, container.getStringList(GYMDATA.getQuery()).orElse(null)));
        }
        return Optional.empty();
    }

    @Override
    public PlayerGymData copy() {
        return new PlayerGymData(this.getValue());
    }

    @Override
    public ImmutablePlayerGymData asImmutable() {
        return new ImmutablePlayerGymData(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(GYMDATA.getQuery(), this.getValue());
    }

}
