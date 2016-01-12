package me.Ckay.gym;

import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Plugin(id = "pixelmongym", name = "PixelmonGym", version = "6.3")
public class PixelGym {

    FileConfiguration config;
    File cfile;
    File playersfile;
    FileConfiguration players;
    private static PixelGym instance;
    public final Logger logger = Logger.getLogger("Minecraft");
    public static PixelGym plugin;
    private Map queues<Integer, ArrayList>;
    private Map cooldownGym;
    private Map cooldownTime;
    private Map cooldownTask;
    public static Permission permission = null;
    public static Economy economy = null;
    public HashMap invsave = new HashMap();
    public final HashMap hashmap = new HashMap();
    Location gym1loc;
    Location iceloc;
    Location grassloc;
    Location waterloc;
    Location electricloc;
    Location fireloc;
    Location poisonloc;
    Location psychicloc;
    Location rockleaderloc;
    Location iceleaderloc;
    Location grassleaderloc;
    Location waterleaderloc;
    Location electricleaderloc;
    Location fireleaderloc;
    Location poisonleaderloc;
    Location psychicleaderloc;
    ScoreboardManager manager;
    Scoreboard board;
    Scoreboard clear;
    Objective none;
    Objective obj;
    Score gym1;
    Score gym2;
    Score gym3;
    Score gym4;
    Score gym5;
    Score gym6;
    Score gym7;
    Score gym8;
    Score gym9;
    Score gym10;
    Score gym11;
    Score gym12;
    Score gym13;
    Score gym14;
    Score gym15;
    Score gym16;
    Score gym17;
    Score gym18;
    Score gym19;
    Score gym20;
    Score gym21;
    Score gym22;
    Score gym23;
    Score gym24;
    Score gym25;
    Score gym26;
    Score gym27;
    Score gym28;
    Score gym29;
    Score gym30;
    Score gym31;
    Score gym32;
    Score e41;
    Score e42;
    Score e43;
    Score e44;
    Score leaders;
    Score queue;
    boolean spawnperm = false;
    boolean tpaperm = false;
    boolean homeperm = false;
    boolean backperm = false;
    boolean warpperm = false;
    boolean tpacceptperm = false;
    boolean randomtpperm = false;
    SettingsManager settings = SettingsManager.getInstance();
    public static Inventory myInventory;
    public static HashMap myInventories = new HashMap();
    public ArrayList hasOpen = new ArrayList();
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
    String enablegym1 = this.getConfig().getString("config.gym1enabled");
    String enable2 = this.getConfig().getString("config.gym2enabled");
    String enable3 = this.getConfig().getString("config.gym3enabled");
    String enable4 = this.getConfig().getString("config.gym4enabled");
    String enable5 = this.getConfig().getString("config.gym5enabled");
    String enable6 = this.getConfig().getString("config.gym6enabled");
    String enable7 = this.getConfig().getString("config.gym7enabled");
    String enable8 = this.getConfig().getString("config.gym8enabled");
    String enable9 = this.getConfig().getString("config.gym9enabled");
    String enablegym10 = this.getConfig().getString("config.gym10enabled");
    String enablegym11 = this.getConfig().getString("config.gym11enabled");
    String enablegym12 = this.getConfig().getString("config.gym12enabled");
    String enablegym13 = this.getConfig().getString("config.gym13enabled");
    String enablegym14 = this.getConfig().getString("config.gym14enabled");
    String enablegym15 = this.getConfig().getString("config.gym15enabled");
    String enablegym16 = this.getConfig().getString("config.gym16enabled");
    String enablegym17 = this.getConfig().getString("config.gym17enabled");
    String enablegym18 = this.getConfig().getString("config.gym18enabled");
    String enablegym19 = this.getConfig().getString("config.gym19enabled");
    String enable20 = this.getConfig().getString("config.gym20enabled");
    String enable21 = this.getConfig().getString("config.gym21enabled");
    String enable22 = this.getConfig().getString("config.gym22enabled");
    String enable23 = this.getConfig().getString("config.gym23enabled");
    String enable24 = this.getConfig().getString("config.gym24enabled");
    String enable25 = this.getConfig().getString("config.gym25enabled");
    String enable26 = this.getConfig().getString("config.gym26enabled");
    String enable27 = this.getConfig().getString("config.gym27enabled");
    String enable28 = this.getConfig().getString("config.gym28enabled");
    String enable29 = this.getConfig().getString("config.gym29enabled");
    String enable30 = this.getConfig().getString("config.gym30enabled");
    String enable31 = this.getConfig().getString("config.gym31enabled");
    String enable32 = this.getConfig().getString("config.gym32enabled");
    String enableGymHeal = this.getConfig().getString("config.gymhealing");
    String enablee4 = this.getConfig().getString("config.e4enabled");

