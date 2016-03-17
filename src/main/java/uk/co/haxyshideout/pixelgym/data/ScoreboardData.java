package uk.co.haxyshideout.pixelgym.data;

import com.google.common.collect.Lists;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

import java.util.List;

public class ScoreboardData {

    /**
     * Called when the last gym leader for any gym leaves causing the gym to close
     */
    public static void refreshAllScoreboards() {
        if(!PixelGymConfig.getInstance().scoreboardEnabled()) {
            return;
        }
        Sponge.getServer().getOnlinePlayers().forEach(ScoreboardData::refreshScoreboard);
    }

    /**
     * Called when a player toggles their scoreboard with /gym scoreboard and when the player logs into the server
     */
    public static void refreshPlayerScoreboard(Player player) {
        if(!PixelGymConfig.getInstance().scoreboardEnabled()) {
            return;
        }
        refreshScoreboard(player);
    }

    private static void refreshScoreboard(Player player) {
        PlayerGymInfoData playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        if (!playerGymInfoData.scoreboardEnabled().get()) {
            return;
        }
        List<String> defeatedGyms = playerGymInfoData.gymsDefeated().get();

        Scoreboard scoreboard = Scoreboard.builder().build();
        Objective openGyms = Objective.builder().name("pixelgym").displayName(Text.of("Open Gyms")).criterion(Criteria.DUMMY).build();

        List<Text> lines = Lists.newArrayListWithCapacity(15);
        int openGymAmount = 0;
        for (GymDataEntry gymDataEntry : GymData.getInstance().getGymData()) {
            if(gymDataEntry.isCurrentlyOpen() && !defeatedGyms.contains(gymDataEntry.getName())) {
                lines.add(gymDataEntry.getFormattedGymName());
                openGymAmount++;
            }
            if(openGymAmount == 15) {
                break;
            }
        }

        for(int i = lines.size(); i > 0; i--) {
            openGyms.getOrCreateScore(lines.get(lines.size()-i)).setScore(i);
        }

        scoreboard.addObjective(openGyms);
        scoreboard.updateDisplaySlot(openGyms, DisplaySlots.SIDEBAR);

        player.setScoreboard(scoreboard);
    }

}
