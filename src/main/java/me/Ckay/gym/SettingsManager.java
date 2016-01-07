package me.Ckay.gym;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SettingsManager {

    static SettingsManager instance = new SettingsManager();
    FileConfiguration data;
    File dfile;
    FileConfiguration badges;
    File bfile;
    FileConfiguration logs;
    File lfile;

    public static SettingsManager getInstance() {
        return SettingsManager.instance;
    }

    public void setupBadges(Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        this.bfile = new File(p.getDataFolder(), "badges.yml");
        if (!this.bfile.exists()) {
            try {
                this.bfile.createNewFile();
            } catch (IOException ioexception) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create badges.yml!");
            }
        }

        this.badges = YamlConfiguration.loadConfiguration(this.bfile);
    }

    public FileConfiguration getBadge() {
        return this.badges;
    }

    public void saveBadges() {
        try {
            this.badges.save(this.bfile);
        } catch (IOException ioexception) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save badges.yml!");
        }

    }

    public void reloadBadges() {
        this.badges = YamlConfiguration.loadConfiguration(this.bfile);
    }

    public void setup(Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        this.dfile = new File(p.getDataFolder(), "data.yml");
        if (!this.dfile.exists()) {
            try {
                this.dfile.createNewFile();
            } catch (IOException ioexception) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        }

        this.data = YamlConfiguration.loadConfiguration(this.dfile);
    }

    public FileConfiguration getData() {
        return this.data;
    }

    public void saveData() {
        try {
            this.data.save(this.dfile);
        } catch (IOException ioexception) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }

    }

    public void reloadData() {
        this.data = YamlConfiguration.loadConfiguration(this.dfile);
    }

    public void setupLog(Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        this.lfile = new File(p.getDataFolder(), "logs.yml");
        if (!this.lfile.exists()) {
            try {
                this.lfile.createNewFile();
            } catch (IOException ioexception) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create logs.yml!");
            }
        }

        this.logs = YamlConfiguration.loadConfiguration(this.lfile);
    }

    public FileConfiguration getLogs() {
        return this.logs;
    }

    public void saveLogs() {
        try {
            this.logs.save(this.lfile);
        } catch (IOException ioexception) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save logs.yml!");
        }

    }

    public void reloadLogs() {
        this.logs = YamlConfiguration.loadConfiguration(this.lfile);
    }
}
