package uk.co.haxyshideout.pixelgym.data;

import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class GymData {

    String gymName;
    Location<World> gymLocation;
    ArrayList<UUID> playerQueue;
    HashMap<UUID, Integer> playerCooldowns;
    HashMap<UUID, Task> cooldownTask;//wtf is this even for
    Optional<GymData> dependsOnCompleting;
    ArrayList<GymPokemonEntry> gymPokemon;
    ArrayList<String> rules;
    Optional<Integer> entryFee;
    Optional<Integer> cooldownTime;
    String rewardItemName;
    Integer levelCap;
    //need something in here for player/npc gym
    Optional<UUID> gymLeader;

}
