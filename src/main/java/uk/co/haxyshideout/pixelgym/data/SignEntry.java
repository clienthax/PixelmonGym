package uk.co.haxyshideout.pixelgym.data;

import java.util.UUID;

public class SignEntry {

    private UUID worldUUID;
    private double posX;
    private double posY;
    private double posZ;
    private String signType;//first line on create

    public SignEntry(UUID worldUUID, double posX, double posY, double posZ, String signType) {
        this.worldUUID = worldUUID;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.signType = signType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
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

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }
}
