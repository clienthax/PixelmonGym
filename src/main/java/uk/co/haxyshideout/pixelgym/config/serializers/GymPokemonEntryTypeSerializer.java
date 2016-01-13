package uk.co.haxyshideout.pixelgym.config.serializers;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import uk.co.haxyshideout.pixelgym.data.GymPokemonEntry;

import java.util.List;

public class GymPokemonEntryTypeSerializer implements TypeSerializer<GymPokemonEntry> {

    @Override
    public GymPokemonEntry deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        GymPokemonEntry gymPokemonEntry = new GymPokemonEntry();
        gymPokemonEntry.setPokemonName(value.getNode("Name").getString());
        gymPokemonEntry.setPokemonLevel(Math.max(1, value.getNode("Level").getInt()));
        gymPokemonEntry.setMoveList(value.getNode("Moves").getList(new TypeToken<String>() {}));
        return gymPokemonEntry;
    }

    @Override
    public void serialize(TypeToken<?> type, GymPokemonEntry obj, ConfigurationNode value) throws ObjectMappingException {
        if(obj.getPokemonName().equals("")) {
            System.out.println("Someone passed missing data into GymPokemonEntryTypeSerializer, not writing");
            return;
        }
        value.getNode("Name").setValue(obj.getPokemonName());
        value.getNode("Level").setValue(obj.getLevel());
        value.getNode("Moves").setValue(new TypeToken<List<String>>() {}, obj.getMoveList());
    }
}