    public void onEnable() {
        if (!this.setupEconomy()) {
            this.getLogger().severe("Pixelmon Gym v6.2+ requires Vault Plugin. Error setting up economy support.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!this.setupPermissions()) {
            this.getLogger().severe("Pixelmon Gym v6.2+ requires Vault Plugin. Error setting up permission support.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        PixelGym.myInventory = Bukkit.createInventory((InventoryHolder) null, 45, ChatColor.GREEN + "Badges!");
        this.settings.setup(this);
        this.settings.setupBadges(this);
        this.settings.setupLog(this);
        this.queues = new HashMap();

        int pm;

        for (pm = 1; pm <= 32; ++pm) {
            this.queues.put(Integer.valueOf(pm), new ArrayList());
        }

        this.cooldownGym = new HashMap();

        for (pm = 1; pm <= 32; ++pm) {
            this.cooldownGym.put(Integer.valueOf(pm), new ArrayList());
        }

        this.cooldownTask = new HashMap();

        for (pm = 1; pm <= 32; ++pm) {
            this.cooldownTask.put(Integer.valueOf(pm), new HashMap());
        }

        this.cooldownTime = new HashMap();

        for (pm = 1; pm <= 32; ++pm) {
            this.cooldownTime.put(Integer.valueOf(pm), new HashMap());
        }

        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        PluginManager pluginmanager = this.getServer().getPluginManager();

        pluginmanager.registerEvents(new PixelGymAdmin(this), this);
        pluginmanager.registerEvents(new SignListeners(), this);
        PixelGym.instance = this;
        this.getCommand("pixelgym").setExecutor(new PixelGymAdmin(this));
        this.manager = Bukkit.getScoreboardManager();
        this.board = this.manager.getNewScoreboard();
        this.clear = this.manager.getNewScoreboard();
        this.none = this.clear.registerNewObjective("test", "dummy");
        this.obj = this.board.registerNewObjective("test", "dummy");
        this.obj.setDisplayName(ChatColor.GREEN + "Open Gyms");
        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.gym1 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym1colour")) + this.getConfig().getString("config.gym1")));
        this.gym2 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym2colour")) + this.getConfig().getString("config.gym2")));
        this.gym3 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym3colour")) + this.getConfig().getString("config.gym3")));
        this.gym4 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym4colour")) + this.getConfig().getString("config.gym4")));
        this.gym5 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym5colour")) + this.getConfig().getString("config.gym5")));
        this.gym6 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym6colour")) + this.getConfig().getString("config.gym6")));
        this.gym7 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym7colour")) + this.getConfig().getString("config.gym7")));
        this.gym8 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym8colour")) + this.getConfig().getString("config.gym8")));
        this.gym9 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym9colour")) + this.getConfig().getString("config.gym9")));
        this.gym10 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym10colour")) + this.getConfig().getString("config.gym10")));
        this.gym11 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym11colour")) + this.getConfig().getString("config.gym11")));
        this.gym12 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym12colour")) + this.getConfig().getString("config.gym12")));
        this.gym13 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym13colour")) + this.getConfig().getString("config.gym13")));
        this.gym14 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym14colour")) + this.getConfig().getString("config.gym14")));
        this.gym15 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym15colour")) + this.getConfig().getString("config.gym15")));
        this.gym16 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym16colour")) + this.getConfig().getString("config.gym16")));
        this.gym17 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym17colour")) + this.getConfig().getString("config.gym17")));
        this.gym18 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym18colour")) + this.getConfig().getString("config.gym18")));
        this.gym19 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym19colour")) + this.getConfig().getString("config.gym19")));
        this.gym20 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym20colour")) + this.getConfig().getString("config.gym20")));
        this.gym21 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym21colour")) + this.getConfig().getString("config.gym21")));
        this.gym22 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym22colour")) + this.getConfig().getString("config.gym22")));
        this.gym23 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym23colour")) + this.getConfig().getString("config.gym23")));
        this.gym24 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym24colour")) + this.getConfig().getString("config.gym24")));
        this.gym25 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym25colour")) + this.getConfig().getString("config.gym25")));
        this.gym26 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym26colour")) + this.getConfig().getString("config.gym26")));
        this.gym27 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym27colour")) + this.getConfig().getString("config.gym27")));
        this.gym28 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym28colour")) + this.getConfig().getString("config.gym28")));
        this.gym29 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym29colour")) + this.getConfig().getString("config.gym29")));
        this.gym30 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym30colour")) + this.getConfig().getString("config.gym30")));
        this.gym31 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym31colour")) + this.getConfig().getString("config.gym31")));
        this.gym32 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym32colour")) + this.getConfig().getString("config.gym32")));
        this.e41 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + this.getConfig().getString("config.e41") + " " + this.getConfig().getString("config.e4ab")));
        this.e42 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + this.getConfig().getString("config.e42") + " " + this.getConfig().getString("config.e4ab")));
        this.e43 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + this.getConfig().getString("config.e43") + " " + this.getConfig().getString("config.e4ab")));
        this.e44 = this.obj.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + this.getConfig().getString("config.e44") + " " + this.getConfig().getString("config.e4ab")));
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                PixelGym.this.updateSigns();
            }
        }, 0L, 20L);
    }

    public void onDisable() {}

    public static PixelGym get() {
        return PixelGym.instance;
    }

    public boolean removeSign(Location l) {
        File directory = this.getDataFolder();

        if (!directory.exists()) {
            return false;
        } else {
            File storage = new File(directory, "signs.yml");

            if (!storage.exists()) {
                return false;
            } else {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);
                List list = config.getStringList("status");

                if (list == null) {
                    return false;
                } else {
                    int x;
                    int y;

                    for (int list2 = 0; list2 < list.size(); ++list2) {
                        String i = (String) list.get(list2);
                        String[] s = i.split(", ");
                        int a = Integer.parseInt(s[0]);

                        x = Integer.parseInt(s[1]);
                        y = Integer.parseInt(s[2]);
                        World z = Bukkit.getWorld(s[3]);

                        if (l.getWorld() == z && l.getBlockX() == a && l.getBlockY() == x && l.getBlockZ() == y) {
                            list.remove(list2);

                            try {
                                config.save(storage);
                            } catch (IOException ioexception) {
                                ioexception.printStackTrace();
                            }

                            return true;
                        }
                    }

                    List list = config.getStringList("leader");

                    if (list == null) {
                        return false;
                    } else {
                        for (int i = 0; i < list.size(); ++i) {
                            String s = (String) list.get(i);
                            String[] astring = s.split(", ");

                            x = Integer.parseInt(astring[0]);
                            y = Integer.parseInt(astring[1]);
                            int j = Integer.parseInt(astring[2]);
                            World w = Bukkit.getWorld(astring[3]);

                            if (l.getWorld() == w && l.getBlockX() == x && l.getBlockY() == y && l.getBlockZ() == j) {
                                list.remove(i);

                                try {
                                    config.save(storage);
                                } catch (IOException ioexception1) {
                                    ioexception1.printStackTrace();
                                }

                                return true;
                            }
                        }

                        return false;
                    }
                }
            }
        }
    }

    public void updateSigns() {
        File directory = this.getDataFolder();

        if (directory.exists()) {
            File storage = new File(directory, "signs.yml");

            if (storage.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);
                List list = config.getStringList("status");

                if (list != null && list.size() != 0) {
                    Iterator s = list.iterator();

                    int x;
                    int y;

                    while (s.hasNext()) {
                        String list2 = (String) s.next();
                        String[] a = list2.split(", ");
                        int a1 = Integer.parseInt(a[0]);

                        x = Integer.parseInt(a[1]);
                        y = Integer.parseInt(a[2]);
                        World z = Bukkit.getWorld(a[3]);
                        String w = "gym" + a[4];
                        Location gym = new Location(z, (double) a1, (double) x, (double) y);

                        if (gym.getBlock().getType() != Material.WALL_SIGN && gym.getBlock().getType() != Material.SIGN_POST) {
                            this.removeSign(gym);
                        } else {
                            Sign l = (Sign) gym.getBlock().getState();

                            l.setLine(0, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + w + "colour") + this.getConfig().getString("config." + w) + " Gym"));
                            l.setLine(1, (this.getConfig().getString("config." + w + "stat").equalsIgnoreCase("Open") ? ChatColor.GREEN : ChatColor.RED) + this.getConfig().getString("config." + w + "stat"));
                            l.setLine(2, "§9Level Cap: " + this.getConfig().getString("config." + w + "lvlcap"));
                            l.update();
                        }
                    }

                    List list = config.getStringList("leader");

                    if (list != null) {
                        Iterator iterator = list.iterator();

                        while (iterator.hasNext()) {
                            String s = (String) iterator.next();
                            String[] astring = s.split(", ");

                            x = Integer.parseInt(astring[0]);
                            y = Integer.parseInt(astring[1]);
                            int i = Integer.parseInt(astring[2]);
                            World world = Bukkit.getWorld(astring[3]);
                            String s1 = "gym" + astring[4];
                            Location location = new Location(world, (double) x, (double) y, (double) i);

                            if (location.getBlock().getType() != Material.WALL_SIGN && location.getBlock().getType() != Material.SIGN_POST) {
                                this.removeSign(location);
                            } else if (this.getSignNumber(location) == 1) {
                                System.out.println("UpdateSign: Sign (1) == 1");
                                ArrayList signsList = new ArrayList();
                                Iterator i = list.iterator();

                                while (i.hasNext()) {
                                    String signsArray = (String) i.next();
                                    String[] a2 = signsArray.split(", ");
                                    int x2 = Integer.parseInt(a2[0]);
                                    int y2 = Integer.parseInt(a2[1]);
                                    int z2 = Integer.parseInt(a2[2]);
                                    World w2 = Bukkit.getWorld(a2[3]);
                                    String gym2 = "gym" + a2[4];
                                    Location l2 = new Location(w2, (double) x2, (double) y2, (double) z2);

                                    if (location.getBlock().getType() != Material.WALL_SIGN && location.getBlock().getType() != Material.SIGN_POST) {
                                        this.removeSign(location);
                                    } else if (gym2.equalsIgnoreCase(s1)) {
                                        System.out.println("UpdateSign: gym2 = gym");
                                        System.out.println("Distance:" + location.distance(l2));
                                        if (location.distance(l2) < 1.1D) {
                                            System.out.println("SNumber: " + this.getSignNumber(l2));
                                            if (this.getSignNumber(l2) > 1) {
                                                System.out.println("SNumber2: " + this.getSignNumber(l2));
                                                System.out.println("UpdateSign: Sign Number (l2) > 1");
                                                Sign sign2 = (Sign) location.getBlock().getState();

                                                sign2.setLine(0, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + s1 + "colour") + this.getConfig().getString("config." + s1) + " Leaders"));
                                                signsList.add(sign2);
                                                sign2.update();
                                            }
                                        }
                                    }
                                }

                                Sign[] asign = new Sign[signsList.size()];

                                for (int j = 0; j < signsList.size(); ++j) {
                                    asign[j] = (Sign) signsList.get(j);
                                }

                                this.updateLeaderSign(location, s1, asign);
                            }
                        }

                    }
                }
            }
        }
    }

    public int getSignNumber(Location l) {
        File directory = this.getDataFolder();

        if (!directory.exists()) {
            return 0;
        } else {
            File storage = new File(directory, "signs.yml");

            if (storage.exists() && storage.mkdirs()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(storage);
                List list = config.getStringList("leader");

                if (list != null && list.size() != 0) {
                    Iterator iterator = list.iterator();

                    String[] a;
                    int x;
                    int y;
                    int z;
                    World w;

                    do {
                        if (!iterator.hasNext()) {
                            return 0;
                        }

                        String s = (String) iterator.next();

                        a = s.split(", ");
                        x = Integer.parseInt(a[0]);
                        y = Integer.parseInt(a[1]);
                        z = Integer.parseInt(a[2]);
                        w = Bukkit.getWorld(a[3]);
                    } while (l.getWorld() != w || l.getBlockX() != x || l.getBlockY() != y || l.getBlockZ() != z);

                    return Integer.parseInt(a[5]);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }
    }

    public void updateLeaderSign(Location l, String gym, Sign[] otherSigns) {
        int line = 1;
        int sign = 1;
        Sign firstSign = (Sign) l.getBlock().getState();
        Sign selectedSign = firstSign;
        Player staff;
        Iterator iterator;

        if (otherSigns != null && otherSigns.length != 0) {
            iterator = Bukkit.getOnlinePlayers().iterator();

            while (iterator.hasNext()) {
                staff = (Player) iterator.next();
                if (line > 3) {
                    line = 0;
                    ++sign;
                    selectedSign.update();
                    selectedSign = null;
                    Sign[] asign = otherSigns;
                    int i = otherSigns.length;

                    for (int j = 0; j < i; ++j) {
                        Sign s = asign[j];

                        if (this.getSignNumber(s.getLocation()) == sign) {
                            selectedSign = s;
                            break;
                        }
                    }

                    if (selectedSign == null) {
                        return;
                    }
                }

                if (staff.hasPermission("pixelgym" + firstSign.getLine(1))) {
                    selectedSign.setLine(line, staff.getName().toString());
                    ++line;
                }
            }
        } else {
            iterator = Bukkit.getOnlinePlayers().iterator();

            while (iterator.hasNext()) {
                staff = (Player) iterator.next();
                if (otherSigns == null || otherSigns.length == 0) {
                    if (line > 3) {
                        break;
                    }

                    if (staff.hasPermission("pixelgym" + firstSign.getLine(1))) {
                        firstSign.setLine(line, staff.getName().toString());
                        ++line;
                    }

                    firstSign.update();
                }
            }
        }

    }

    @EventHandler
    public void onLeave1(PlayerQuitEvent l) {
        Player p = l.getPlayer();
        UUID u = l.getPlayer().getUniqueId();

        int i;

        for (i = 1; i <= 32; ++i) {
            if (((List) this.queues.get(Integer.valueOf(i))).contains(u)) {
                ((List) this.queues.get(Integer.valueOf(i))).remove(u);
            }
        }

        int count;
        Player online;
        Iterator iterator;

        for (i = 1; i <= 4; ++i) {
            if (p.hasPermission("pixelgym.e4" + i) && this.getConfig().getString("config.e4" + i + "stat").equalsIgnoreCase("Open")) {
                count = 0;
                iterator = Bukkit.getOnlinePlayers().iterator();

                while (iterator.hasNext()) {
                    online = (Player) iterator.next();
                    if (!p.getName().equalsIgnoreCase(online.getName()) && online.hasPermission("pixelgym.e4" + i)) {
                        ++count;
                    }
                }

                if (count == 0) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e4" + i + "colour")) + "The " + this.getConfig().getString("config.e4" + i) + " Elite 4 Level is now " + ChatColor.RED + "Closed");
                    this.getConfig().set("config.e4" + i + "stat", "Closed");
                    this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e4" + i + "colour")) + this.getConfig().getString("config.e4" + i) + " " + this.getConfig().getString("config.e4ab")));
                }
            }
        }

        for (i = 1; i <= 32; ++i) {
            if (p.hasPermission("pixelgym.gym" + i) && this.getConfig().getString("config.gym" + i + "stat").equalsIgnoreCase("Open")) {
                count = 0;
                iterator = Bukkit.getOnlinePlayers().iterator();

                while (iterator.hasNext()) {
                    online = (Player) iterator.next();
                    if (!p.getName().equalsIgnoreCase(online.getName()) && online.hasPermission("pixelgym.gym" + i)) {
                        ++count;
                    }
                }

                if (count == 0) {
                    p.sendMessage("You are the last" + this.getConfig().getString("pixelgym.gym" + i) + "gym leader" + i);
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + i + "colour")) + "The " + this.getConfig().getString("config.gym" + i) + " Gym is now " + ChatColor.RED + "Closed");
                    this.getConfig().set("config.gym" + i + "stat", "Closed");
                    this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + i + "colour")) + this.getConfig().getString("config.gym" + i)));
                }
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String enablegym1 = this.getConfig().getString("config.gym1enabled");
        String enable2 = this.getConfig().getString("config.gym2enabled");
        String enable3 = this.getConfig().getString("config.gym3enabled");
        String enable4 = this.getConfig().getString("config.gym4enabled");
        String enable5 = this.getConfig().getString("config.gym5enabled");
        String enable6 = this.getConfig().getString("config.gym6enabled");
        String enable7 = this.getConfig().getString("config.gym7enabled");
        String enable8 = this.getConfig().getString("config.gym8enabled");
        String enable9 = this.getConfig().getString("config.gym9enabled");
        String enablegym10 = this.getConfig().getString("config.gym10enabled");
        String enablegym11 = this.getConfig().getString("config.gym11enabled");
        String enablegym12 = this.getConfig().getString("config.gym12enabled");
        String enablegym13 = this.getConfig().getString("config.gym13enabled");
        String enablegym14 = this.getConfig().getString("config.gym14enabled");
        String enablegym15 = this.getConfig().getString("config.gym15enabled");
        String enablegym16 = this.getConfig().getString("config.gym16enabled");
        String enablegym17 = this.getConfig().getString("config.gym17enabled");
        String enablegym18 = this.getConfig().getString("config.gym18enabled");
        String enablegym19 = this.getConfig().getString("config.gym19enabled");
        String enable20 = this.getConfig().getString("config.gym20enabled");
        String enable21 = this.getConfig().getString("config.gym21enabled");
        String enable22 = this.getConfig().getString("config.gym22enabled");
        String enable23 = this.getConfig().getString("config.gym23enabled");
        String enable24 = this.getConfig().getString("config.gym24enabled");
        String enable25 = this.getConfig().getString("config.gym25enabled");
        String enable26 = this.getConfig().getString("config.gym26enabled");
        String enable27 = this.getConfig().getString("config.gym27enabled");
        String enable28 = this.getConfig().getString("config.gym28enabled");
        String enable29 = this.getConfig().getString("config.gym29enabled");
        String enable30 = this.getConfig().getString("config.gym30enabled");
        String enable31 = this.getConfig().getString("config.gym31enabled");
        String enable32 = this.getConfig().getString("config.gym32enabled");
        String enablee4 = this.getConfig().getString("config.e4enabled");
        Player p = e.getPlayer();

        Player i;
        Iterator iterator;

        if (this.getConfig().getString("config.scoreboard").equals("True")) {
            p.sendMessage(ChatColor.BLUE + "To disable your Scoreboard, type /gym scoreboard.");
            p.setScoreboard(this.board);
            this.hashmap.put(p, (Object) null);
            iterator = Bukkit.getOnlinePlayers().iterator();

            while (iterator.hasNext()) {
                i = (Player) iterator.next();
                i.setScoreboard(this.board);
            }

            if (this.getConfig().getString("config.e41stat").equals("Open") && enablee4.equalsIgnoreCase("true")) {
                this.e41.setScore(101);
            }

            if (this.getConfig().getString("config.e42stat").equals("Open") && enablee4.equalsIgnoreCase("true")) {
                this.e42.setScore(102);
            }

            if (this.getConfig().getString("config.e43stat").equals("Open") && enablee4.equalsIgnoreCase("true")) {
                this.e43.setScore(103);
            }

            if (this.getConfig().getString("config.e44stat").equals("Open") && enablee4.equalsIgnoreCase("true")) {
                this.e44.setScore(104);
            }

            if (this.getConfig().getString("config.gym1stat").equals("Open") && enablegym1.equalsIgnoreCase("true")) {
                this.gym1.setScore(1);
            }

            if (this.getConfig().getString("config.gym2stat").equals("Open") && enable2.equalsIgnoreCase("true")) {
                this.gym2.setScore(2);
            }

            if (this.getConfig().getString("config.gym3stat").equals("Open") && enable3.equalsIgnoreCase("true")) {
                this.gym3.setScore(3);
            }

            if (this.getConfig().getString("config.gym4stat").equals("Open") && enable4.equalsIgnoreCase("true")) {
                this.gym4.setScore(4);
            }

            if (this.getConfig().getString("config.gym5stat").equals("Open") && enable5.equalsIgnoreCase("true")) {
                this.gym5.setScore(5);
            }

            if (this.getConfig().getString("config.gym6stat").equals("Open") && enable6.equalsIgnoreCase("true")) {
                this.gym6.setScore(6);
            }

            if (this.getConfig().getString("config.gym7stat").equals("Open") && enable7.equalsIgnoreCase("true")) {
                this.gym7.setScore(7);
            }

            if (this.getConfig().getString("config.gym8stat").equals("Open") && enable8.equalsIgnoreCase("true")) {
                this.gym8.setScore(8);
            }

            if (this.getConfig().getString("config.gym9stat").equals("Open") && enable9.equalsIgnoreCase("true")) {
                this.gym9.setScore(9);
            }

            if (this.getConfig().getString("config.gym10stat").equals("Open") && enablegym10.equalsIgnoreCase("true")) {
                this.gym10.setScore(10);
            }

            if (this.getConfig().getString("config.gym11stat").equals("Open") && enablegym11.equalsIgnoreCase("true")) {
                this.gym11.setScore(11);
            }

            if (this.getConfig().getString("config.gym12stat").equals("Open") && enablegym12.equalsIgnoreCase("true")) {
                this.gym12.setScore(12);
            }

            if (this.getConfig().getString("config.gym13stat").equals("Open") && enablegym13.equalsIgnoreCase("true")) {
                this.gym13.setScore(13);
            }

            if (this.getConfig().getString("config.gym14stat").equals("Open") && enablegym14.equalsIgnoreCase("true")) {
                this.gym14.setScore(14);
            }

            if (this.getConfig().getString("config.gym15stat").equals("Open") && enablegym15.equalsIgnoreCase("true")) {
                this.gym15.setScore(15);
            }

            if (this.getConfig().getString("config.gym16stat").equals("Open") && enablegym16.equalsIgnoreCase("true")) {
                this.gym16.setScore(16);
            }

            if (this.getConfig().getString("config.gym17stat").equals("Open") && enablegym17.equalsIgnoreCase("true")) {
                this.gym17.setScore(17);
            }

            if (this.getConfig().getString("config.gym18stat").equals("Open") && enablegym18.equalsIgnoreCase("true")) {
                this.gym18.setScore(18);
            }

            if (this.getConfig().getString("config.gym19stat").equals("Open") && enablegym19.equalsIgnoreCase("true")) {
                this.gym19.setScore(19);
            }

            if (this.getConfig().getString("config.gym20stat").equals("Open") && enable20.equalsIgnoreCase("true")) {
                this.gym20.setScore(20);
            }

            if (this.getConfig().getString("config.gym21stat").equals("Open") && enable21.equalsIgnoreCase("true")) {
                this.gym21.setScore(21);
            }

            if (this.getConfig().getString("config.gym22stat").equals("Open") && enable22.equalsIgnoreCase("true")) {
                this.gym22.setScore(22);
            }

            if (this.getConfig().getString("config.gym23stat").equals("Open") && enable23.equalsIgnoreCase("true")) {
                this.gym23.setScore(23);
            }

            if (this.getConfig().getString("config.gym24stat").equals("Open") && enable24.equalsIgnoreCase("true")) {
                this.gym24.setScore(24);
            }

            if (this.getConfig().getString("config.gym25stat").equals("Open") && enable25.equalsIgnoreCase("true")) {
                this.gym25.setScore(25);
            }

            if (this.getConfig().getString("config.gym26stat").equals("Open") && enable26.equalsIgnoreCase("true")) {
                this.gym26.setScore(26);
            }

            if (this.getConfig().getString("config.gym27stat").equals("Open") && enable27.equalsIgnoreCase("true")) {
                this.gym27.setScore(27);
            }

            if (this.getConfig().getString("config.gym28stat").equals("Open") && enable28.equalsIgnoreCase("true")) {
                this.gym28.setScore(28);
            }

            if (this.getConfig().getString("config.gym29stat").equals("Open") && enable29.equalsIgnoreCase("true")) {
                this.gym29.setScore(29);
            }

            if (this.getConfig().getString("config.gym30stat").equals("Open") && enable30.equalsIgnoreCase("true")) {
                this.gym30.setScore(30);
            }

            if (this.getConfig().getString("config.gym31stat").equals("Open") && enable31.equalsIgnoreCase("true")) {
                this.gym31.setScore(31);
            }

            if (this.getConfig().getString("config.gym32stat").equals("Open") && enable32.equalsIgnoreCase("true")) {
                this.gym32.setScore(32);
            }
        }


        }

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
        Player p = (Player) sender;
        Player playerTarget;
        Iterator gymArg;

        if (commandLable.equalsIgnoreCase("gym")) {
            if (args.length == 0) {
                if (!p.hasPermission("pixelgym.leader")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/gym see [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player.");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                } else if (p.hasPermission("pixelgym.leader")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/gym <see | check> [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player. | = or, you can type see or check.");
                    p.sendMessage(ChatColor.GREEN + "/gym next <gym#>" + ChatColor.DARK_GREEN + " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don\'t need to)");
                    p.sendMessage(ChatColor.GREEN + "/gym remove <gym#>" + ChatColor.DARK_GREEN + " - Remove\'s the first person of the specified gym queue (If someone has disconnected and does not relog after a while)");
                    p.sendMessage(ChatColor.GREEN + "/gym <winner | win | w> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a winner, giving them the badge for the next gym!");
                    p.sendMessage(ChatColor.GREEN + "/gym <lost | lose | l> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a loser, teleporting them out of the gym and giving them a 1 hour cooldown!");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                } else if (p.hasPermission("pixelgym.admin")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/gym <see | check> [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player. | = or, you can type see or check.");
                    p.sendMessage(ChatColor.GREEN + "/gym next <gym#>" + ChatColor.DARK_GREEN + " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don\'t need to)");
                    p.sendMessage(ChatColor.GREEN + "/gym remove <gym#>" + ChatColor.DARK_GREEN + " - Remove\'s the first person of the specified gym queue (If someone has disconnected and does not relog after a while)");
                    p.sendMessage(ChatColor.GREEN + "/gym <winner | win | w> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a winner, giving them the badge for the next gym!");
                    p.sendMessage(ChatColor.GREEN + "/gym givebadge <gym#> [player]" + ChatColor.DARK_GREEN + " - Need to give badges quickly? Then use this command to give player\'s there badge\'s, avoiding the cooldown and having to be in a queue.");
                    p.sendMessage(ChatColor.GREEN + "/gym delbadge <gym#> [player]" + ChatColor.DARK_GREEN + " - Need to delete badges quickly? Then use this command to remove player\'s badge\'s, avoiding the cooldown and having to be in a queue.");
                    p.sendMessage(ChatColor.GREEN + "/gym setwarp <gym#>" + ChatColor.DARK_GREEN + " - Used for the queue system, set a warp that is only a number. E.G: /gym setwarp 1 in the gym1 challanger spot.");
                    p.sendMessage(ChatColor.GREEN + "/gym delwarp <gym#>" + ChatColor.DARK_GREEN + " - Used for the queue system, delete a warp that you no longer need. E.G: /gym delwarp 1 to remove the gym1 teleport.");
                    p.sendMessage(ChatColor.GREEN + "/gym warp [warp name]" + ChatColor.DARK_GREEN + " - Warp to a gym warp! (For testing teleport locations of the queue system).");
                }
            } else {
                int playerLost;
                int gym;

                if (args.length == 1) {
                } else {
                    final UUID po;
                    double y;
                    double z;
                    String s;
                    Player player;
                    int j;
                    int k;
                    World world;
                    double d0;

                    if (args.length == 2) {
                        if (!args[0].equalsIgnoreCase("check") && !args[0].equalsIgnoreCase("position")) {
                            if (!args[0].equalsIgnoreCase("leave") && !args[0].equalsIgnoreCase("quit")) {
                                Player player1;

                                if (args[0].equalsIgnoreCase("remove")) {
                                    s = args[1].replace("gym", "");

                                    try {
                                        gym = Integer.parseInt(s);
                                    } catch (NumberFormatException numberformatexception1) {
                                        p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                        return true;
                                    }

                                    if (gym < 1 || gym > 32) {
                                        return true;
                                    }

                                    if (p.hasPermission("pixelgym.leader") && (p.hasPermission("pixelgym." + args[1]) || p.hasPermission("pixelgym.admin"))) {
                                        UUID uuid = (UUID) ((List) this.queues.get(Integer.valueOf(gym))).get(0);

                                        player1 = Bukkit.getPlayer(uuid);
                                        if (this.settings.getData().get("warps.spawn") != null) {
                                            ((List) this.queues.get(Integer.valueOf(gym))).remove(0);
                                            world = Bukkit.getServer().getWorld(this.settings.getData().getString("warps.spawn.world"));
                                            d0 = this.settings.getData().getDouble("warps.spawn.x");
                                            y = this.settings.getData().getDouble("warps.spawn.y");
                                            z = this.settings.getData().getDouble("warps.spawn.z");
                                            player1.teleport(new Location(world, d0, y, z));
                                            player1.sendMessage(ChatColor.GREEN + "You have been removed from the queue by a gym leader! (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + gym + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + gym) + ChatColor.GREEN + ") You can re-enter the queue, but make sure you are not Afk and co-operate with the gym leader!"));
                                            p.sendMessage(ChatColor.GREEN + "Successfully telported " + player1.getName() + " out of your gym!");
                                            p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym" + gym);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Warp point \'spawn\' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
                                        }
                                    }
                                } else if (args[0].equalsIgnoreCase("next")) {
                                    s = args[1].replace("gym", "");

                                    try {
                                        gym = Integer.parseInt(s);
                                    } catch (NumberFormatException numberformatexception2) {
                                        p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                        return true;
                                    }

                                    if (gym < 1 || gym > 32) {
                                        return true;
                                    }

                                    playerLost = gym + 1;
                                    if (p.hasPermission("pixelgym.leader")) {
                                        if (p.hasPermission("pixelgym." + args[1])) {
                                            player1 = null;

                                            while (player1 == null) {
                                                if (((List) this.queues.get(Integer.valueOf(gym))).size() <= 0) {
                                                    p.sendMessage(ChatColor.RED + "There are currently no people in the queue " + ChatColor.YELLOW + ChatColor.BOLD + gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + gym + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + gym) + ChatColor.BLACK + ")"));
                                                    return true;
                                                }

                                                if (this.settings.getData().getConfigurationSection("warps." + args[1]) == null) {
                                                    p.sendMessage(ChatColor.RED + "Warp " + args[1] + " does not exist!");
                                                    return true;
                                                }

                                                UUID uuid1 = (UUID) ((List) this.queues.get(Integer.valueOf(gym))).get(0);

                                                player1 = Bukkit.getPlayer(uuid1);
                                                p.sendMessage(ChatColor.GREEN + "Getting first player from queue " + ChatColor.GOLD + " (" + player1.getName() + ")" + ChatColor.YELLOW + ChatColor.BOLD + gym + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + gym + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + gym) + ChatColor.BLACK + ")"));
                                                p.sendMessage(ChatColor.BLUE + "Make sure you type " + ChatColor.RED + "/gym lost gym" + gym + " (player) " + ChatColor.BLUE + "or " + ChatColor.GREEN + "/gym win gym" + gym + " (player)" + ChatColor.BLUE + " after they have won or lost the battle. (They need it to join gym" + playerLost + " queue (If they won.)) And it teleports them out of your gym!");
                                            }

                                            if (this.getConfig().getString("config.gymfee").equalsIgnoreCase("True")) {
                                                k = this.getConfig().getInt("config.gymfee");
                                                if (PixelGym.economy.getBalance(player1) < (double) k) {
                                                    player1.sendMessage(ChatColor.RED + "You do not have enough money to face the gym!");
                                                    ((List) this.queues.get(Integer.valueOf(gym))).remove(0);
                                                    p.sendMessage(ChatColor.RED + "First player did not have enough money, type /gym next gym" + gym + " to get the next player.");
                                                    return true;
                                                }

                                                Bukkit.dispatchCommand(player1, "pay " + p.getName() + " " + this.getConfig().getString("config.gymfeeamount"));
                                                World world1 = Bukkit.getServer().getWorld(this.settings.getData().getString("warps." + args[1] + ".world"));
                                                EconomyResponse time = PixelGym.economy.withdrawPlayer(player1, (double) k);
                                                EconomyResponse economyresponse = PixelGym.economy.depositPlayer(p, (double) k);

                                                if (time.transactionSuccess()) {
                                                    economyresponse.transactionSuccess();
                                                }

                                                double x = this.settings.getData().getDouble("warps." + args[1] + ".x");
                                                double y1 = this.settings.getData().getDouble("warps." + args[1] + ".y");
                                                double z2 = this.settings.getData().getDouble("warps." + args[1] + ".z");

                                                player1.teleport(new Location(world1, x, y1, z2));
                                                player1.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.YELLOW + ChatColor.BOLD + args[1] + "!");
                                                player1.sendMessage(ChatColor.GREEN + "Welcome to " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour") + ChatColor.BOLD + this.getConfig().getString("config." + args[1]) + " Gym!"));
                                                player1.sendMessage(ChatColor.GOLD + "----- " + this.getConfig().getString("config.gym" + gym) + " Gym Rules -----");
                                                player1.sendMessage("");
                                                player1.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config.gym" + gym + "rule1"));
                                                player1.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config.gym" + gym + "rule2"));
                                                player1.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config.gym" + gym + "rule3"));
                                                player1.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config.gym" + gym + "rule4"));
                                                player1.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config.gym" + gym + "rule5"));
                                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + player1.getName().toString());
                                                player1.sendMessage(ChatColor.GREEN + "Your pixelmon have been healed!");
                                            }
                                        } else {
                                            p.sendMessage(ChatColor.RED + "You do not have permission to open gym" + gym + "!");
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "You are not a gym leader, you do not have permission to do this command!");
                                    }
                                }
                            }
                        } else {
                            s = args[1].replace("gym", "");

                            try {
                                gym = Integer.parseInt(s);
                            } catch (NumberFormatException numberformatexception4) {
                                p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                return true;
                            }

                            if (gym < 1 || gym > 32) {
                                return true;
                            }

                            player = null;
                            if (((List) this.queues.get(Integer.valueOf(gym))).size() > 0) {
                                po = (UUID) ((List) this.queues.get(Integer.valueOf(gym))).get(0);
                                player = Bukkit.getPlayer(po);
                                k = ((List) this.queues.get(Integer.valueOf(gym))).indexOf(p.getUniqueId()) + 1;
                                if (((List) this.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId())) {
                                    p.sendMessage(ChatColor.GOLD + "You are currently in position " + k + " for the " + this.getConfig().getString("config." + args[1]) + " Gym");
                                    p.sendMessage(ChatColor.GOLD + "The queue size for the " + this.getConfig().getString("config." + args[1]) + " Gym is " + ((List) this.queues.get(Integer.valueOf(gym))).size());
                                }

                                if (p.hasPermission("pixelgym." + args[1]) && !((List) this.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId())) {
                                    p.sendMessage(ChatColor.GOLD + "The queue size for the " + this.getConfig().getString("config." + args[1]) + " Gym is " + ((List) this.queues.get(Integer.valueOf(gym))).size());
                                    p.sendMessage(ChatColor.GOLD + "The first player of the queue is: (" + player.getName() + ")");
                                } else if (((List) this.queues.get(Integer.valueOf(gym))).contains(p.getUniqueId()) && p.hasPermission("pixelgym." + args[1])) {
                                    p.sendMessage(ChatColor.GOLD + "The queue size for the " + this.getConfig().getString("config." + args[1]) + " Gym is " + ((List) this.queues.get(Integer.valueOf(gym))).size());
                                    p.sendMessage(ChatColor.GOLD + "The first player of the queue is: (" + player.getName() + ")");
                                } else {
                                    p.sendMessage(ChatColor.RED + "You are not in queue " + args[1]);
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "There is no one in this queue!");
                            }
                        }

                        if ((args[0].equalsIgnoreCase("see") || args[0].equalsIgnoreCase("s")) && Bukkit.getPlayer(args[1]) != null) {
                            playerTarget = Bukkit.getPlayer(args[1]);
                            Inventory inventory = Bukkit.createInventory((InventoryHolder) null, 45, ChatColor.GREEN + "Badges!");

                            for (playerLost = 1; playerLost <= 32; ++playerLost) {
                                j = playerLost - 1;
                                if (this.settings.getBadge().get("Players." + playerTarget.getUniqueId() + ".Badges.gym" + playerLost) != null && this.settings.getBadge().getString("Players." + playerTarget.getUniqueId() + ".Badges.gym" + playerLost).equalsIgnoreCase("Won") && this.getConfig().getString("config.gym" + playerLost + "badge") != null) {
                                    ItemStack itemstack = new ItemStack(this.getConfig().getInt("config.gym" + playerLost + "badge"));
                                    ItemMeta itemmeta = itemstack.getItemMeta();

                                    itemmeta.setDisplayName(ChatColor.RED + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + playerLost + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + playerLost) + " Badge!"));
                                    if (this.settings.getLogs().get("Challengers." + playerTarget.getUniqueId() + ".gym" + playerLost) != null) {
                                        itemmeta.setLore(Arrays.asList(new String[] { ChatColor.GOLD + "Date/Time Won: " + ChatColor.GREEN + this.settings.getLogs().getString("Challengers." + playerTarget.getUniqueId() + "." + "gym" + playerLost + ".Date/Time Won"), ChatColor.GOLD + "Gym Leader: " + ChatColor.GREEN + this.settings.getLogs().getString("Challengers." + playerTarget.getUniqueId() + "." + "gym" + playerLost + ".Gym Leader")}));
                                        itemstack.setItemMeta(itemmeta);
                                        inventory.setItem(j, itemstack);
                                    } else {
                                        itemmeta.setLore(Arrays.asList(new String[] { ChatColor.RED + "Gym badge info un-known! (Badge won before feature implemented!)"}));
                                        itemstack.setItemMeta(itemmeta);
                                        inventory.setItem(j, itemstack);
                                    }
                                }
                            }

                            if (!this.hasOpen.contains(p.getName())) {
                                this.hasOpen.add(p.getName());
                            }

                            p.openInventory(inventory);
                            p.sendMessage(ChatColor.GREEN + "Opening " + playerTarget.getName() + "\'s badge showcase!");
                        }
                        if (args[0].equalsIgnoreCase("setwarp")) {
                            if (p.hasPermission("pixelgym.admin")) {
                                if (this.settings.getData().get("warps." + args[1]) != null) {
                                    p.sendMessage(ChatColor.RED + args[1] + " warp already exists. If you want to overwrite it, do /gym delwarp " + args[1] + ". And then re-set the new warp.");
                                } else {
                                    this.settings.getData().set("warps." + args[1] + ".world", p.getLocation().getWorld().getName());
                                    this.settings.getData().set("warps." + args[1] + ".x", Double.valueOf(p.getLocation().getX()));
                                    this.settings.getData().set("warps." + args[1] + ".y", Double.valueOf(p.getLocation().getY()));
                                    this.settings.getData().set("warps." + args[1] + ".z", Double.valueOf(p.getLocation().getZ()));
                                    this.settings.saveData();
                                    p.sendMessage(ChatColor.GREEN + "Set warp " + args[1] + "!");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "You do not have permission to set a warp!");
                            }
                        }

                        if (args[0].equalsIgnoreCase("delwarp")) {
                            if (p.hasPermission("pixelgym.admin")) {
                                if (this.settings.getData().getConfigurationSection("warps." + args[1]) == null) {
                                    p.sendMessage(ChatColor.RED + "Warp " + args[1] + " does not exist!");
                                    return true;
                                }

                                this.settings.getData().set("warps." + args[1], (Object) null);
                                this.settings.saveData();
                                p.sendMessage(ChatColor.GREEN + "Removed warp " + args[1] + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "You do not have permission to delete a warp!");
                            }
                        }

                        if (args[0].equalsIgnoreCase("warp")) {
                            if (!p.hasPermission("pixelgym.admin") && !p.hasPermission("pixelgym." + args[1])) {
                                p.sendMessage(ChatColor.RED + "You do not have permission to warp to a gym!");
                            } else {
                                if (this.settings.getData().getConfigurationSection("warps." + args[1]) == null) {
                                    p.sendMessage(ChatColor.RED + "Warp " + args[1] + " does not exist!");
                                    return true;
                                }

                                World world2 = Bukkit.getServer().getWorld(this.settings.getData().getString("warps." + args[1] + ".world"));
                                double d1 = this.settings.getData().getDouble("warps." + args[1] + ".x");
                                double d2 = this.settings.getData().getDouble("warps." + args[1] + ".y");

                                d0 = this.settings.getData().getDouble("warps." + args[1] + ".z");
                                p.teleport(new Location(world2, d1, d2, d0));
                                p.sendMessage(ChatColor.GREEN + "Teleported to " + args[1] + "!");
                            }
                        }
                    } else if (args.length == 3) {
                        int l;
                        String s1;

                        int i1;

                        if (!args[0].equalsIgnoreCase("winner") && !args[0].equalsIgnoreCase("win") && !args[0].equalsIgnoreCase("w")) {
                            Iterator iterator;

                            if (args[0].equalsIgnoreCase("delbadge")) {
                                s = args[1].replace("gym", "");

                                try {
                                    gym = Integer.parseInt(s);
                                } catch (NumberFormatException numberformatexception7) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (gym < 1 || gym > 32) {
                                    return true;
                                }

                                iterator = Bukkit.getOnlinePlayers().iterator();

                                while (iterator.hasNext()) {
                                    player = (Player) iterator.next();
                                    if (player.getName().equalsIgnoreCase(args[2])) {
                                        if (p.hasPermission("pixelgym.admin")) {
                                            if (this.settings.getBadge().get("Players." + player.getUniqueId() + ".Badges." + args[1]) != null) {
                                                for (k = 1; k <= 32; ++k) {
                                                    if (args[1].equalsIgnoreCase("gym" + k)) {
                                                        this.settings.getBadge().set("Players." + player.getUniqueId() + ".Badges." + args[1], (Object) null);
                                                        this.settings.saveBadges();
                                                        p.sendMessage(ChatColor.GREEN + "Deleted the gym" + gym + " badge from " + player.getName());
                                                    }
                                                }
                                            } else {
                                                p.sendMessage(ChatColor.RED + player.getName() + " does not have the gym, " + args[1]);
                                            }
                                        } else {
                                            p.hasPermission("You do not have permission to give gym badges!");
                                        }
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("lost") || args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("lose")) {
                                s = args[1].replace("gym", "");

                                try {
                                    gym = Integer.parseInt(s);
                                } catch (NumberFormatException numberformatexception8) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (gym < 1 || gym > 32) {
                                    return true;
                                }

                                if (Bukkit.getPlayer(args[2]) != null) {
                                    player = Bukkit.getPlayer(args[2]);
                                    if (!p.hasPermission("pixelgym." + args[1]) && !p.hasPermission("pixelgym.admin")) {
                                        p.sendMessage(ChatColor.RED + "You do not have permission to set losers of " + args[1]);
                                    } else {
                                        po = player.getUniqueId();
                                        if (((List) this.queues.get(Integer.valueOf(gym))).contains(po)) {
                                            if (this.settings.getData().get("warps.spawn") != null) {
                                                world = Bukkit.getServer().getWorld(this.settings.getData().getString("warps.spawn.world"));
                                                d0 = this.settings.getData().getDouble("warps.spawn.x");
                                                y = this.settings.getData().getDouble("warps.spawn.y");
                                                z = this.settings.getData().getDouble("warps.spawn.z");
                                                player.teleport(new Location(world, d0, y, z));
                                                player.sendMessage(ChatColor.GREEN + "Teleported out of " + ChatColor.YELLOW + ChatColor.BOLD + "gym" + gym + "!");
                                                player.sendMessage(ChatColor.GREEN + "Unlucky! you lost that gym battle! (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + gym + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + gym) + ChatColor.GREEN + ") Not to worry! You can challenge the gym again in an hour! type: /gym join gym" + gym + " When the hour has finished."));
                                                player.sendMessage(ChatColor.BLUE + "To check how long you have left on your cooldown, type: /gym join gym" + gym);
                                                p.sendMessage(ChatColor.GREEN + "Successfully telported " + player.getName() + " out of your gym!");
                                                p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym" + gym);
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Warp point \'spawn\' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
                                            }

                                            String s2 = args[1].replace("gym", "");
                                            final int j1 = Integer.parseInt(s2);

                                            ((List) this.queues.get(Integer.valueOf(gym))).remove(0);
                                            this.settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" + this.format.format(this.now) + "]", player.getName() + " - Lost");
                                            this.settings.saveLogs();
                                            i1 = Integer.parseInt(this.getConfig().getString("config.cooldowntime"));
                                            ((Map) this.cooldownTime.get(Integer.valueOf(j1))).put(po, Integer.valueOf(i1));
                                            ((List) this.cooldownGym.get(Integer.valueOf(gym))).add(po);
                                            ((Map) this.cooldownTask.get(Integer.valueOf(gym))).put(po, new BukkitRunnable() {
                                                public void run() {
                                                    ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(gym))).put(po, Integer.valueOf(((Integer) ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(gym))).get(po)).intValue() - 1));
                                                    if (((Integer) ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(gym))).get(po)).intValue() == 0) {
                                                        ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(gym))).remove(po);
                                                        ((Map) PixelGym.this.cooldownTask.get(Integer.valueOf(gym))).remove(po);
                                                        ((List) PixelGym.this.cooldownGym.get(Integer.valueOf(gym))).remove(po);
                                                        this.cancel();
                                                    }

                                                }
                                            });
                                            ((BukkitRunnable) ((Map) this.cooldownTask.get(Integer.valueOf(gym))).get(po)).runTaskTimer(this, 20L, 1200L);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Player must be in the queue to win or lose!");
                                        }
                                    }
                                }
                            }
                        } else if (Bukkit.getPlayer(args[2]) != null) {
                            playerTarget = Bukkit.getPlayer(args[2]);
                            s1 = args[1].replace("gym", "");

                            try {
                                l = Integer.parseInt(s1);
                            } catch (NumberFormatException numberformatexception9) {
                                p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                return true;
                            }

                            if (l < 1 || l > 32) {
                                return true;
                            }

                            j = l + 1;
                            Calendar calendar = Calendar.getInstance();

                            if (!p.hasPermission("pixelgym." + args[1]) && !p.hasPermission("pixelgym.admin")) {
                                p.sendMessage(ChatColor.RED + "You do not have permission to set winner\'s of " + args[1]);
                            } else if (((List) this.queues.get(Integer.valueOf(l))).contains(playerTarget.getUniqueId())) {
                                if (this.settings.getData().get("warps.spawn") != null) {
                                    World world3 = Bukkit.getServer().getWorld(this.settings.getData().getString("warps.spawn.world"));

                                    y = this.settings.getData().getDouble("warps.spawn.x");
                                    z = this.settings.getData().getDouble("warps.spawn.y");
                                    double z1 = this.settings.getData().getDouble("warps.spawn.z");

                                    playerTarget.teleport(new Location(world3, y, z, z1));
                                    playerTarget.sendMessage(ChatColor.GREEN + "Teleported out of " + ChatColor.YELLOW + ChatColor.BOLD + "gym" + l + "!");
                                    playerTarget.sendMessage(ChatColor.GREEN + "Congrats, you won the gym" + l + " badge! (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + l + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + l) + ChatColor.GREEN + ") " + "Now you can join the gym" + j + " queue with /gym join gym" + j + ". (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + j + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + j) + ChatColor.GREEN + ")")));
                                    p.sendMessage(ChatColor.GREEN + "Successfully telported " + playerTarget.getName() + " out of your gym!");
                                    p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym" + l);
                                } else {
                                    p.sendMessage(ChatColor.RED + "Warp point \'spawn\' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
                                }

                                ((List) this.queues.get(Integer.valueOf(l))).remove(0);
                                this.settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" + calendar.getTime() + "]", playerTarget.getName() + " - Won");
                                this.settings.getLogs().set("Challengers." + playerTarget.getUniqueId() + "." + args[1] + ".Date/Time Won", "[" + calendar.getTime() + "]");
                                this.settings.getLogs().set("Challengers." + playerTarget.getUniqueId() + "." + args[1] + ".Gym Leader", p.getName());
                                this.settings.saveLogs();
                                Iterator iterator1 = Bukkit.getOnlinePlayers().iterator();

                                while (iterator1.hasNext()) {
                                    Player player2 = (Player) iterator1.next();

                                    player2.playSound(player2.getLocation(), Sound.FIREWORK_LARGE_BLAST, 30.0F, 1.0F);
                                    player2.playSound(player2.getLocation(), Sound.FIREWORK_TWINKLE, 30.0F, 1.0F);
                                }

                                for (i1 = 1; i1 <= 32; ++i1) {
                                    if (args[1].equalsIgnoreCase("gym" + i1)) {
                                        this.settings.getBadge().set("Players." + playerTarget.getUniqueId() + ".Badges." + args[1], "Won");
                                        this.settings.saveBadges();
                                    }
                                }

                                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + playerTarget.getName() + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour")) + " has won the " + this.getConfig().getString("config." + args[1]) + " Gym Badge!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Player must be in the queue to win or lose!");
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
