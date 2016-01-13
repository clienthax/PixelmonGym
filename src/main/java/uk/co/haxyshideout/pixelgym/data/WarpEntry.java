package uk.co.haxyshideout.pixelgym.data;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

public class WarpEntry {

    private UUID worldUUID;
    private double posX;
    private double posY;
    private double posZ;
    private double pitch = 0;
    private double yaw = 0;

    public WarpEntry(Player player) {
        Location<World> worldLocation = player.getLocation();
        Vector3d rotation = player.getRotation();
        this.worldUUID = worldLocation.getExtent().getUniqueId();
        this.posX = worldLocation.getX();
        this.posY = worldLocation.getY();
        this.posZ = worldLocation.getZ();
        this.pitch = rotation.getX();
        this.yaw = rotation.getY();
    }

    public WarpEntry() {

    }

    /**
     * Returns the optional value of the world location if successful
     */
    public Optional<Location<World>> getLocation() {
        Optional<World> world = Sponge.getServer().getWorld(this.worldUUID);
        if(world.isPresent()) {
            return Optional.of(new Location<>(world.get(), this.posX, this.posY, this.posZ));
        }
        return Optional.empty();
    }

    public Vector3d getRotation() {
        return new Vector3d(this.pitch, this.yaw, 0);
    }

    public void setRotation(Vector3d vector3d) {
        pitch = vector3d.getX();
        yaw = vector3d.getY();
    }

    public void attemptWarp(Player playerToWarp) {
        Optional<Location<World>> location = getLocation();
        if(location.isPresent()) {
            playerToWarp.setLocation(location.get());
            playerToWarp.setRotation(getRotation());
        }
    }

    //TODO util method to tp player if the location exists :D

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }
}
