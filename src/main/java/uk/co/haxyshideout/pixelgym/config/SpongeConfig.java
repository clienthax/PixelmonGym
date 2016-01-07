package uk.co.haxyshideout.pixelgym.config;

import com.google.common.collect.Lists;
import uk.co.haxyshideout.pixelgym.data.GymData;

import java.util.List;

public class SpongeConfig {

    public void loadConfig() {

    }

    public List<String> getJoinMessages() {
        return null;
    }

    public boolean gymHealingEnabled() {
        return true;
    }

    public boolean scoreboardEnabled() {
        return false;
    }

    public List<GymData> getGyms() {
        return Lists.newArrayList();
    }

}
