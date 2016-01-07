package me.Ckay.gym;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListeners implements Listener {

    public boolean setStatusSign(Location l, String gym) {
        File directory = PixelGym.get().getDataFolder();

        if (!directory.exists() && !directory.mkdirs()) {
            return false;
        } else {
            File storage = new File(directory, "signs.yml");

            try {
                if (!storage.exists() && !storage.createNewFile()) {
                    return false;
                }
            } catch (IOException ioexception) {
                return false;
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);
            Object list = config.getStringList("status");

            if (list == null) {
                list = new ArrayList();
            }

            ((List) list).add(l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + ", " + l.getWorld().getName() + ", " + gym);
            config.set("status", list);

            try {
                config.save(storage);
            } catch (IOException ioexception1) {
                ioexception1.printStackTrace();
                return false;
            }

            PixelGym.get().updateSigns();
            return true;
        }
    }

    public boolean setLeaderSign(Location l, String gym, int number) {
        File directory = PixelGym.get().getDataFolder();

        if (!directory.exists() && !directory.mkdirs()) {
            return false;
        } else {
            File storage = new File(directory, "signs.yml");

            try {
                if (!storage.exists() && !storage.createNewFile()) {
                    return false;
                }
            } catch (IOException ioexception) {
                return false;
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);
            Object list = config.getStringList("leader");

            if (list == null) {
                list = new ArrayList();
            }

            ((List) list).add(l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + ", " + l.getWorld().getName() + ", " + gym + ", " + number);
            config.set("leader", list);

            try {
                config.save(storage);
            } catch (IOException ioexception1) {
                ioexception1.printStackTrace();
                return false;
            }

            PixelGym.get().updateSigns();
            return true;
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();

        if (e.getLine(0).equalsIgnoreCase("[GymStatus]") && p.hasPermission("pixelgym.admin")) {
            if (e.getLine(1).startsWith("gym")) {
                String ex = e.getLine(1).replace("gym", "");
                int gymNumber = 0;

                try {
                    gymNumber = Integer.parseInt(ex);
                } catch (NumberFormatException numberformatexception) {
                    p.sendMessage("Use \"gym + number\" on line 2!");
                }

                this.setStatusSign(e.getBlock().getLocation(), Integer.toString(gymNumber));
            }
        } else if (e.getLine(0).equalsIgnoreCase("[GymLeaders]") && p.hasPermission("pixelgym.admin")) {
            this.setLeaderSign(e.getBlock().getLocation(), e.getLine(1), 1);
        } else if (e.getLine(0).startsWith("[GymLeaders") && e.getLine(0).endsWith("]") && p.hasPermission("pixelgym.admin")) {
            try {
                this.setLeaderSign(e.getBlock().getLocation(), e.getLine(1), Integer.parseInt(e.getLine(0).replace("[GymLeaders", "").replace("]", "")));
            } catch (NumberFormatException numberformatexception1) {
                p.sendMessage(ChatColor.RED + "Use [GymLeaders + number] on line 1!");
            }
        }

    }
}
