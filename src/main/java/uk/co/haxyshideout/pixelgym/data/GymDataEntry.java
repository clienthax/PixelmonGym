package uk.co.haxyshideout.pixelgym.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GymDataEntry {

    /**
     * /Required things from config
     */
    String gymName;
    boolean enabled;
    List<String> rules;
    List<GymPokemonEntry> gymPokemon;
    List<UUID> gymLeaders = Collections.singletonList(UUID.fromString("00000000-0000-0000-0000-000000000000"));//Dummy value so the config looks sane
    TextColor gymColour;
    ItemType badgeItemType;
    int levelCap;
    //TODO warp locations for battle start / exit - store as xyz/worlduuid, to avoid world refs use optional

    /**
     * Optional things from config
     */
    Optional<Integer> entryFee = Optional.empty();
    Optional<Integer> cooldownTime = Optional.empty();//in minutes
    Optional<String> previousGymName = Optional.empty();

    /**
     * Non config related vars
     */
    boolean currentlyOpen = true;//TODO set back to false after tests
    List<UUID> onlineLeaders = Lists.newArrayList();
    List<UUID> playerQueue = Lists.newArrayList();

    //need something in here for player/npc gym

    public GymDataEntry() {
    }

    public ItemStackSnapshot makeBadge(Text leaderName) {
        ItemStack itemStack = ItemStack.builder().itemType(ItemTypes.POTATO).quantity(1).build();
        itemStack.offer(Keys.DISPLAY_NAME, Text.of(getColour(), TextStyles.BOLD, getName()+" Badge"));
        List<Text> lore = Lists.newArrayList();
        lore.add(Text.of(TextColors.GOLD, "Date/Time Won: ", TextColors.GREEN, ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME)));
        lore.add(Text.of(TextColors.GOLD, "Gym Leader: ",TextColors.GREEN, leaderName));
        itemStack.offer(Keys.ITEM_LORE, lore);
        return itemStack.createSnapshot();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return gymName;
    }

    public List<UUID> getPlayerQueue() {
        return playerQueue;
    }

    public ImmutableList<UUID> getOnlineLeaders() {
        return ImmutableList.copyOf(onlineLeaders);
    }

    public void removeOnlineLeader(UUID uuid) {
        onlineLeaders.remove(uuid);
        if(onlineLeaders.isEmpty()) {
            currentlyOpen = false;
            ScoreboardData.refreshAllScoreboards();
        }
    }

    public void addOnlineLeader(UUID uuid) {
        onlineLeaders.add(uuid);
    }

    public boolean isCurrentlyOpen() {
        return currentlyOpen;
    }

    public void setCurrentlyOpen(boolean open) {
        currentlyOpen = open;
    }//TODO show title showing gym is open if player hasnt challenged it

    public int getLevelCap() {
        return levelCap;
    }

    public List<String> getRules() {
        return rules;
    }

    public void addPlayerToQueue(UUID uuid) {
        playerQueue.add(uuid);
    }

    public Optional<Integer> getPositionInQueue(UUID playerUUID) {
        int pos = 0;
        for (UUID uuid1 : playerQueue) {
            if(playerUUID.equals(uuid1))
                return Optional.of(pos);
            pos++;
        }
        return Optional.empty();
    }

    public Optional<GymDataEntry> getDependsOnCompletingData() {
        if(previousGymName.isPresent()) {
            return GymData.getInstance().getGymData(previousGymName.get());
        }
        return Optional.empty();
    }

    public Optional<String> getDependsOnCompletingName() {
        if(previousGymName.isPresent())
            return Optional.of(previousGymName.get());
        else
            return Optional.empty();
    }

    public void setGymPokemon(List<GymPokemonEntry> gymPokemon) {
        this.gymPokemon = gymPokemon;
    }

    public void setName(String name) {
        this.gymName = name;
    }

    public void setGymColour(TextColor gymColour) {
        this.gymColour = gymColour;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setBadgeItemType(ItemType badgeItemType) {
        this.badgeItemType = badgeItemType;
    }

    public void setLevelCap(int levelCap) {
        this.levelCap = levelCap;
    }

    public TextColor getColour() {
        return gymColour;
    }

    public ItemType getBadgeItemType() {
        return badgeItemType;
    }

    public List<GymPokemonEntry> getGymPokemon() {
        return gymPokemon;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public List<UUID> getGymLeaders() {
        return gymLeaders;
    }

    public void setGymLeaders(List<UUID> gymLeaders) {
        this.gymLeaders = gymLeaders;
    }

    public Optional<Integer> getCoolDownTime() {
        return cooldownTime;
    }

    public Optional<Integer> getEntryFee() {
        return entryFee;
    }

    public void setPreviousGym(GymDataEntry previousGym) {
        this.previousGymName = Optional.of(previousGym.getName());
    }

    public void setCoolDown(int coolDown) {
        this.cooldownTime = Optional.of(coolDown);
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = Optional.of(entryFee);
    }

    public void setPreviousGymName(String previousGymName) {
        this.previousGymName = Optional.of(previousGymName);
    }

}
