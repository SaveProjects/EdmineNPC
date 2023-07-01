package fr.edminecoreteam.npc.customname;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.edminecoreteam.npc.Main;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;

public class HologramManager
{
	public static Main core = Main.getInstance();
	
	private Player p;
	private String spigot_online;
	private String server_id;
	
	public HologramManager(Player p) {
		this.p = p;
	}
	
	public HologramManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void createHolograms() {
		for (String holo : core.getConfig().getConfigurationSection("NPC").getKeys(false))
		{
			String world = core.getConfig().getString("NPC." + holo + ".world");
			float x = (float) core.getConfig().getDouble("NPC." + holo + ".loc.x");
			float y = (float) core.getConfig().getDouble("NPC." + holo + ".loc.y");
			float z = (float) core.getConfig().getDouble("NPC." + holo + ".loc.z");
			
			float finalY = (float) (y + 1.5);
			for (String lines : core.getConfig().getConfigurationSection("NPC." + holo + ".customname").getKeys(false))
			{
				finalY -= 0.3;
				holoManageNMS(world, x, finalY, z, holo, lines);
			}
		}
	}
	
	public void removeHolograms() {
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "minecraft:kill @e[type=ArmorStand]");
        System.out.println("Toutes les entités on été suprimer...");
    }
	
	private void holoManageNMS(String world, float x, float y, float z, String name, String lines) {
		new BukkitRunnable() {
            int t = 0;
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            WorldServer ws = ((CraftWorld)loc.getWorld()).getHandle();
            EntityArmorStand nmsStand = new EntityArmorStand((World)ws);
            
            public void run() {
            	
            	if (Bukkit.getPlayer(p.getName()) == null) { cancel(); }
            	
            	if (t == 1 || t == 0)
            	{
            		spigot_online = "&7Connecté(s): &a".replace("&", "§") + Bukkit.getServer().getOnlinePlayers().size();
                	server_id = getID();
                	
                	nmsStand.setLocation(x, y, z, 0.0f, 0.0f);
            		nmsStand.setSmall(true);
                    
                    nmsStand.setCustomName(encode(core.getConfig().getString("NPC." + name + ".customname." + lines)));
                    
                    nmsStand.setCustomNameVisible(true);
                    nmsStand.setGravity(false);
                    nmsStand.setInvisible(true);
                    PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(nmsStand.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetDestroy);
                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving((EntityLiving)nmsStand);
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packetSpawn);
            	}
            	
                if (t == 2) {
                    this.t = 0;
                }
                ++t;
            }
        }.runTaskTimer((Plugin)core, 0L, 100L);
	}
	
	
	public String getID() 
    {
        String id = "";
        List<Character> number = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        char[] charArray;
        for (int length = (charArray = Bukkit.getServerName().toCharArray()).length, i = 0; i < length; ++i) 
        {
            char cha = charArray[i];
            if (number.contains(cha)) 
            {
                id = String.valueOf(id) + new String(new char[] { cha });
            }
        }
        return id;
    }
	
	public String encode(String s) {
	    String encoded = s
	    		.replace("&", "§")
        		.replace("{e1}", "é")
        		.replace("{e2}", "è")
        		.replace("{e3}", "ê")
        		.replace("{i1}", "î")
        		.replace("{a1}", "à")
        		.replace("{a2}", "â")
        		.replace("{o1}", "ô")
        		.replace("{love}", "❤")
        		.replace("{valide}", "✔")
        		.replace("{unvalid}", "✘")
        		.replace("{flocon}", "✵")
        		.replace("{eclats}", "❁")
        		.replace("{money}", "✪")
        		.replace("{star}", "✯")
        		.replace("{right}", "➡")
        		.replace("{left}", "⬅")
        		.replace("{up}", "⬆")
        		.replace("{down}", "⬇")
        		.replace("{right_select}", "➟")
        		.replace("{alert}", "⚠")
        		.replace("{separator}", "»")
        		.replace("{inverseseparator}", "«")
        		.replace("{spigot_online}", spigot_online)
    			.replace("{server_id}", server_id)
    			.replace("{player_name}", p.getName())
    			.replace("{euro}", "€");
	    
	    return encoded;
	}
}
