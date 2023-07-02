package fr.edminecoreteam.npc;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.edminecoreteam.npc.customname.PlayerJoinPacketHolograms;
import fr.edminecoreteam.npc.interactions.ClickEvent;
import fr.edminecoreteam.npc.spawn.SpawnListener;


public class Main extends JavaPlugin
{
    private static Main instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Main.instance = this;
        loadListeners();
        SpawnListener.init();
    }

    @Override
    public void onDisable() {
        removeEntities();
    }


    private void loadListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents((Listener)new PlayerJoinPacketHolograms(), (Plugin)this);
        pm.registerEvents((Listener)new ClickEvent(), (Plugin)this);
    }

    public void removeEntities() {
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "minecraft:kill @e[type=ArmorStand]");
        System.out.println("Toutes les entités ont été supprimées...");
    }
    public static Main getInstance() {  return Main.instance; }
    public static Plugin getPlugin() { return null; }
}
