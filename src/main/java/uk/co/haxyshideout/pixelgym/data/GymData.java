package uk.co.haxyshideout.pixelgym.data;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GymData {

    private final static GymData INSTANCE = new GymData();
    private List<GymDataEntry> gymDatas;

    public void setGymData(List<GymDataEntry> gymDatas) {
        this.gymDatas = gymDatas;
    }

    public List<GymDataEntry> getGymData() {
        return this.gymDatas;
    }

    public Optional<GymDataEntry> getGymData(String gymName) {
        for (GymDataEntry gymData : gymDatas) {
            if(gymData.getName().equals(gymName))
                return Optional.of(gymData);
        }
        return Optional.empty();
    }

    public static GymData getInstance() {
        return INSTANCE;
    }

    public List<GymDataEntry> getGymsForLeader(UUID uniqueId) {
        return gymDatas.stream().filter(gymData -> gymData.getGymLeaders().contains(uniqueId)).collect(Collectors.toList());
    }

    public List<String> getGymNames() {
        List<String> gymNames = Lists.newArrayList();
        gymNames.addAll(gymDatas.stream().map(GymDataEntry::getName).collect(Collectors.toList()));
        return gymNames;
    }
}
