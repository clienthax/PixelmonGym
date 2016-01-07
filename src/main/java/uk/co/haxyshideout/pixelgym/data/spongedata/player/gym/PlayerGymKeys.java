package uk.co.haxyshideout.pixelgym.data.spongedata.player.gym;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.ListValue;

public class PlayerGymKeys {
    public static final Key<ListValue<String>> GYMDATA = KeyFactory.makeListKey(String.class, DataQuery.of("GYMDATA"));
}
