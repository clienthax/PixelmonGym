package uk.co.haxyshideout.pixelgym.config.serializers;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import uk.co.haxyshideout.pixelgym.data.WarpEntry;

import java.util.UUID;

public class WarpEntryTypeSerializer implements TypeSerializer<WarpEntry> {

    @Override
    public WarpEntry deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        WarpEntry warpEntry = new WarpEntry();
        warpEntry.setWorldUUID(value.getNode("worldUUID").getValue(new TypeToken<UUID>() {}));
        warpEntry.setPosX(value.getNode("posX").getDouble());
        warpEntry.setPosY(value.getNode("posY").getDouble());
        warpEntry.setPosZ(value.getNode("posZ").getDouble());
        warpEntry.setPitch(value.getNode("pitch").getDouble());
        warpEntry.setYaw(value.getNode("yaw").getDouble());
        return warpEntry;
    }

    @Override
    public void serialize(TypeToken<?> type, WarpEntry obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("worldUUID").setValue(new TypeToken<UUID>() {}, obj.getWorldUUID());
        value.getNode("posX").setValue(obj.getPosX());
        value.getNode("posY").setValue(obj.getPosY());
        value.getNode("posZ").setValue(obj.getPosZ());
        value.getNode("pitch").setValue(obj.getPitch());
        value.getNode("yaw").setValue(obj.getYaw());
    }

}
