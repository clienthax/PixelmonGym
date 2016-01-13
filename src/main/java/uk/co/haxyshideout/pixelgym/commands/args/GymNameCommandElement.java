package uk.co.haxyshideout.pixelgym.commands.args;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.PatternMatchingCommandElement;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.data.GymData;

import javax.annotation.Nullable;

public class GymNameCommandElement extends PatternMatchingCommandElement {

    public GymNameCommandElement(@Nullable Text key) {
        super(key);
    }

    @Override
    protected Iterable<String> getChoices(CommandSource source) {
        return GymData.getInstance().getGymNames();
    }

    @Override
    protected Object getValue(String choice) throws IllegalArgumentException {
        return choice;
    }

}
