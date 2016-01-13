package me.Ckay.gym;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PixelGymAdmin implements CommandExecutor, Listener {

    PixelGym plugin;

    public PixelGymAdmin(PixelGym pixelGym) {
        this.plugin = pixelGym;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
        Player p = (Player) sender;

        if (commandLable.equalsIgnoreCase("pixelgym") && p.hasPermission("pixelgym.admin")) {
            if (args.length == 0) {
                if (!p.hasPermission("pixelgym.admin")) {
                    p.sendMessage(ChatColor.GOLD + "----- PixelmonGyms -----");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "You do not haver permission for any commands in this area.");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Plugin Made By " + ChatColor.GOLD + "ABkayCkay");
                } else if (p.hasPermission("pixelgym.admin")) {
                    p.sendMessage(ChatColor.GREEN + "/pixelgym checkconfig" + ChatColor.DARK_GREEN + " - Checks the currently set config settings.");
                    p.sendMessage(ChatColor.GREEN + "/pixelgym addleader <Gym#/e4#> (Username)" + ChatColor.DARK_GREEN + " - Add a gym leader to a specific gym or elite 4 level. E.G: /pixelgym addleader Gym1 ABkayCkay");
                    p.sendMessage(ChatColor.GREEN + "/pixelgym delleader <Gym#/e4#> (Username)" + ChatColor.DARK_GREEN + " - Remove a gym leader from a specific gym or elite 4 level. E.G: /pixelgym delleader Gym1 ABkayCkay");
                    p.sendMessage(ChatColor.GREEN + "/pixelgym setlevel <gym#> (level)" + ChatColor.DARK_GREEN + " - Sets the level of the specified gym. (Modify\'s the config)");
                    p.sendMessage(ChatColor.GREEN + "/pixelgym setrule <gym#> <rule#> (rule)" + ChatColor.DARK_GREEN + " - Sets the rules of the specified gym. (Modify\'s the config) E.G: /pixelgym setrule gym1 1 No_Potions");
                }
            } else if (args.length == 1) {
            } else {
                String ruleArg;
                int gym1;
                String gymArg1;

                if (args.length == 3) {
                    Player gym;
                    int gymArg;
                    String rule;

                    if (args[0].equalsIgnoreCase("addleader")) {
                        if (p.hasPermission("pixelgym.admin")) {
                            if (Bukkit.getPlayer(args[2]) != null) {
                                gym = Bukkit.getPlayer(args[2]);
                                rule = args[1].replace("gym", "");

                                try {
                                    gymArg = Integer.parseInt(rule);
                                } catch (NumberFormatException numberformatexception) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (gymArg < 1 || gymArg > 32) {
                                    return true;
                                }

                                if (this.plugin.getConfig().getString("config.enablegroup").equals("True")) {
                                    if (PixelGym.permission.playerInGroup(gym, this.plugin.getConfig().getString("config.globalgroupname"))) {
                                        p.sendMessage(ChatColor.RED + "Player is already in this group, giving them the gym permission node. (pixelgym.gym" + gymArg);
                                        PixelGym.permission.playerAdd(gym, "pixelgym.gym" + gymArg);
                                    } else {
                                        ruleArg = this.plugin.getConfig().getString("config.globalgroupname");
                                        PixelGym.permission.playerAddGroup(gym, ruleArg);
                                        PixelGym.permission.playerAdd(gym, "pixelgym.gym" + gymArg);
                                    }
                                } else {
                                    PixelGym.permission.playerAdd(gym, "pixelgym.gym" + gymArg);
                                    PixelGym.permission.playerAdd(gym, "pixelgym.leader");
                                }

                                if (this.plugin.getConfig().getString("config.giveleaderpokemon").equals("True")) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config.gym" + gymArg + "poke1") + " lvl" + this.plugin.getConfig().getString("config.gym" + gymArg + "poke1lvl"));
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config.gym" + gymArg + "poke2") + " lvl" + this.plugin.getConfig().getString("config.gym" + gymArg + "poke2lvl"));
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config.gym" + gymArg + "poke3") + " lvl" + this.plugin.getConfig().getString("config.gym" + gymArg + "poke3lvl"));
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config.gym" + gymArg + "poke4") + " lvl" + this.plugin.getConfig().getString("config.gym" + gymArg + "poke4lvl"));
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config.gym" + gymArg + "poke5") + " lvl" + this.plugin.getConfig().getString("config.gym" + gymArg + "poke5lvl"));
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config.gym" + gymArg + "poke6") + " lvl" + this.plugin.getConfig().getString("config.gym" + gymArg + "poke6lvl"));
                                }

                                gym.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + "You have been set as a " + this.plugin.getConfig().getString("config." + args[1]) + " Leader!");
                                gym.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + "Try /gym or /e4 to see your new commands!");
                                p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + gym.getName().toString() + " has successfully been added as a " + this.plugin.getConfig().getString("config." + args[1]) + " leader!");
                            }

                            if (args[1].equalsIgnoreCase("e41") || args[1].equalsIgnoreCase("e42") || args[1].equalsIgnoreCase("e43") || args[1].equalsIgnoreCase("e44")) {
                                gym = Bukkit.getPlayer(args[2]);
                                if (args[2].equals(gym.getName())) {
                                    if (this.plugin.getConfig().getString("config.enablegroup").equals("True")) {
                                        if (PixelGym.permission.playerInGroup(gym, this.plugin.getConfig().getString("config.globale4groupname"))) {
                                            p.sendMessage(ChatColor.RED + "Player is already in this group, giving them the gym permission node. (pixelgym." + args[1] + ")");
                                            PixelGym.permission.playerAdd(gym, "pixelgym." + args[1]);
                                        } else {
                                            gymArg1 = this.plugin.getConfig().getString("config.globale4groupname");
                                            PixelGym.permission.playerAddGroup(gym, gymArg1);
                                            PixelGym.permission.playerAdd(gym, "pixelgym." + args[1]);
                                        }
                                    } else {
                                        PixelGym.permission.playerAdd(gym, "pixelgym." + args[1]);
                                        PixelGym.permission.playerAdd(gym, "pixelgym.e4leader");
                                    }

                                    if (this.plugin.getConfig().getString("config.giveleaderpokemon").equals("True")) {
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config." + args[1] + "poke1") + " lvl" + this.plugin.getConfig().getString("config." + args[1] + "poke1lvl"));
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config." + args[1] + "poke2") + " lvl" + this.plugin.getConfig().getString("config." + args[1] + "poke2lvl"));
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config." + args[1] + "poke3") + " lvl" + this.plugin.getConfig().getString("config." + args[1] + "poke3lvl"));
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config." + args[1] + "poke4") + " lvl" + this.plugin.getConfig().getString("config." + args[1] + "poke4lvl"));
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config." + args[1] + "poke5") + " lvl" + this.plugin.getConfig().getString("config." + args[1] + "poke5lvl"));
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pokegive " + gym.getName().toString() + " " + this.plugin.getConfig().getString("config." + args[1] + "poke6") + " lvl" + this.plugin.getConfig().getString("config." + args[1] + "poke6lvl"));
                                    }

                                    gym.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + "You have been set as a " + this.plugin.getConfig().getString("config." + args[1]) + " Leader!");
                                    gym.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + "Try /gym or /e4 to see your new commands!");
                                    p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + gym.getName().toString() + " has successfully been added as a " + this.plugin.getConfig().getString("config." + args[1]) + " leader!");
                                }
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                        }
                    }

                    if (args[0].equalsIgnoreCase("delleader")) {
                        if (p.hasPermission("pixelgym.admin")) {
                            gym = Bukkit.getPlayer(args[2]);
                            if (args[2].equals(gym.getName())) {
                                rule = args[1].replace("gym", "");

                                try {
                                    gymArg = Integer.parseInt(rule);
                                } catch (NumberFormatException numberformatexception1) {
                                    p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                                    return true;
                                }

                                if (gymArg < 1 || gymArg > 32) {
                                    return true;
                                }

                                if (this.plugin.getConfig().getString("config.enablegroup").equals("True")) {
                                    if (PixelGym.permission.playerInGroup(gym, this.plugin.getConfig().getString("config.globalgroupname"))) {
                                        PixelGym.permission.playerRemoveGroup(gym, this.plugin.getConfig().getString("config.globalgroupname"));
                                        PixelGym.permission.playerRemove(gym, "pixelgym.gym" + gymArg);
                                    } else {
                                        p.sendMessage(ChatColor.RED + gym.getName() + " is not the gym leader group! Removing there permission nodes.");
                                        PixelGym.permission.playerRemove(gym, "pixelgym.gym" + gymArg);
                                        PixelGym.permission.playerRemove(gym, "pixelgym.leader");
                                    }
                                } else {
                                    PixelGym.permission.playerRemove(gym, "pixelgym.gym" + gymArg);
                                    PixelGym.permission.playerRemove(gym, "pixelgym.leader");
                                }

                                gym.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + "You have been removed from being a " + this.plugin.getConfig().getString("config." + args[1]) + " Leader!");
                                p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + gym.getName().toString() + " has successfully been removed as a " + this.plugin.getConfig().getString("config." + args[1]) + " leader!");
                            } else if ((args[1].equalsIgnoreCase("e41") || args[1].equalsIgnoreCase("e42") || args[1].equalsIgnoreCase("e43") || args[1].equalsIgnoreCase("e44")) && args[2].equals(gym.getName())) {
                                if (this.plugin.getConfig().getString("config.enablegroup").equals("True")) {
                                    if (PixelGym.permission.playerInGroup(gym, this.plugin.getConfig().getString("config.globale4groupname"))) {
                                        PixelGym.permission.playerRemoveGroup(gym, this.plugin.getConfig().getString("config.globale4groupname"));
                                        PixelGym.permission.playerRemove(gym, "pixelgym." + args[1]);
                                    } else {
                                        p.sendMessage(ChatColor.RED + gym.getName() + " is not the e4 leader group! Removing there permission nodes.");
                                        PixelGym.permission.playerRemove(gym, "pixelgym." + args[1]);
                                        PixelGym.permission.playerRemove(gym, "pixelgym.e4leader");
                                    }
                                } else {
                                    PixelGym.permission.playerRemove(gym, "pixelgym." + args[1]);
                                    PixelGym.permission.playerRemove(gym, "pixelgym.e4leader");
                                }

                                gym.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + "You have been removed from being a " + this.plugin.getConfig().getString("config." + args[1]) + " Leader!");
                                p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + this.plugin.getConfig().getString("config.title") + ChatColor.DARK_GRAY + "] " + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("config." + args[1] + "colour")) + gym.getName().toString() + " has successfully been removed as a " + this.plugin.getConfig().getString("config." + args[1]) + " leader!");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                        }
                    }

                    if (args[0].equalsIgnoreCase("setlevel")) {
                        gymArg1 = args[1].replace("gym", "");

                        try {
                            gym1 = Integer.parseInt(gymArg1);
                        } catch (NumberFormatException numberformatexception2) {
                            p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                            return true;
                        }

                        if (gym1 < 1 || gym1 > 32) {
                            return true;
                        }

                        if (args[2] != null) {
                            this.plugin.getConfig().set("config.gym" + gym1 + "lvlcap", args[2]);
                            this.plugin.saveDefaultConfig();
                        }
                    }
                } else if (args.length == 4 && args[0].equalsIgnoreCase("setrule")) {
                    gymArg1 = args[1].replace("gym", "");

                    try {
                        gym1 = Integer.parseInt(gymArg1);
                    } catch (NumberFormatException numberformatexception3) {
                        p.sendMessage(ChatColor.RED + args[1] + " is not a gym!");
                        return true;
                    }

                    if (gym1 < 1 || gym1 > 32) {
                        return true;
                    }

                    ruleArg = args[2];

                    int rule1;

                    try {
                        rule1 = Integer.parseInt(ruleArg);
                    } catch (NumberFormatException numberformatexception4) {
                        p.sendMessage(ChatColor.RED + args[2] + " is not an integer!");
                        return true;
                    }

                    if (rule1 <= 5 && rule1 >= 1) {
                        String message = args[3].replace("_", " ");

                        this.plugin.getConfig().set("config.gym" + gym1 + "rule" + rule1, message);
                        this.plugin.saveDefaultConfig();
                    }
                }
            }
        }

        return false;
    }
}
