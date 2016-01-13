package uk.co.haxyshideout.pixelgym.config;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import uk.co.haxyshideout.pixelgym.PixelGym;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.ScoreboardData;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class PixelGymConfig {

    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private CommentedConfigurationNode rootNode;
    private static PixelGymConfig INSTANCE;

    public PixelGymConfig(ConfigurationLoader<CommentedConfigurationNode> configurationLoader) {
        this.configurationLoader = configurationLoader;
        INSTANCE = this;
        loadConfig();
    }

    public void loadConfig() {
        try {
            rootNode = configurationLoader.load();
            loadGyms();
            saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            saveGyms();
            configurationLoader.save(rootNode);
            rootNode = configurationLoader.load();
            loadGyms();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveGyms() {
        try {
            rootNode.getNode("Gyms").setValue(new TypeToken<List<GymDataEntry>>() {}, GymData.getInstance().getGymData());
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    private void loadGyms() {
        try {
            GymData.getInstance().setGymData(rootNode.getNode("Gyms").getList(new TypeToken<GymDataEntry>() {}));
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public boolean showJoinMessages() {
        return rootNode.getNode("General", "EnableJoinMessages").getBoolean();
    }

    /**
     * If this is enabled we need to move all the players party pokemon into their pc and then give them the gym pokemon
     * @return
     */
    public boolean enforceGymPokemon() {
        return rootNode.getNode("General", "EnforceGymPokemon").getBoolean();
    }

    public List<String> getJoinMessages() {
        try {
            return rootNode.getNode("General", "JoinMessages").getList(new TypeToken<String>() {});
        } catch (ObjectMappingException e) {
            throw new IllegalStateException("Invalid content in JoinMessages node");
        }
    }

    public boolean leadersCanHealOwnPokemon() {
        return rootNode.getNode("General", "LeadersCanHealOwnPokemon").getBoolean();
    }

    public boolean scoreboardEnabled() {
        return rootNode.getNode("General", "ScoreboardEnabled").getBoolean();
    }

    public static PixelGymConfig getInstance() {
        return INSTANCE;
    }

}
