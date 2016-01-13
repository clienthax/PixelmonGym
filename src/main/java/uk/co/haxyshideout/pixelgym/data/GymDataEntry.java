package uk.co.haxyshideout.pixelgym.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.common.data.util.DataQueries;

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
    private String gymName;
    private boolean enabled;
    private List<String> rules;
    private List<GymPokemonEntry> gymPokemon;
    private List<UUID> gymLeaders = Collections.singletonList(UUID.fromString("00000000-0000-0000-0000-000000000000"));//Dummy value so the config looks sane
    private TextColor gymColour;
    private ItemType badgeItemType;
    private int badgeItemDamageValue;
    private int levelCap;

    /**
     * Optional things from config
     */
    private Optional<Integer> entryFee = Optional.empty();
    private Optional<Integer> cooldownTime = Optional.empty();//in minutes
    private Optional<List<String>> previousGymNames = Optional.empty();
    private Optional<WarpEntry> insideWarp = Optional.empty();
    private Optional<WarpEntry> outsideWarp = Optional.empty();

    /**
     * Non config related vars
     */
    private boolean currentlyOpen = false;
    private List<UUID> onlineLeaders = Lists.newArrayList();
    private List<UUID> playerQueue = Lists.newArrayList();

    //need something in here for player/npc gym

    public GymDataEntry() {
    }

    public ItemStackSnapshot makeBadge(Text leaderName) {
        ItemStack itemStack = ItemStack.builder().fromContainer(ItemStack.builder().itemType(getBadgeItemType()).quantity(1).build().toContainer().set(DataQueries.ITEM_DAMAGE_VALUE, getBadgeItemDamageValue())).build();
        itemStack.offer(Keys.DISPLAY_NAME, getFormattedBadgeName());
        List<Text> lore = Lists.newArrayList();
        lore.add(Text.of(TextColors.GOLD, "Date/Time Won: ", TextColors.GREEN, ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME)));
        lore.add(Text.of(TextColors.GOLD, "Gym Leader: ",TextColors.GREEN, leaderName));
        itemStack.offer(Keys.ITEM_LORE, lore);
        return itemStack.createSnapshot();
    }

    public void sendRules(Player challenger) {
        challenger.sendMessage(Text.of(TextColors.GOLD, challenger.getName()+", Make sure you read the rules before facing the ", getFormattedGymName()));
        for(String rule : getRules()) {
            challenger.sendMessage(Text.of(TextColors.AQUA, rule));
        }
    }

    /**
     * Returns the coloured gym name in the format "Rock Gym"
     * @return the formatted gym name
     */
    public Text getFormattedGymName() {
        return Text.of(getColour(), TextStyles.BOLD, getName(), " Gym", TextColors.RESET);
    }

    /**
     * Returns the coloured badge name in the format "Rock Badge"
     * @return the formatted badge name
     */
    public Text getFormattedBadgeName() {
        return Text.of(getColour(), TextStyles.BOLD, getName(), " Badge", TextColors.RESET);
    }

    public Optional<WarpEntry> getInsideWarp() {
        return insideWarp;
    }

    public Optional<WarpEntry> getOutsideWarp() {
        return outsideWarp;
    }

    /**
     * GymUtils method for setting the warp from a players current location and rotation
     * @param player the player to set from
     */
    public void setInsideWarp(Player player) {
        insideWarp = Optional.of(new WarpEntry(player));
    }

    public void setOutsideWarp(Player player) {
        outsideWarp = Optional.of(new WarpEntry(player));
    }

    public void setInsideWarp(WarpEntry warpEntry) {
        insideWarp = Optional.ofNullable(warpEntry);
    }

    public void setOutsideWarp(WarpEntry warpEntry) {
        outsideWarp = Optional.ofNullable(warpEntry);
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

    public Optional<List<String>> getDependsOnCompletingNames() {
        return previousGymNames;
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

    public void setBadgeItemDamageValue(int badgeItemDamageValue) {
        this.badgeItemDamageValue = badgeItemDamageValue;
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

    public void setCoolDown(int coolDown) {
        this.cooldownTime = Optional.of(coolDown);
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = Optional.of(entryFee);
    }

    public void setPreviousGymNames(List<String> previousGymNames) {
        this.previousGymNames = Optional.of(previousGymNames);
    }

    public int getBadgeItemDamageValue() {
        return badgeItemDamageValue;
    }

    public boolean removePlayerFromQueue(UUID uniqueId) {
        return playerQueue.remove(uniqueId);
    }

    public void addLeader(UUID uniqueId) {
        gymLeaders.add(uniqueId);
    }

    public void removeLeader(UUID uniqueId) {
        gymLeaders.remove(uniqueId);
    }
}
