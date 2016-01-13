package uk.co.haxyshideout.pixelgym.utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import uk.co.haxyshideout.pixelgym.data.GymData;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GymUtils {

    public static Optional<Integer> getMinutesSinceLastChallenge(Player player, String gymName) {
        PlayerGymInfoData playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        Map<String,String> lastTimeGymChallenged = playerGymInfoData.lastTimeGymChallenged().get();
        String ztimeData = lastTimeGymChallenged.get(gymName);
        if(ztimeData == null) {
            return Optional.empty();
        }
        ZonedDateTime lastChallengeTime = ZonedDateTime.parse(ztimeData, DateTimeFormatter.RFC_1123_DATE_TIME);
        ZonedDateTime now = ZonedDateTime.now();
        int between = (int) ChronoUnit.MINUTES.between(lastChallengeTime, now);
        return Optional.of(between);
    }

    public static Optional<Integer> getRemainingCooldownMinutes(PlayerGymInfoData playerGymInfoData, String gymName) {
        Optional<GymDataEntry> gymData = GymData.getInstance().getGymData(gymName);
        if(!gymData.isPresent()) {
            return Optional.empty();
        }
        Optional<Integer> cooldown = gymData.get().getCoolDownTime();
        if(!cooldown.isPresent()) {
            return Optional.empty();//If the gym doesn't have a cooldown return 0
        }
        Map<String,String> lastTimeGymChallenged = playerGymInfoData.lastTimeGymChallenged().get();
        String ztimeData = lastTimeGymChallenged.get(gymName);
        if(ztimeData == null) {
            return Optional.empty();//If the player hasnt challenged the gym before return 0
        }
        ZonedDateTime lastChallengeTime = ZonedDateTime.parse(ztimeData, DateTimeFormatter.RFC_1123_DATE_TIME);
        ZonedDateTime now = ZonedDateTime.now();

        int between = (int) ChronoUnit.MINUTES.between(lastChallengeTime, now);
        int remainingMins = Math.max(0, cooldown.get() - between);
        if(remainingMins == 0) {
            return Optional.empty();//No cooldown if 0
        }
        return Optional.of(remainingMins);//Avoid negatives
    }

    public static void updateChallengedTime(Player player, String gymName) {
        PlayerGymInfoData playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        Map<String, String> gymTimeMap = playerGymInfoData.lastTimeGymChallenged().get();
        gymTimeMap.put(gymName, ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        playerGymInfoData.setLastTimeGymChallenged(gymTimeMap);
        player.offer(playerGymInfoData);
        //TODO logging
    }

    public static void awardBadge(Player player, GymDataEntry gymDataEntry, String leaderName) {
        PlayerGymInfoData playerGymInfoData = player.get(PlayerGymInfoData.class).get();
        List<String> gymsDefeated = playerGymInfoData.gymsDefeated().get();
        gymsDefeated.add(gymDataEntry.getName());
        playerGymInfoData.setGymsDefeated(gymsDefeated);
        List<ItemStackSnapshot> badgeItemStackSnapshots = playerGymInfoData.badgeItems().get();
        badgeItemStackSnapshots.add(gymDataEntry.makeBadge(Text.of(leaderName)));
        playerGymInfoData.setBadgeItems(badgeItemStackSnapshots);
        player.offer(playerGymInfoData);
    }
}
