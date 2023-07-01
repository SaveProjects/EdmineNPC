package fr.edminecoreteam.npc.interactions;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.edminecoreteam.npc.Main;

public class ClickEvent implements Listener
{
	private static Main core = Main.getInstance();
	
	@EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e) {
		Entity ent = e.getRightClicked();
        Player p = e.getPlayer();
        if (ent instanceof ArmorStand)
        {
        	for (String npc : core.getConfig().getConfigurationSection("NPC").getKeys(false))
            {
            	String npcName = core.getConfig().getString("NPC." + npc + ".name");
            	if ((ent.getType().equals((Object)EntityType.ARMOR_STAND)) && ent.getCustomName().equalsIgnoreCase(npcName))
            	{
            		p.performCommand(core.getConfig().getString("NPC." + npc + ".command"));
            		e.setCancelled(true);
            		return;
            	}
            }
        }
    }

	
	@EventHandler
	public void entityDamageEvent(EntityDamageByEntityEvent e) {
		Entity ent = e.getEntity();
		Entity attacker = e.getDamager();
		if (ent instanceof ArmorStand)
		{
			if (attacker instanceof Player)
			{
				Player p = (Player) attacker;
				for (String npc : core.getConfig().getConfigurationSection("NPC").getKeys(false))
	            {
	            	String npcName = core.getConfig().getString("NPC." + npc + ".name");
	            	if ((ent.getType().equals((Object)EntityType.ARMOR_STAND)) && ent.getCustomName().equalsIgnoreCase(npcName))
	            	{
	            		p.performCommand(core.getConfig().getString("NPC." + npc + ".command"));
	            		e.setCancelled(true);
	            		return;
	            	}
	            }
			}
		}
	}
}
