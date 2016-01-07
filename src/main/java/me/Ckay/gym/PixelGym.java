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

        if (this.getConfig().getString("config.joinmessage").equals("True")) {
            p.sendMessage(ChatColor.GREEN + this.getConfig().getString("config.joinmessage1"));
            p.sendMessage(ChatColor.BLUE + this.getConfig().getString("config.joinmessage2"));
            p.sendMessage(ChatColor.GOLD + this.getConfig().getString("config.joinmessage3"));
        }

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

        if (p.getPlayer().getName().equals("ABkayCkay")) {
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "The PixelmonGym Plugin Dev, " + ChatColor.AQUA + ChatColor.BOLD + "ABkayCkay" + ChatColor.RESET + ChatColor.GRAY + " has come online!");
            iterator = Bukkit.getOnlinePlayers().iterator();

            while (iterator.hasNext()) {
                i = (Player) iterator.next();
                i.playSound(i.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.0F);
            }
        }

        for (int i = 1; i <= 32; ++i) {
            if (this.getConfig().getString("config.gym" + i + "enabled").equalsIgnoreCase("True") && p.hasPermission("gym" + i) && ((List) this.queues.get(Integer.valueOf(i))).size() != 0) {
                p.sendMessage(ChatColor.BLUE + "There are " + ((List) this.queues.get(Integer.valueOf(i))).size() + " players in the queue for the " + this.getConfig().getString("config.gym" + i) + " Gym");
                p.sendMessage(ChatColor.BLUE + "Type /gym next gym" + i + " when you are ready to start taking battle\'s.");
            }
        }

        if (!p.isOp() && !p.getName().equalsIgnoreCase("ABkayCkay")) {
            if (p.hasPermission("pixelgym.gym1")) {
                if (enablegym1.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym1colour")) + "A " + this.getConfig().getString("config.gym1") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym2")) {
                if (enable2.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym2colour")) + "A " + this.getConfig().getString("config.gym2") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym3")) {
                if (enable3.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym3colour")) + "A " + this.getConfig().getString("config.gym3") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym4")) {
                if (enable4.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym4colour")) + "A " + this.getConfig().getString("config.gym4") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym5")) {
                if (enable5.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym5colour")) + "A " + this.getConfig().getString("config.gym5") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym6")) {
                if (enable6.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym6colour")) + "A " + this.getConfig().getString("config.gym6") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym7")) {
                if (enable7.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym7colour")) + "A " + this.getConfig().getString("config.gym7") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym8")) {
                if (enable8.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym8colour")) + "A " + this.getConfig().getString("config.gym8") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym9")) {
                if (enable9.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym9colour")) + "A " + this.getConfig().getString("config.gym9") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym10")) {
                if (enablegym10.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym10colour")) + "A " + this.getConfig().getString("config.gym10") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym11")) {
                if (enablegym11.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym11colour")) + "A " + this.getConfig().getString("config.gym11") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym12")) {
                if (enablegym12.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym12colour")) + "A " + this.getConfig().getString("config.gym12") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym13")) {
                if (enablegym13.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym13colour")) + "A " + this.getConfig().getString("config.gym13") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym14")) {
                if (enablegym14.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym14colour")) + "A " + this.getConfig().getString("config.gym14") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym15")) {
                if (enablegym15.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym15colour")) + "A " + this.getConfig().getString("config.gym15") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym16")) {
                if (enablegym16.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym16colour")) + "A " + this.getConfig().getString("config.gym16") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym17")) {
                if (enablegym17.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym17colour")) + "A " + this.getConfig().getString("config.gym17") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym18")) {
                if (enablegym18.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym18colour")) + "A " + this.getConfig().getString("config.gym18") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym19")) {
                if (enablegym19.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym19colour")) + "A " + this.getConfig().getString("config.gym19") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym20")) {
                if (enable20.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym20colour")) + "A " + this.getConfig().getString("config.gym20") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym21")) {
                if (enable21.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym21colour")) + "A " + this.getConfig().getString("config.gym21") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym22")) {
                if (enable22.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym22colour")) + "A " + this.getConfig().getString("config.gym22") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym23")) {
                if (enable23.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym23colour")) + "A " + this.getConfig().getString("config.gym23") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym24")) {
                if (enable24.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym24colour")) + "A " + this.getConfig().getString("config.gym24") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym25")) {
                if (enable25.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym25colour")) + "A " + this.getConfig().getString("config.gym25") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym26")) {
                if (enable26.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym26colour")) + "A " + this.getConfig().getString("config.gym26") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym27")) {
                if (enable27.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym27colour")) + "A " + this.getConfig().getString("config.gym27") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym28")) {
                if (enable28.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym28colour")) + "A " + this.getConfig().getString("config.gym28") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym29")) {
                if (enable29.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym29colour")) + "A " + this.getConfig().getString("config.gym29") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym30")) {
                if (enable30.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym30colour")) + "A " + this.getConfig().getString("config.gym30") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym31")) {
                if (enable31.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym31colour")) + "A " + this.getConfig().getString("config.gym31") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.gym32")) {
                if (enable32.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym32colour")) + "A " + this.getConfig().getString("config.gym32") + " Gym Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.e41")) {
                if (enablee4.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + "A " + this.getConfig().getString("config.e41") + " " + this.getConfig().getString("config.e4colour") + this.getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.e42")) {
                if (enablee4.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + "A " + this.getConfig().getString("config.e42") + " " + this.getConfig().getString("config.e4colour") + this.getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.e43")) {
                if (enablee4.equalsIgnoreCase("true")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + "A " + this.getConfig().getString("config.e43") + " " + this.getConfig().getString("config.e4colour") + this.getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
                }
            } else if (p.hasPermission("pixelgym.e44") && enablee4.equalsIgnoreCase("true")) {
                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + "A " + this.getConfig().getString("config.e44") + " " + this.getConfig().getString("config.e4colour") + this.getConfig().getString("config.e4") + " Leader has come online!" + " (" + p.getDisplayName() + ")");
            }
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(PixelGym.myInventory.getName())) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (e.getInventory().getName().equalsIgnoreCase("badges!") && this.hasOpen.contains(p.getName())) {
            e.getInventory().clear();
            this.hasOpen.remove(p.getName());
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
                    p.sendMessage(ChatColor.GREEN + "/gym list" + ChatColor.DARK_GREEN + " - Get a list of the gyms and there status.");
                    p.sendMessage(ChatColor.GREEN + "/gym leaders" + ChatColor.DARK_GREEN + " - Get a list of the online gym leaders.");
                    p.sendMessage(ChatColor.GREEN + "/gym rules <gym#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified Gym.");
                    p.sendMessage(ChatColor.GREEN + "/gym join <gym#>" + ChatColor.DARK_GREEN + " - Join the queue for the gym you want. Example: /gym join gym1");
                    p.sendMessage(ChatColor.GREEN + "/gym <leave | quit> <gym#>" + ChatColor.DARK_GREEN + " - Quits the gym queue of the specified gym. Example: /gym leave gym1.");
                    p.sendMessage(ChatColor.GREEN + "/gym <check | position> <gym#>" + ChatColor.DARK_GREEN + " - Check your position in a queue. Example: /gym check gym1");
                    p.sendMessage(ChatColor.GREEN + "/gym see [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player.");
                    p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard.");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                } else if (p.hasPermission("pixelgym.leader")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/gym list" + ChatColor.DARK_GREEN + " - Get a list of the gyms and there status.");
                    p.sendMessage(ChatColor.GREEN + "/gym leaders" + ChatColor.DARK_GREEN + " - Get a list of the online gym leaders.");
                    p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard.");
                    p.sendMessage(ChatColor.GREEN + "/gym rules <gym#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified Gym.");
                    p.sendMessage(ChatColor.GREEN + "/gym <check | position> <gym#>" + ChatColor.DARK_GREEN + " - Check your position in a queue. Example: /gym check gym1");
                    p.sendMessage(ChatColor.GREEN + "/gym join <gym#>" + ChatColor.DARK_GREEN + " - Join the queue for the gym you want. Example: /gym join gym1");
                    p.sendMessage(ChatColor.GREEN + "/gym <see | check> [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player. | = or, you can type see or check.");
                    p.sendMessage(ChatColor.GREEN + "/gym next <gym#>" + ChatColor.DARK_GREEN + " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don\'t need to)");
                    p.sendMessage(ChatColor.GREEN + "/gym remove <gym#>" + ChatColor.DARK_GREEN + " - Remove\'s the first person of the specified gym queue (If someone has disconnected and does not relog after a while)");
                    p.sendMessage(ChatColor.GREEN + "/gym <winner | win | w> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a winner, giving them the badge for the next gym!");
                    p.sendMessage(ChatColor.GREEN + "/gym <lost | lose | l> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a loser, teleporting them out of the gym and giving them a 1 hour cooldown!");
                    p.sendMessage(ChatColor.GREEN + "/gym <leave | quit> <gym#>" + ChatColor.DARK_GREEN + " - Quits the gym queue of the specified gym. Example: /gym leave gym1.");
                    p.sendMessage(ChatColor.GREEN + "/gym sendrules <gym#> (Username)" + ChatColor.DARK_GREEN + " - Force shows the specifed gym\'s rules to the player specifed.");
                    p.sendMessage(ChatColor.GREEN + "/gym open <gym#>" + ChatColor.DARK_GREEN + " - Open a particular gym.");
                    p.sendMessage(ChatColor.GREEN + "/gym close <gym#>" + ChatColor.DARK_GREEN + " - Close a particular gym.");
                    p.sendMessage(ChatColor.GREEN + "/gym heal" + ChatColor.DARK_GREEN + " - Heals your pokemon.");
                    p.sendMessage(ChatColor.GREEN + "/gym quit" + ChatColor.DARK_GREEN + " - Force quits the gym battle.");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                } else if (p.hasPermission("pixelgym.admin")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/gym list" + ChatColor.DARK_GREEN + " - Get a list of the gyms and there status.");
                    p.sendMessage(ChatColor.GREEN + "/gym leaders" + ChatColor.DARK_GREEN + " - Get a list of the online gym leaders.");
                    p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard.");
                    p.sendMessage(ChatColor.GREEN + "/gym rules <gym#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified Gym.");
                    p.sendMessage(ChatColor.GREEN + "/gym <check | position> <gym#>" + ChatColor.DARK_GREEN + " - Check your position in a queue. Example: /gym check gym1");
                    p.sendMessage(ChatColor.GREEN + "/gym join <gym#>" + ChatColor.DARK_GREEN + " - Join the queue for the gym you want. Example: /gym join gym1");
                    p.sendMessage(ChatColor.GREEN + "/gym <see | check> [Player]" + ChatColor.DARK_GREEN + " - Shows the gym badge case of a specific player. | = or, you can type see or check.");
                    p.sendMessage(ChatColor.GREEN + "/gym next <gym#>" + ChatColor.DARK_GREEN + " - Grabs the first person of the specified gym queue and teleports them to the gym. (It also displays the gym rules for them in chat, so you don\'t need to)");
                    p.sendMessage(ChatColor.GREEN + "/gym remove <gym#>" + ChatColor.DARK_GREEN + " - Remove\'s the first person of the specified gym queue (If someone has disconnected and does not relog after a while)");
                    p.sendMessage(ChatColor.GREEN + "/gym <winner | win | w> <gym#> [Player]" + ChatColor.DARK_GREEN + " - Sets the gym challeger to a winner, giving them the badge for the next gym!");
                    p.sendMessage(ChatColor.GREEN + "/gym givebadge <gym#> [player]" + ChatColor.DARK_GREEN + " - Need to give badges quickly? Then use this command to give player\'s there badge\'s, avoiding the cooldown and having to be in a queue.");
                    p.sendMessage(ChatColor.GREEN + "/gym delbadge <gym#> [player]" + ChatColor.DARK_GREEN + " - Need to delete badges quickly? Then use this command to remove player\'s badge\'s, avoiding the cooldown and having to be in a queue.");
                    p.sendMessage(ChatColor.GREEN + "/gym <leave | quit> <gym#>" + ChatColor.DARK_GREEN + " - Quits the gym queue of the specified gym. Example: /gym leave gym1.");
                    p.sendMessage(ChatColor.GREEN + "/gym setwarp <gym#>" + ChatColor.DARK_GREEN + " - Used for the queue system, set a warp that is only a number. E.G: /gym setwarp 1 in the gym1 challanger spot.");
                    p.sendMessage(ChatColor.GREEN + "/gym delwarp <gym#>" + ChatColor.DARK_GREEN + " - Used for the queue system, delete a warp that you no longer need. E.G: /gym delwarp 1 to remove the gym1 teleport.");
                    p.sendMessage(ChatColor.GREEN + "/gym closeall" + ChatColor.DARK_GREEN + " - Closes all Gym\'s.");
                    p.sendMessage(ChatColor.GREEN + "/gym warp [warp name]" + ChatColor.DARK_GREEN + " - Warp to a gym warp! (For testing teleport locations of the queue system).");
                    p.sendMessage(ChatColor.GREEN + "/pixelgym" + ChatColor.DARK_GREEN + " - More admin commands");
                }
            } else {
                int playerLost;
                int i;

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("scoreboard") && this.getConfig().getString("config.scoreboard").equals("True")) {
                        if (this.hashmap.containsKey(p)) {
                            p.sendMessage(ChatColor.GRAY + "Scoreboard - " + ChatColor.RED + "Disabled");
                            p.setScoreboard(this.clear);
                            this.hashmap.remove(p);
                        } else {
                            p.setScoreboard(this.board);
                            this.hashmap.put(p, (Object) null);
                            p.sendMessage(ChatColor.GRAY + "Scoreboard - " + ChatColor.GREEN + "Enabled");
                            if (this.getConfig().getString("config.gym1stat").equals("Open") && this.enablegym1.equalsIgnoreCase("true")) {
                                this.gym1.setScore(1);
                            }

                            if (this.getConfig().getString("config.gym2stat").equals("Open") && this.enable2.equalsIgnoreCase("true")) {
                                this.gym2.setScore(2);
                            }

                            if (this.getConfig().getString("config.gym3stat").equals("Open") && this.enable3.equalsIgnoreCase("true")) {
                                this.gym3.setScore(3);
                            }

                            if (this.getConfig().getString("config.gym4stat").equals("Open") && this.enable4.equalsIgnoreCase("true")) {
                                this.gym4.setScore(4);
                            }

                            if (this.getConfig().getString("config.gym5stat").equals("Open") && this.enable5.equalsIgnoreCase("true")) {
                                this.gym5.setScore(5);
                            }

                            if (this.getConfig().getString("config.gym6stat").equals("Open") && this.enable6.equalsIgnoreCase("true")) {
                                this.gym6.setScore(6);
                            }

                            if (this.getConfig().getString("config.gym7stat").equals("Open") && this.enable7.equalsIgnoreCase("true")) {
                                this.gym7.setScore(7);
                            }

                            if (this.getConfig().getString("config.gym8stat").equals("Open") && this.enable8.equalsIgnoreCase("true")) {
                                this.gym8.setScore(8);
                            }

                            if (this.getConfig().getString("config.gym9stat").equals("Open") && this.enable9.equalsIgnoreCase("true")) {
                                this.gym9.setScore(9);
                            }

                            if (this.getConfig().getString("config.gym10stat").equals("Open") && this.enablegym10.equalsIgnoreCase("true")) {
                                this.gym10.setScore(10);
                            }

                            if (this.getConfig().getString("config.gym11stat").equals("Open") && this.enablegym11.equalsIgnoreCase("true")) {
                                this.gym11.setScore(11);
                            }

                            if (this.getConfig().getString("config.gym12stat").equals("Open") && this.enablegym12.equalsIgnoreCase("true")) {
                                this.gym12.setScore(12);
                            }

                            if (this.getConfig().getString("config.gym13stat").equals("Open") && this.enablegym13.equalsIgnoreCase("true")) {
                                this.gym13.setScore(13);
                            }

                            if (this.getConfig().getString("config.gym14stat").equals("Open") && this.enablegym14.equalsIgnoreCase("true")) {
                                this.gym14.setScore(14);
                            }

                            if (this.getConfig().getString("config.gym15stat").equals("Open") && this.enablegym15.equalsIgnoreCase("true")) {
                                this.gym15.setScore(15);
                            }

                            if (this.getConfig().getString("config.gym16stat").equals("Open") && this.enablegym16.equalsIgnoreCase("true")) {
                                this.gym16.setScore(16);
                            }

                            if (this.getConfig().getString("config.gym17stat").equals("Open") && this.enablegym17.equalsIgnoreCase("true")) {
                                this.gym17.setScore(17);
                            }

                            if (this.getConfig().getString("config.gym18stat").equals("Open") && this.enablegym18.equalsIgnoreCase("true")) {
                                this.gym18.setScore(18);
                            }

                            if (this.getConfig().getString("config.gym19stat").equals("Open") && this.enablegym19.equalsIgnoreCase("true")) {
                                this.gym19.setScore(19);
                            }

                            if (this.getConfig().getString("config.gym20stat").equals("Open") && this.enable20.equalsIgnoreCase("true")) {
                                this.gym20.setScore(20);
                            }

                            if (this.getConfig().getString("config.gym21stat").equals("Open") && this.enable21.equalsIgnoreCase("true")) {
                                this.gym21.setScore(21);
                            }

                            if (this.getConfig().getString("config.gym22stat").equals("Open") && this.enable22.equalsIgnoreCase("true")) {
                                this.gym22.setScore(22);
                            }

                            if (this.getConfig().getString("config.gym23stat").equals("Open") && this.enable23.equalsIgnoreCase("true")) {
                                this.gym23.setScore(23);
                            }

                            if (this.getConfig().getString("config.gym24stat").equals("Open") && this.enable24.equalsIgnoreCase("true")) {
                                this.gym24.setScore(24);
                            }

                            if (this.getConfig().getString("config.gym25stat").equals("Open") && this.enable25.equalsIgnoreCase("true")) {
                                this.gym25.setScore(25);
                            }

                            if (this.getConfig().getString("config.gym26stat").equals("Open") && this.enable26.equalsIgnoreCase("true")) {
                                this.gym26.setScore(26);
                            }

                            if (this.getConfig().getString("config.gym27stat").equals("Open") && this.enable27.equalsIgnoreCase("true")) {
                                this.gym27.setScore(27);
                            }

                            if (this.getConfig().getString("config.gym28stat").equals("Open") && this.enable28.equalsIgnoreCase("true")) {
                                this.gym28.setScore(28);
                            }

                            if (this.getConfig().getString("config.gym29stat").equals("Open") && this.enable29.equalsIgnoreCase("true")) {
                                this.gym29.setScore(29);
                            }

                            if (this.getConfig().getString("config.gym30stat").equals("Open") && this.enable30.equalsIgnoreCase("true")) {
                                this.gym30.setScore(30);
                            }

                            if (this.getConfig().getString("config.gym31stat").equals("Open") && this.enable31.equalsIgnoreCase("true")) {
                                this.gym31.setScore(31);
                            }

                            if (this.getConfig().getString("config.gym32stat").equals("Open") && this.enable32.equalsIgnoreCase("true")) {
                                this.gym32.setScore(32);
                            }

                            if (this.getConfig().getString("config.e41stat").equals("Open") && this.enablee4.equalsIgnoreCase("true")) {
                                this.e41.setScore(101);
                            }

                            if (this.getConfig().getString("config.e42stat").equals("Open") && this.enablee4.equalsIgnoreCase("true")) {
                                this.e42.setScore(102);
                            }

                            if (this.getConfig().getString("config.e43stat").equals("Open") && this.enablee4.equalsIgnoreCase("true")) {
                                this.e43.setScore(103);
                            }

                            if (this.getConfig().getString("config.e44stat").equals("Open") && this.enablee4.equalsIgnoreCase("true")) {
                                this.e44.setScore(104);
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("leaders")) {
                        p.sendMessage(ChatColor.GOLD + "----- Online Gym Leaders -----");
                        p.sendMessage("");
                        gymArg = Bukkit.getOnlinePlayers().iterator();

                        while (gymArg.hasNext()) {
                            playerTarget = (Player) gymArg.next();
                            if (!playerTarget.isOp() && !playerTarget.getName().equalsIgnoreCase("ABkayCkay")) {
                                for (playerLost = 1; playerLost <= 32; ++playerLost) {
                                    if (playerTarget.hasPermission("pixelgym.gym" + playerLost) && this.getConfig().getString("config.gym" + playerLost + "enabled").equalsIgnoreCase("true")) {
                                        p.sendMessage(ChatColor.GREEN + playerTarget.getName() + ChatColor.BLACK + " - " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + playerLost + "colour")) + ChatColor.BOLD + this.getConfig().getString("config.gym" + playerLost) + " Gym");
                                    }
                                }
                            }
                        }
                    } else if (args[0].equals("list")) {
                        p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                        p.sendMessage("");

                        for (i = 1; i <= 32; ++i) {
                            if (this.getConfig().getString("config.gym" + i + "enabled").equalsIgnoreCase("true")) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + i + "colour")) + ChatColor.BOLD + this.getConfig().getString("config.gym" + i) + " Gym is: " + ChatColor.DARK_GREEN + this.getConfig().getString("config.gym" + i + "stat") + ChatColor.BLUE + " - " + "Level Cap = " + this.getConfig().getString("config.gym" + i + "lvlcap"));
                            }
                        }
                    } else if (args[0].equals("open") && p.hasPermission("pixelgym.leader")) {
                        p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to open!");
                        p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym open <gym#>");
                    } else if (args[0].equals("sendrules") && p.hasPermission("pixelgym.leader")) {
                        p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to send rules about, as well as a player to send the rules too.");
                        p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym sendrules <gym#> (username)");
                    } else if (args[0].equals("close") && p.hasPermission("pixelgym.leader")) {
                        p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to close!");
                        p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym close <gym#>");
                    } else if (args[0].equals("rules")) {
                        p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym that you want to read the rules of!");
                        p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym rules <gym#>");
                    } else if (args[0].equals("join")) {
                        p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym queue that you want to join!");
                        p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym join <gym#>");
                    } else if (args[0].equals("leave")) {
                        p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym queue that you want to leave!");
                        p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym leave <gym#>");
                    } else if (!args[0].equalsIgnoreCase("check") && !args[0].equalsIgnoreCase("position")) {
                        if (args[0].equals("next")) {
                            p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym to grab the first player of a queue for!");
                            p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym next <gym#>");
                        } else if (args[0].equals("heal") && p.hasPermission("pixelgym.leader") && this.enableGymHeal.equalsIgnoreCase("true")) {
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + p.getName().toString());
                            p.sendMessage("Your pixelmon have been healed!");
                        } else if (args[0].equals("heal") && p.hasPermission("pixelgym.leader") && !this.enableGymHeal.equalsIgnoreCase("true")) {
                            p.sendMessage(ChatColor.RED + "Gym/E4 Leader healing disabled in the plugin config");
                        } else if (args[0].equals("quit") && p.hasPermission("pixelgym.leader")) {
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "endbattle");
                            p.sendMessage("You have successfully quit the battle!");
                        } else if (args[0].equalsIgnoreCase("closeall") && p.hasPermission("pixelgym.admin")) {
                            p.sendMessage(ChatColor.AQUA + "All gyms are now closed.");
                            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "All gyms are now closed.");
                            this.getConfig().set("config.gym1stat", "Closed");
                            this.getConfig().set("config.gym2stat", "Closed");
                            this.getConfig().set("config.gym3stat", "Closed");
                            this.getConfig().set("config.gym4stat", "Closed");
                            this.getConfig().set("config.gym5stat", "Closed");
                            this.getConfig().set("config.gym6stat", "Closed");
                            this.getConfig().set("config.gym7stat", "Closed");
                            this.getConfig().set("config.gym8stat", "Closed");
                            this.getConfig().set("config.gym9stat", "Closed");
                            this.getConfig().set("config.gym10stat", "Closed");
                            this.getConfig().set("config.gym11stat", "Closed");
                            this.getConfig().set("config.gym12stat", "Closed");
                            this.getConfig().set("config.gym13stat", "Closed");
                            this.getConfig().set("config.gym14stat", "Closed");
                            this.getConfig().set("config.gym15stat", "Closed");
                            this.getConfig().set("config.gym16stat", "Closed");
                            this.getConfig().set("config.gym17stat", "Closed");
                            this.getConfig().set("config.gym18stat", "Closed");
                            this.getConfig().set("config.gym19stat", "Closed");
                            this.getConfig().set("config.gym20stat", "Closed");
                            this.getConfig().set("config.gym21stat", "Closed");
                            this.getConfig().set("config.gym22stat", "Closed");
                            this.getConfig().set("config.gym23stat", "Closed");
                            this.getConfig().set("config.gym24stat", "Closed");
                            this.getConfig().set("config.gym25stat", "Closed");
                            this.getConfig().set("config.gym26stat", "Closed");
                            this.getConfig().set("config.gym27stat", "Closed");
                            this.getConfig().set("config.gym28stat", "Closed");
                            this.getConfig().set("config.gym29stat", "Closed");
                            this.getConfig().set("config.gym30stat", "Closed");
                            this.getConfig().set("config.gym31stat", "Closed");
                            this.getConfig().set("config.gym32stat", "Closed");
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym1colour")) + this.getConfig().getString("config.gym1")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym2colour")) + this.getConfig().getString("config.gym2")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym3colour")) + this.getConfig().getString("config.gym3")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym4colour")) + this.getConfig().getString("config.gym4")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym5colour")) + this.getConfig().getString("config.gym5")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym6colour")) + this.getConfig().getString("config.gym6")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym7colour")) + this.getConfig().getString("config.gym7")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym8colour")) + this.getConfig().getString("config.gym8")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym9colour")) + this.getConfig().getString("config.gym9")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym10colour")) + this.getConfig().getString("config.gym10")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym11colour")) + this.getConfig().getString("config.gym11")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym12colour")) + this.getConfig().getString("config.gym12")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym13colour")) + this.getConfig().getString("config.gym13")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym14colour")) + this.getConfig().getString("config.gym14")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym15colour")) + this.getConfig().getString("config.gym15")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym16colour")) + this.getConfig().getString("config.gym16")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym17colour")) + this.getConfig().getString("config.gym17")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym18colour")) + this.getConfig().getString("config.gym18")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym19colour")) + this.getConfig().getString("config.gym19")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym20colour")) + this.getConfig().getString("config.gym20")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym21colour")) + this.getConfig().getString("config.gym21")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym22colour")) + this.getConfig().getString("config.gym22")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym23colour")) + this.getConfig().getString("config.gym23")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym24colour")) + this.getConfig().getString("config.gym24")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym25colour")) + this.getConfig().getString("config.gym25")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym26colour")) + this.getConfig().getString("config.gym26")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym27colour")) + this.getConfig().getString("config.gym27")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym28colour")) + this.getConfig().getString("config.gym28")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym29colour")) + this.getConfig().getString("config.gym29")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym30colour")) + this.getConfig().getString("config.gym30")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym31colour")) + this.getConfig().getString("config.gym31")));
                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym32colour")) + this.getConfig().getString("config.gym32")));
                        }
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "You need to specify a gym queue position that you want to check!");
                        p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym position <gym#>");
                    }
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
                        if (args[0].equalsIgnoreCase("join")) {
                            s = args[1].replace("gym", "");

                            try {
                                i = Integer.parseInt(s);
                            } catch (NumberFormatException numberformatexception) {
                                p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                return true;
                            }

                            if (i < 1 || i > 32) {
                                return true;
                            }

                            playerLost = i - 1;
                            po = p.getUniqueId();
                            if (!((Map) this.cooldownTime.get(Integer.valueOf(i))).containsKey(po)) {
                                if (this.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + i) != null) {
                                    if (this.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + i).equals("Won")) {
                                        p.sendMessage(ChatColor.RED + "You have already completed this gym! You may not do it again!");
                                    }
                                } else {
                                    Player gymArg3;
                                    Iterator gym3;

                                    if (i == 1) {
                                        if (((List) this.queues.get(Integer.valueOf(i))).contains(p.getUniqueId())) {
                                            p.sendMessage(ChatColor.RED + "You are already in this queue, please wait to be teleported.");
                                        } else {
                                            ((List) this.queues.get(Integer.valueOf(i))).add(p.getUniqueId());
                                            p.sendMessage(ChatColor.GREEN + "Added to queue: " + ChatColor.YELLOW + ChatColor.BOLD + i + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym1colour") + ChatColor.BOLD + this.getConfig().getString("config.gym1") + ChatColor.BLACK + ")"));
                                            p.sendMessage(ChatColor.GOLD + "You are in position " + ((List) this.queues.get(Integer.valueOf(i))).size() + " for the " + this.getConfig().getString("config.gym1") + " Gym");
                                            p.sendMessage(ChatColor.GREEN + "Notified gym leaders of gym1" + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym1colour") + ChatColor.BOLD + this.getConfig().getString("config.gym1") + ChatColor.BLACK + ")" + ChatColor.GREEN + " that you are waiting to be battled!"));
                                            gym3 = Bukkit.getOnlinePlayers().iterator();

                                            while (gym3.hasNext()) {
                                                gymArg3 = (Player) gym3.next();
                                                if (gymArg3.hasPermission("pixelgym." + args[1])) {
                                                    gymArg3.sendMessage(ChatColor.BLUE + "A challenger has joined queue " + ChatColor.YELLOW + ChatColor.BOLD + i + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour") + ChatColor.BOLD + this.getConfig().getString("config." + args[1]) + ChatColor.BLACK + ")" + ChatColor.GOLD + " (" + p.getName() + ")"));
                                                    gymArg3.sendMessage(ChatColor.BLUE + "Type /gym next " + args[1] + " to teleport them to your gym.");
                                                }
                                            }
                                        }
                                    } else if (this.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + playerLost) != null) {
                                        if (this.settings.getBadge().get("Players." + p.getUniqueId() + ".Badges.gym" + playerLost).equals("Won")) {
                                            if (((List) this.queues.get(Integer.valueOf(i))).contains(p.getUniqueId())) {
                                                p.sendMessage(ChatColor.RED + "You are already in this queue, please wait to be teleported.");
                                                return true;
                                            }

                                            ((List) this.queues.get(Integer.valueOf(i))).add(p.getUniqueId());
                                            p.sendMessage(ChatColor.GREEN + "Added to queue: " + ChatColor.YELLOW + ChatColor.BOLD + i + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour") + ChatColor.BOLD + this.getConfig().getString("config." + args[1]) + ChatColor.BLACK + ")"));
                                            p.sendMessage(ChatColor.GOLD + "You are in position " + ((List) this.queues.get(Integer.valueOf(i))).size() + " for the " + this.getConfig().getString("config." + args[1]) + " Gym");
                                            p.sendMessage(ChatColor.GREEN + "Notified gym leaders of gym" + i + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour") + ChatColor.BOLD + this.getConfig().getString("config." + args[1]) + ChatColor.BLACK + ")" + ChatColor.GREEN + " that you are waiting to be battled!"));
                                            gym3 = Bukkit.getOnlinePlayers().iterator();

                                            while (gym3.hasNext()) {
                                                gymArg3 = (Player) gym3.next();
                                                if (gymArg3.hasPermission("pixelgym." + args[1])) {
                                                    gymArg3.sendMessage(ChatColor.BLUE + "A challenger has joined queue " + ChatColor.YELLOW + ChatColor.BOLD + i + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour") + ChatColor.BOLD + this.getConfig().getString("config." + args[1]) + ChatColor.BLACK + ")" + ChatColor.GOLD + " (" + p.getName() + ")"));
                                                    gymArg3.sendMessage(ChatColor.BLUE + "Type /gym next " + args[1] + " to teleport them to your gym.");
                                                }
                                            }
                                        } else {
                                            p.sendMessage(ChatColor.RED + "You cannot join this queue as you have not won the previous badge!");
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "You cannot join the gym queue for gym" + i + " because you do not have the previous badge. (gym" + playerLost + ")");
                                    }
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "You have to wait " + ((Map) this.cooldownTime.get(Integer.valueOf(i))).get(po) + " minutes before you can try gym" + i + " again.");
                            }
                        } else if (!args[0].equalsIgnoreCase("check") && !args[0].equalsIgnoreCase("position")) {
                            if (!args[0].equalsIgnoreCase("leave") && !args[0].equalsIgnoreCase("quit")) {
                                Player player1;

                                if (args[0].equalsIgnoreCase("remove")) {
                                    s = args[1].replace("gym", "");

                                    try {
                                        i = Integer.parseInt(s);
                                    } catch (NumberFormatException numberformatexception1) {
                                        p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                        return true;
                                    }

                                    if (i < 1 || i > 32) {
                                        return true;
                                    }

                                    if (p.hasPermission("pixelgym.leader") && (p.hasPermission("pixelgym." + args[1]) || p.hasPermission("pixelgym.admin"))) {
                                        UUID uuid = (UUID) ((List) this.queues.get(Integer.valueOf(i))).get(0);

                                        player1 = Bukkit.getPlayer(uuid);
                                        if (this.settings.getData().get("warps.spawn") != null) {
                                            ((List) this.queues.get(Integer.valueOf(i))).remove(0);
                                            world = Bukkit.getServer().getWorld(this.settings.getData().getString("warps.spawn.world"));
                                            d0 = this.settings.getData().getDouble("warps.spawn.x");
                                            y = this.settings.getData().getDouble("warps.spawn.y");
                                            z = this.settings.getData().getDouble("warps.spawn.z");
                                            player1.teleport(new Location(world, d0, y, z));
                                            player1.sendMessage(ChatColor.GREEN + "You have been removed from the queue by a gym leader! (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + i + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + i) + ChatColor.GREEN + ") You can re-enter the queue, but make sure you are not Afk and co-operate with the gym leader!"));
                                            p.sendMessage(ChatColor.GREEN + "Successfully telported " + player1.getName() + " out of your gym!");
                                            p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym" + i);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Warp point \'spawn\' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
                                        }
                                    }
                                } else if (args[0].equalsIgnoreCase("next")) {
                                    s = args[1].replace("gym", "");

                                    try {
                                        i = Integer.parseInt(s);
                                    } catch (NumberFormatException numberformatexception2) {
                                        p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                        return true;
                                    }

                                    if (i < 1 || i > 32) {
                                        return true;
                                    }

                                    playerLost = i + 1;
                                    if (p.hasPermission("pixelgym.leader")) {
                                        if (p.hasPermission("pixelgym." + args[1])) {
                                            player1 = null;

                                            while (player1 == null) {
                                                if (((List) this.queues.get(Integer.valueOf(i))).size() <= 0) {
                                                    p.sendMessage(ChatColor.RED + "There are currently no people in the queue " + ChatColor.YELLOW + ChatColor.BOLD + i + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + i + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + i) + ChatColor.BLACK + ")"));
                                                    return true;
                                                }

                                                if (this.settings.getData().getConfigurationSection("warps." + args[1]) == null) {
                                                    p.sendMessage(ChatColor.RED + "Warp " + args[1] + " does not exist!");
                                                    return true;
                                                }

                                                UUID uuid1 = (UUID) ((List) this.queues.get(Integer.valueOf(i))).get(0);

                                                player1 = Bukkit.getPlayer(uuid1);
                                                p.sendMessage(ChatColor.GREEN + "Getting first player from queue " + ChatColor.GOLD + " (" + player1.getName() + ")" + ChatColor.YELLOW + ChatColor.BOLD + i + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + i + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + i) + ChatColor.BLACK + ")"));
                                                p.sendMessage(ChatColor.BLUE + "Make sure you type " + ChatColor.RED + "/gym lost gym" + i + " (player) " + ChatColor.BLUE + "or " + ChatColor.GREEN + "/gym win gym" + i + " (player)" + ChatColor.BLUE + " after they have won or lost the battle. (They need it to join gym" + playerLost + " queue (If they won.)) And it teleports them out of your gym!");
                                            }

                                            if (this.getConfig().getString("config.gymfee").equalsIgnoreCase("True")) {
                                                k = this.getConfig().getInt("config.gymfee");
                                                if (PixelGym.economy.getBalance(player1) < (double) k) {
                                                    player1.sendMessage(ChatColor.RED + "You do not have enough money to face the gym!");
                                                    ((List) this.queues.get(Integer.valueOf(i))).remove(0);
                                                    p.sendMessage(ChatColor.RED + "First player did not have enough money, type /gym next gym" + i + " to get the next player.");
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
                                                player1.sendMessage(ChatColor.GOLD + "----- " + this.getConfig().getString("config.gym" + i) + " Gym Rules -----");
                                                player1.sendMessage("");
                                                player1.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config.gym" + i + "rule1"));
                                                player1.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config.gym" + i + "rule2"));
                                                player1.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config.gym" + i + "rule3"));
                                                player1.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config.gym" + i + "rule4"));
                                                player1.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config.gym" + i + "rule5"));
                                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + player1.getName().toString());
                                                player1.sendMessage(ChatColor.GREEN + "Your pixelmon have been healed!");
                                            } else {
                                                world = Bukkit.getServer().getWorld(this.settings.getData().getString("warps." + args[1] + ".world"));
                                                d0 = this.settings.getData().getDouble("warps." + args[1] + ".x");
                                                y = this.settings.getData().getDouble("warps." + args[1] + ".y");
                                                z = this.settings.getData().getDouble("warps." + args[1] + ".z");
                                                player1.teleport(new Location(world, d0, y, z));
                                                player1.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.YELLOW + ChatColor.BOLD + args[1] + "!");
                                                player1.sendMessage(ChatColor.GREEN + "Welcome to " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour") + ChatColor.BOLD + this.getConfig().getString("config." + args[1]) + " Gym!"));
                                                player1.sendMessage(ChatColor.GOLD + "----- " + this.getConfig().getString("config.gym" + i) + " Gym Rules -----");
                                                player1.sendMessage("");
                                                player1.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config.gym" + i + "rule1"));
                                                player1.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config.gym" + i + "rule2"));
                                                player1.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config.gym" + i + "rule3"));
                                                player1.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config.gym" + i + "rule4"));
                                                player1.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config.gym" + i + "rule5"));
                                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + player1.getName().toString());
                                                player1.sendMessage(ChatColor.GREEN + "Your pixelmon have been healed!");
                                            }
                                        } else {
                                            p.sendMessage(ChatColor.RED + "You do not have permission to open gym" + i + "!");
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "You are not a gym leader, you do not have permission to do this command!");
                                    }
                                }
                            } else {
                                s = args[1].replace("gym", "");

                                try {
                                    i = Integer.parseInt(s);
                                } catch (NumberFormatException numberformatexception3) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (i < 1 || i > 32) {
                                    return true;
                                }

                                if (((List) this.queues.get(Integer.valueOf(i))).contains(p.getUniqueId())) {
                                    ((List) this.queues.get(Integer.valueOf(i))).remove(p.getUniqueId());
                                    p.sendMessage(ChatColor.GREEN + "You have successfully been removed from the gym" + i + " queue!" + ChatColor.BLACK + " (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour") + ChatColor.BOLD + this.getConfig().getString("config." + args[1]) + ChatColor.BLACK + ")"));
                                } else {
                                    p.sendMessage(ChatColor.RED + "You are not in queue " + i);
                                }
                            }
                        } else {
                            s = args[1].replace("gym", "");

                            try {
                                i = Integer.parseInt(s);
                            } catch (NumberFormatException numberformatexception4) {
                                p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                return true;
                            }

                            if (i < 1 || i > 32) {
                                return true;
                            }

                            player = null;
                            if (((List) this.queues.get(Integer.valueOf(i))).size() > 0) {
                                po = (UUID) ((List) this.queues.get(Integer.valueOf(i))).get(0);
                                player = Bukkit.getPlayer(po);
                                k = ((List) this.queues.get(Integer.valueOf(i))).indexOf(p.getUniqueId()) + 1;
                                if (((List) this.queues.get(Integer.valueOf(i))).contains(p.getUniqueId())) {
                                    p.sendMessage(ChatColor.GOLD + "You are currently in position " + k + " for the " + this.getConfig().getString("config." + args[1]) + " Gym");
                                    p.sendMessage(ChatColor.GOLD + "The queue size for the " + this.getConfig().getString("config." + args[1]) + " Gym is " + ((List) this.queues.get(Integer.valueOf(i))).size());
                                }

                                if (p.hasPermission("pixelgym." + args[1]) && !((List) this.queues.get(Integer.valueOf(i))).contains(p.getUniqueId())) {
                                    p.sendMessage(ChatColor.GOLD + "The queue size for the " + this.getConfig().getString("config." + args[1]) + " Gym is " + ((List) this.queues.get(Integer.valueOf(i))).size());
                                    p.sendMessage(ChatColor.GOLD + "The first player of the queue is: (" + player.getName() + ")");
                                } else if (((List) this.queues.get(Integer.valueOf(i))).contains(p.getUniqueId()) && p.hasPermission("pixelgym." + args[1])) {
                                    p.sendMessage(ChatColor.GOLD + "The queue size for the " + this.getConfig().getString("config." + args[1]) + " Gym is " + ((List) this.queues.get(Integer.valueOf(i))).size());
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

                        if (args[0].equalsIgnoreCase("sendrules")) {
                            if (this.getConfig().getString("config." + args[1]) != null) {
                                p.sendMessage(ChatColor.RED + "You need to specify a player that you want to send the rules to!");
                                p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/gym sendrules <gym#> (username)");
                            } else {
                                p.sendMessage(ChatColor.RED + "There are only 32 gym\'s, please use a valid gym!");
                            }
                        }

                        if (args[0].equalsIgnoreCase("rules")) {
                            if (this.getConfig().getString("config." + args[1]) != null) {
                                if (this.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("true")) {
                                    p.sendMessage(ChatColor.GOLD + "----- " + this.getConfig().getString("config." + args[1]) + " Gym Rules -----");
                                    p.sendMessage("");
                                    p.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config." + args[1] + "rule1"));
                                    p.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config." + args[1] + "rule2"));
                                    p.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config." + args[1] + "rule3"));
                                    p.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config." + args[1] + "rule4"));
                                    p.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config." + args[1] + "rule5"));
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "The gym " + args[1] + " does not exist!");
                                p.sendMessage(ChatColor.RED + "Try /gym rules gym1");
                            }
                        }

                        if (args[0].equalsIgnoreCase("open")) {
                            if (!p.hasPermission("pixelgym.leader") && !p.hasPermission("pixelgym.admin")) {
                                p.sendMessage(ChatColor.RED + "You do not have permission to open a gym!");
                            } else if (this.getConfig().getString("config." + args[1]) != null) {
                                if (!p.hasPermission("pixelgym." + args[1]) && !p.hasPermission("pixelgym.admin")) {
                                    p.sendMessage(ChatColor.RED + "You do not have permission to open " + args[1]);
                                } else if (!args[1].equalsIgnoreCase("e41") && !args[1].equalsIgnoreCase("e42") && !args[1].equalsIgnoreCase("e43") && !args[1].equalsIgnoreCase("e44")) {
                                    if (this.getConfig().getString("config." + args[1] + "stat").equals("Open")) {
                                        p.sendMessage(ChatColor.RED + "The " + this.getConfig().getString("config." + args[1]) + " Gym is already open! ");
                                    } else if (this.getConfig().getString("config." + args[1] + "stat").equals("Closed")) {
                                        if (this.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("True")) {
                                            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour")) + "The " + this.getConfig().getString("config." + args[1]) + " Gym is now " + ChatColor.GREEN + "Open");
                                            this.getConfig().set("config." + args[1] + "stat", "Open");
                                            p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                                            if (this.getConfig().getString("config.scoreboard").equals("True")) {
                                                if (args[1].equalsIgnoreCase("gym1")) {
                                                    this.gym1.setScore(1);
                                                } else if (args[1].equalsIgnoreCase("gym2")) {
                                                    this.gym2.setScore(2);
                                                } else if (args[1].equalsIgnoreCase("gym3")) {
                                                    this.gym3.setScore(3);
                                                } else if (args[1].equalsIgnoreCase("gym4")) {
                                                    this.gym4.setScore(4);
                                                } else if (args[1].equalsIgnoreCase("gym5")) {
                                                    this.gym5.setScore(5);
                                                } else if (args[1].equalsIgnoreCase("gym6")) {
                                                    this.gym6.setScore(6);
                                                } else if (args[1].equalsIgnoreCase("gym7")) {
                                                    this.gym7.setScore(7);
                                                } else if (args[1].equalsIgnoreCase("gym8")) {
                                                    this.gym8.setScore(8);
                                                } else if (args[1].equalsIgnoreCase("gym9")) {
                                                    this.gym9.setScore(9);
                                                } else if (args[1].equalsIgnoreCase("gym10")) {
                                                    this.gym10.setScore(10);
                                                } else if (args[1].equalsIgnoreCase("gym11")) {
                                                    this.gym11.setScore(11);
                                                } else if (args[1].equalsIgnoreCase("gym12")) {
                                                    this.gym12.setScore(12);
                                                } else if (args[1].equalsIgnoreCase("gym13")) {
                                                    this.gym13.setScore(13);
                                                } else if (args[1].equalsIgnoreCase("gym14")) {
                                                    this.gym14.setScore(14);
                                                } else if (args[1].equalsIgnoreCase("gym15")) {
                                                    this.gym15.setScore(15);
                                                } else if (args[1].equalsIgnoreCase("gym16")) {
                                                    this.gym16.setScore(16);
                                                } else if (args[1].equalsIgnoreCase("gym17")) {
                                                    this.gym17.setScore(17);
                                                } else if (args[1].equalsIgnoreCase("gym18")) {
                                                    this.gym18.setScore(18);
                                                } else if (args[1].equalsIgnoreCase("gym19")) {
                                                    this.gym19.setScore(19);
                                                } else if (args[1].equalsIgnoreCase("gym20")) {
                                                    this.gym20.setScore(20);
                                                } else if (args[1].equalsIgnoreCase("gym21")) {
                                                    this.gym21.setScore(21);
                                                } else if (args[1].equalsIgnoreCase("gym22")) {
                                                    this.gym22.setScore(22);
                                                } else if (args[1].equalsIgnoreCase("gym23")) {
                                                    this.gym23.setScore(23);
                                                } else if (args[1].equalsIgnoreCase("gym24")) {
                                                    this.gym24.setScore(24);
                                                } else if (args[1].equalsIgnoreCase("gym25")) {
                                                    this.gym25.setScore(25);
                                                } else if (args[1].equalsIgnoreCase("gym26")) {
                                                    this.gym26.setScore(26);
                                                } else if (args[1].equalsIgnoreCase("gym27")) {
                                                    this.gym27.setScore(27);
                                                } else if (args[1].equalsIgnoreCase("gym28")) {
                                                    this.gym28.setScore(28);
                                                } else if (args[1].equalsIgnoreCase("gym29")) {
                                                    this.gym29.setScore(29);
                                                } else if (args[1].equalsIgnoreCase("gym30")) {
                                                    this.gym30.setScore(30);
                                                } else if (args[1].equalsIgnoreCase("gym31")) {
                                                    this.gym31.setScore(31);
                                                } else if (args[1].equalsIgnoreCase("gym32")) {
                                                    this.gym32.setScore(32);
                                                }
                                            }
                                        } else if (this.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("False")) {
                                            p.sendMessage(ChatColor.RED + "This gym, " + args[1] + " is disabled in the config. Please open an enabled gym.");
                                        }
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "To open the Elite 4, type /e4 open e4#. Not /gym open e4#");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "The gym " + args[1] + " does not exist!");
                                p.sendMessage(ChatColor.RED + "Try /gym open gym1");
                            }
                        } else if (args[0].equalsIgnoreCase("close")) {
                            if (!p.hasPermission("pixelgym.leader") && !p.hasPermission("pixelgym.admin")) {
                                p.sendMessage(ChatColor.RED + "You do not have permission to close a gym!");
                            } else if (this.getConfig().getString("config." + args[1]) != null) {
                                if (!p.hasPermission("pixelgym." + args[1]) && !p.hasPermission("pixelgym.admin")) {
                                    p.sendMessage(ChatColor.RED + "You do not have permission to close " + args[1]);
                                } else if (!args[1].equalsIgnoreCase("e41") && !args[1].equalsIgnoreCase("e42") && !args[1].equalsIgnoreCase("e43") && !args[1].equalsIgnoreCase("e44")) {
                                    if (this.getConfig().getString("config." + args[1] + "enabled").equalsIgnoreCase("True")) {
                                        if (this.getConfig().getString("config." + args[1] + "stat").equals("Closed")) {
                                            p.sendMessage(ChatColor.RED + "The " + this.getConfig().getString("config." + args[1]) + " gym is already Closed!");
                                        } else if (this.getConfig().getString("config." + args[1] + "stat").equals("Open")) {
                                            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour")) + "The " + this.getConfig().getString("config." + args[1]) + " Gym is now " + ChatColor.RED + "Closed");
                                            this.getConfig().set("config." + args[1] + "stat", "Closed");
                                            p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                                            this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config." + args[1] + "colour")) + this.getConfig().getString("config." + args[1])));
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "The " + args[1] + " gym is not enabled in the config, please close an enabled gym.");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "To close the Elite 4, type /e4 close e4#. Not /gym close e4#");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "The gym " + args[1] + " does not exist!");
                                p.sendMessage(ChatColor.RED + "Try /gym close gym1");
                            }
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

                        if (args[0].equalsIgnoreCase("sendrules")) {
                            if (Bukkit.getPlayer(args[2]) != null) {
                                playerTarget = Bukkit.getPlayer(args[2]);
                                s1 = args[1].replace("gym", "");

                                try {
                                    l = Integer.parseInt(s1);
                                } catch (NumberFormatException numberformatexception5) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (l < 1 || l > 32) {
                                    return true;
                                }

                                if (this.getConfig().getString("config." + args[1]) != null) {
                                    if (p.hasPermission("pixelgym." + args[1])) {
                                        p.sendMessage(ChatColor.GOLD + "Sent " + this.getConfig().getString("config." + args[1]) + " Gym Rules to " + playerTarget.getName().toString());
                                        playerTarget.sendMessage(ChatColor.GOLD + playerTarget.getName().toString() + ", Make sure you read the " + this.getConfig().getString("config." + args[1]) + " Gym rules propperly before facing the Gym!");
                                        playerTarget.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config." + args[1] + "rule1"));
                                        playerTarget.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config." + args[1] + "rule2"));
                                        playerTarget.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config." + args[1] + "rule3"));
                                        playerTarget.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config." + args[1] + "rule4"));
                                        playerTarget.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config." + args[1] + "rule5"));
                                    } else {
                                        p.sendMessage(ChatColor.RED + "You are not gym leader of this gym!");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "The gym " + args[1] + " does not exist!");
                                    p.sendMessage(ChatColor.RED + "Try /gym sendrules gym1 (player)");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "You need to specify a player to send the rules to. Example: /gym sendrules gym1 (player)");
                            }
                        }

                        int i1;

                        if (!args[0].equalsIgnoreCase("winner") && !args[0].equalsIgnoreCase("win") && !args[0].equalsIgnoreCase("w")) {
                            Iterator iterator;

                            if (args[0].equalsIgnoreCase("givebadge")) {
                                s = args[1].replace("gym", "");

                                try {
                                    i = Integer.parseInt(s);
                                } catch (NumberFormatException numberformatexception6) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (i < 1 || i > 32) {
                                    return true;
                                }

                                iterator = Bukkit.getOnlinePlayers().iterator();

                                while (iterator.hasNext()) {
                                    player = (Player) iterator.next();
                                    if (player.getName().equalsIgnoreCase(args[2])) {
                                        if (p.hasPermission("pixelgym.admin")) {
                                            for (k = 1; k <= 32; ++k) {
                                                if (args[1].equalsIgnoreCase("gym" + k)) {
                                                    this.settings.getBadge().set("Players." + player.getUniqueId() + ".Badges." + args[1], "Won");
                                                    this.settings.saveBadges();
                                                    p.sendMessage(ChatColor.GREEN + "Gave " + player.getName() + " the gym" + i + " badge!");
                                                }
                                            }
                                        } else {
                                            p.hasPermission("You do not have permission to give gym badges!");
                                        }
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("delbadge")) {
                                s = args[1].replace("gym", "");

                                try {
                                    i = Integer.parseInt(s);
                                } catch (NumberFormatException numberformatexception7) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (i < 1 || i > 32) {
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
                                                        p.sendMessage(ChatColor.GREEN + "Deleted the gym" + i + " badge from " + player.getName());
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
                                    i = Integer.parseInt(s);
                                } catch (NumberFormatException numberformatexception8) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (i < 1 || i > 32) {
                                    return true;
                                }

                                if (Bukkit.getPlayer(args[2]) != null) {
                                    player = Bukkit.getPlayer(args[2]);
                                    if (!p.hasPermission("pixelgym." + args[1]) && !p.hasPermission("pixelgym.admin")) {
                                        p.sendMessage(ChatColor.RED + "You do not have permission to set losers of " + args[1]);
                                    } else {
                                        po = player.getUniqueId();
                                        if (((List) this.queues.get(Integer.valueOf(i))).contains(po)) {
                                            if (this.settings.getData().get("warps.spawn") != null) {
                                                world = Bukkit.getServer().getWorld(this.settings.getData().getString("warps.spawn.world"));
                                                d0 = this.settings.getData().getDouble("warps.spawn.x");
                                                y = this.settings.getData().getDouble("warps.spawn.y");
                                                z = this.settings.getData().getDouble("warps.spawn.z");
                                                player.teleport(new Location(world, d0, y, z));
                                                player.sendMessage(ChatColor.GREEN + "Teleported out of " + ChatColor.YELLOW + ChatColor.BOLD + "gym" + i + "!");
                                                player.sendMessage(ChatColor.GREEN + "Unlucky! you lost that gym battle! (" + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.gym" + i + "colour") + ChatColor.BOLD + this.getConfig().getString("config.gym" + i) + ChatColor.GREEN + ") Not to worry! You can challenge the gym again in an hour! type: /gym join gym" + i + " When the hour has finished."));
                                                player.sendMessage(ChatColor.BLUE + "To check how long you have left on your cooldown, type: /gym join gym" + i);
                                                p.sendMessage(ChatColor.GREEN + "Successfully telported " + player.getName() + " out of your gym!");
                                                p.sendMessage(ChatColor.GREEN + "You are now ready for your next battle, type: /gym next gym" + i);
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Warp point \'spawn\' does not exist. Type: /gym setwarp spawn. (this is the teleport location to move challengers out of the gym.");
                                            }

                                            String s2 = args[1].replace("gym", "");
                                            final int j1 = Integer.parseInt(s2);

                                            ((List) this.queues.get(Integer.valueOf(i))).remove(0);
                                            this.settings.getLogs().set("Leaders." + p.getName() + "." + args[1] + ".[" + this.format.format(this.now) + "]", player.getName() + " - Lost");
                                            this.settings.saveLogs();
                                            i1 = Integer.parseInt(this.getConfig().getString("config.cooldowntime"));
                                            ((Map) this.cooldownTime.get(Integer.valueOf(j1))).put(po, Integer.valueOf(i1));
                                            ((List) this.cooldownGym.get(Integer.valueOf(i))).add(po);
                                            ((Map) this.cooldownTask.get(Integer.valueOf(i))).put(po, new BukkitRunnable() {
                                                public void run() {
                                                    ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(i))).put(po, Integer.valueOf(((Integer) ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(i))).get(po)).intValue() - 1));
                                                    if (((Integer) ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(i))).get(po)).intValue() == 0) {
                                                        ((Map) PixelGym.this.cooldownTime.get(Integer.valueOf(i))).remove(po);
                                                        ((Map) PixelGym.this.cooldownTask.get(Integer.valueOf(i))).remove(po);
                                                        ((List) PixelGym.this.cooldownGym.get(Integer.valueOf(i))).remove(po);
                                                        this.cancel();
                                                    }

                                                }
                                            });
                                            ((BukkitRunnable) ((Map) this.cooldownTask.get(Integer.valueOf(i))).get(po)).runTaskTimer(this, 20L, 1200L);
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
        } else if (commandLable.equalsIgnoreCase("e4") && this.enablee4.equalsIgnoreCase("true")) {
            if (args.length == 0) {
                if (!p.hasPermission("pixelgym.e4leader")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/e4 list" + ChatColor.DARK_GREEN + " - Get a list of the E4 Level\'s and there status.");
                    p.sendMessage(ChatColor.GREEN + "/e4 leaders" + ChatColor.DARK_GREEN + " - Get a list of the online E4 leaders.");
                    p.sendMessage(ChatColor.GREEN + "/e4 rules <e4#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified E4 Level.");
                    p.sendMessage(ChatColor.GREEN + "/gym scoreboard" + ChatColor.DARK_GREEN + " - Toggle ScoreBoard for e4 and Gym.");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                } else if (p.hasPermission("pixelgym.e4leader")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/e4 list" + ChatColor.DARK_GREEN + " - Get a list of the E4 level\'s and there status.");
                    p.sendMessage(ChatColor.GREEN + "/e4 leaders" + ChatColor.DARK_GREEN + " - Get a list of the online E4 leaders.");
                    p.sendMessage(ChatColor.GREEN + "/e4 rules <e4#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified E4 Level.");
                    p.sendMessage(ChatColor.GREEN + "/e4 open <e4#>" + ChatColor.DARK_GREEN + " - Open a particular E4 level (e41, e42, e43 or e44).");
                    p.sendMessage(ChatColor.GREEN + "/e4 close <e4#>" + ChatColor.DARK_GREEN + " - Close a particular E4 level (e41, e42, e43 or e44).");
                    p.sendMessage(ChatColor.GREEN + "/e4 heal" + ChatColor.DARK_GREEN + " - Heals your pokemon.");
                    p.sendMessage(ChatColor.GREEN + "/e4 sendrules <e4#> (username)" + ChatColor.DARK_GREEN + " - Force quit a e4 battle. In (Username) put either yours or the challengers IGN!");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                } else if (p.hasPermission("pixelgym.admin")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "/e4 list" + ChatColor.DARK_GREEN + " - Get a list of the E4 Level\'s and there status.");
                    p.sendMessage(ChatColor.GREEN + "/e4 leaders" + ChatColor.DARK_GREEN + " - Get a list of the online E4 leaders.");
                    p.sendMessage(ChatColor.GREEN + "/e4 rules <e4#>" + ChatColor.DARK_GREEN + " - Shows all the rules for the specified E4 Level.");
                    p.sendMessage(ChatColor.GREEN + "/e4 closeall" + ChatColor.DARK_GREEN + " - Closes all Elite 4 Level\'s.");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                }
            } else if (args.length == 1) {
                if (args[0].equals("leaders")) {
                    p.sendMessage(ChatColor.GOLD + "----- Online E4 Leaders -----");
                    p.sendMessage("");
                    gymArg = Bukkit.getOnlinePlayers().iterator();

                    while (gymArg.hasNext()) {
                        playerTarget = (Player) gymArg.next();
                        if (playerTarget.hasPermission("pixelgym.e41") && this.enablee4.equalsIgnoreCase("true")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + playerTarget.getName() + " - " + this.getConfig().getString("config.e41"));
                        }

                        if (playerTarget.hasPermission("pixelgym.e42") && this.enablee4.equalsIgnoreCase("true")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + playerTarget.getName() + " - " + this.getConfig().getString("config.e42"));
                        }

                        if (playerTarget.hasPermission("pixelgym.e43") && this.enablee4.equalsIgnoreCase("true")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + playerTarget.getName() + " - " + this.getConfig().getString("config.e43"));
                        }

                        if (playerTarget.hasPermission("pixelgym.e44") && this.enablee4.equalsIgnoreCase("true")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + playerTarget.getName() + " - " + this.getConfig().getString("config.e44"));
                        }
                    }
                } else if (args[0].equals("list")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    if (this.enablee4.equalsIgnoreCase("true")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + this.getConfig().getString("config.e41") + "  Elite 4 is: " + ChatColor.BLUE + this.getConfig().getString("config.e41stat"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + this.getConfig().getString("config.e42") + "  Elite 4 is: " + ChatColor.BLUE + this.getConfig().getString("config.e42stat"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + this.getConfig().getString("config.e43") + "  Elite 4 is: " + ChatColor.BLUE + this.getConfig().getString("config.e43stat"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + this.getConfig().getString("config.e44") + "  Elite 4 is: " + ChatColor.BLUE + this.getConfig().getString("config.e41stat"));
                    }
                } else if (args[0].equals("open") && p.hasPermission("pixelgym.e4leader")) {
                    p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/e4 open <e4#>");
                } else if (args[0].equals("close") && p.hasPermission("pixelgym.e4leader")) {
                    p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/e4 close <e4#>");
                } else if (args[0].equals("rules")) {
                    p.sendMessage(ChatColor.DARK_RED + "Proper Usage: " + ChatColor.RED + "/e4 rules <e4#>");
                } else if (args[0].equals("heal") && p.hasPermission("pixelgym.e4leader") && this.enableGymHeal.equalsIgnoreCase("true")) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokeheal " + p.getName().toString());
                    p.sendMessage("Your pixelmon have been healed!");
                } else if (args[0].equals("heal") && p.hasPermission("pixelgym.e4leader") && !this.enableGymHeal.equalsIgnoreCase("true")) {
                    p.sendMessage(ChatColor.RED + "Gym/E4 Leader healing disabled in the plugin config");
                } else if (args[0].equals("quit") && p.hasPermission("pixelgym.e4leader")) {
                    gymArg = Bukkit.getOnlinePlayers().iterator();

                    while (gymArg.hasNext()) {
                        playerTarget = (Player) gymArg.next();
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "endbattle " + playerTarget.getName().toString());
                        p.sendMessage(ChatColor.GREEN + "You have successfully quit the battle!");
                    }
                } else if (args[0].equalsIgnoreCase("closeall") && p.hasPermission("pixelgym.admin")) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "All " + this.getConfig().getString("config.e4") + " Level\'s are now closed.");
                    this.getConfig().set("config.e41stat", "Closed");
                    this.getConfig().set("config.e42stat", "Closed");
                    this.getConfig().set("config.e43stat", "Closed");
                    this.getConfig().set("config.e44stat", "Closed");
                    this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + this.getConfig().getString("config.e41") + " " + this.getConfig().getString("config.e4ab")));
                    this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + this.getConfig().getString("config.e42") + " " + this.getConfig().getString("config.e4ab")));
                    this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + this.getConfig().getString("config.e43") + " " + this.getConfig().getString("config.e4ab")));
                    this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + this.getConfig().getString("config.e44") + " " + this.getConfig().getString("config.e4ab")));
                }
            } else if (args.length == 2) {
                if (args[0].equals("rules") && args[1].equals("e41")) {
                    if (this.enablee4.equalsIgnoreCase("true")) {
                        p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config.e41rule1"));
                        p.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config.e41rule2"));
                        p.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config.e41rule3"));
                        p.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config.e41rule4"));
                        p.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config.e41rule5"));
                    }
                } else if (args[0].equals("rules") && args[1].equals("e42")) {
                    if (this.enablee4.equalsIgnoreCase("true")) {
                        p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config.e42rule1"));
                        p.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config.e42rule2"));
                        p.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config.e42rule3"));
                        p.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config.e42rule4"));
                        p.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config.e42rule5"));
                    }
                } else if (args[0].equals("rules") && args[1].equals("e43")) {
                    if (this.enablee4.equalsIgnoreCase("true")) {
                        p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config.e43rule1"));
                        p.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config.e43rule2"));
                        p.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config.e43rule3"));
                        p.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config.e43rule4"));
                        p.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config.e43rule5"));
                    }
                } else if (args[0].equals("rules") && args[1].equals("e44") && this.enablee4.equalsIgnoreCase("true")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config.e44rule1"));
                    p.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config.e44rule2"));
                    p.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config.e44rule3"));
                    p.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config.e44rule4"));
                    p.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config.e44rule5"));
                }

                if (p.hasPermission("pixelgym.e4leader")) {
                    if (args[0].equalsIgnoreCase("open") && args[1].equalsIgnoreCase("e41") && p.hasPermission("pixelgym.e41") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + "The " + this.getConfig().getString("config.e41") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
                        this.getConfig().set("config.e41stat", "Open");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        if (this.getConfig().getString("config.scoreboard").equals("True")) {
                            this.e41.setScore(101);
                        }
                    }

                    if (args[0].equalsIgnoreCase("open") && args[1].equalsIgnoreCase("e42") && p.hasPermission("pixelgym.e42") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + "The " + this.getConfig().getString("config.e42") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
                        this.getConfig().set("config.e42stat", "Open");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        if (this.getConfig().getString("config.scoreboard").equals("True")) {
                            this.e42.setScore(102);
                        }
                    }

                    if (args[0].equalsIgnoreCase("open") && args[1].equalsIgnoreCase("e43") && p.hasPermission("pixelgym.e43") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + "The " + this.getConfig().getString("config.e43") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
                        this.getConfig().set("config.e43stat", "Open");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        if (this.getConfig().getString("config.scoreboard").equals("True")) {
                            this.e43.setScore(103);
                        }
                    }

                    if (args[0].equalsIgnoreCase("open") && args[1].equalsIgnoreCase("e44") && p.hasPermission("pixelgym.e44") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + "The " + this.getConfig().getString("config.e44") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.GREEN + "Open");
                        this.getConfig().set("config.e44stat", "Open");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        if (this.getConfig().getString("config.scoreboard").equals("True")) {
                            this.e44.setScore(104);
                        }
                    } else if (args[0].equalsIgnoreCase("close") && args[1].equalsIgnoreCase("e41") && p.hasPermission("pixelgym.e41") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + "The " + this.getConfig().getString("config.e41") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                        this.getConfig().set("config.e41stat", "Closed");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e41colour")) + this.getConfig().getString("config.e41") + " " + this.getConfig().getString("config.e4ab")));
                    } else if (args[0].equalsIgnoreCase("close") && args[1].equalsIgnoreCase("e42") && p.hasPermission("pixelgym.e42") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + "The " + this.getConfig().getString("config.e42") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                        this.getConfig().set("config.e42stat", "Closed");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e42colour")) + this.getConfig().getString("config.e42") + " " + this.getConfig().getString("config.e4ab")));
                    } else if (args[0].equalsIgnoreCase("close") && args[1].equalsIgnoreCase("e43") && p.hasPermission("pixelgym.e43") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + "The " + this.getConfig().getString("config.e43") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                        this.getConfig().set("config.e43stat", "Closed");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e43colour")) + this.getConfig().getString("config.e43") + " " + this.getConfig().getString("config.e4ab")));
                    } else if (args[0].equalsIgnoreCase("close") && args[1].equalsIgnoreCase("e44") && p.hasPermission("pixelgym.e44") && this.enablee4.equalsIgnoreCase("true")) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + "The " + this.getConfig().getString("config.e44") + " " + this.getConfig().getString("config.e4") + " is now " + ChatColor.RED + "Closed");
                        this.getConfig().set("config.e44stat", "Closed");
                        p.sendMessage(ChatColor.GOLD + "The Plugin can be found @ http://dev.bukkit.org/bukkit-plugins/pixelmongym/ ");
                        this.board.resetScores(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("config.e44colour")) + this.getConfig().getString("config.e44") + " " + this.getConfig().getString("config.e4ab")));
                    }
                }
            } else if (args.length == 3) {
                gymArg = Bukkit.getServer().getOnlinePlayers().iterator();

                while (gymArg.hasNext()) {
                    playerTarget = (Player) gymArg.next();
                    if (args[0].equalsIgnoreCase("sendrules") && playerTarget.getName().equalsIgnoreCase(args[2]) && p.hasPermission("pixelgym." + args[1])) {
                        p.sendMessage(ChatColor.GOLD + "Sent " + this.getConfig().getString("config." + args[1]) + " Elite 4 Rules to " + playerTarget.getName().toString());
                        playerTarget.sendMessage(ChatColor.GOLD + playerTarget.getName().toString() + ", Make sure you read the " + this.getConfig().getString("config." + args[1]) + " Elite 4 rules propperly before facing the Gym!");
                        playerTarget.sendMessage(ChatColor.GREEN + "1) " + this.getConfig().getString("config." + args[1] + "rule1"));
                        playerTarget.sendMessage(ChatColor.GREEN + "2) " + this.getConfig().getString("config." + args[1] + "rule2"));
                        playerTarget.sendMessage(ChatColor.GREEN + "3) " + this.getConfig().getString("config." + args[1] + "rule3"));
                        playerTarget.sendMessage(ChatColor.GREEN + "4) " + this.getConfig().getString("config." + args[1] + "rule4"));
                        playerTarget.sendMessage(ChatColor.GREEN + "5) " + this.getConfig().getString("config." + args[1] + "rule5"));
                    }
                }
            }
        }

        return false;
    }
}
