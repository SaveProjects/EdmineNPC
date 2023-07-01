package fr.edminecoreteam.npc.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.EulerAngle;

import fr.edminecoreteam.npc.Main;
import fr.edminecoreteam.npc.animations.HellowAnimation;
import fr.edminecoreteam.npc.animations.WalkAnimation;
import fr.edminecoreteam.npc.animations.WalkWithBlockAnimation;
import fr.edminecoreteam.npc.utils.ItemStackSerializer;
import fr.edminecoreteam.npc.utils.SkullNBT;


public class SpawnListener 
{
	public static Main core = Main.getInstance();
	private static ItemStack getSkull(String url) { return SkullNBT.getSkull(url); }
	
	public static void init() {
		
		for (String npc : core.getConfig().getConfigurationSection("NPC").getKeys(false))
		{
			String world = core.getConfig().getString("NPC." + npc + ".world");
			float x = (float) core.getConfig().getDouble("NPC." + npc + ".loc.x");
			float y = (float) core.getConfig().getDouble("NPC." + npc + ".loc.y");
			float z = (float) core.getConfig().getDouble("NPC." + npc + ".loc.z");
			float f = (float) core.getConfig().getDouble("NPC." + npc + ".loc.f");
			float p = (float) core.getConfig().getDouble("NPC." + npc + ".loc.p");
			
			Location customloc = new Location(Bukkit.getWorld(world), x, y, z, f, p);
	        ArmorStand armorStand = (ArmorStand)Bukkit.getWorld(world).spawnEntity(customloc, EntityType.ARMOR_STAND);
	        if (core.getConfig().getString("NPC." + npc + ".npcsize").equalsIgnoreCase("small"))
	        {
	        	armorStand.setSmall(true);
	        }
	        else
	        {
	        	armorStand.setSmall(false);
	        }
	        armorStand.setVisible(true);
	        armorStand.setCanPickupItems(false);
	        armorStand.setArms(true);
	        armorStand.setCustomName(core.getConfig().getString("NPC." + npc + ".name"));
	        armorStand.setCustomNameVisible(false);
	        armorStand.setGravity(false);
	        armorStand.setBasePlate(false);
	        armorStand.setHelmet(getSkull("http://textures.minecraft.net/texture/" + core.getConfig().getString("NPC." + npc + ".head")));
	        if (!core.getConfig().getString("NPC." + npc + ".iteminhand").equalsIgnoreCase("null"))
	        {
	        	ItemStack iteminhand = ItemStackSerializer.deserialize(core.getConfig().getString("NPC." + npc + ".iteminhand"));
		        ItemMeta iteminhandM = (ItemMeta)iteminhand.getItemMeta();
		        if (core.getConfig().getString("NPC." + npc + ".itemisenchant").equalsIgnoreCase("yes"))
		        {
		        	iteminhandM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		        }
		        iteminhand.setItemMeta((ItemMeta)iteminhandM);
		        armorStand.setItemInHand(iteminhand);
	        }
	        else
	        {
	        	armorStand.setItemInHand(null);
	        }
	        armorStand.setRightArmPose(new EulerAngle(Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(147.0)));
	        armorStand.setLeftArmPose(new EulerAngle(Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(342.0)));
	        armorStand.setRightLegPose(new EulerAngle(Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(8.0)));
	        armorStand.setLeftLegPose(new EulerAngle(Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(348.0)));
	        armorStand.setHeadPose(new EulerAngle(Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(8.0)));
	        
	        String anim = core.getConfig().getString("NPC." + npc + ".animation");
	        
	        if (anim.equalsIgnoreCase("{anim1}")) { WalkAnimation.start(armorStand); }
	        if (anim.equalsIgnoreCase("{anim2}")) { WalkWithBlockAnimation.start(armorStand); }
	        if (anim.equalsIgnoreCase("{anim3}")) { HellowAnimation.start(armorStand); }
	        
	        if (core.getConfig().getString("NPC." + npc + ".chestplate.material").contains("leather"))
	        {
	        	ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		        LeatherArmorMeta itemM = (LeatherArmorMeta)item.getItemMeta();
		        itemM.setColor(Color.fromRGB(
		        		core.getConfig().getInt("NPC." + npc + ".chestplate.color.r"), 
		        		core.getConfig().getInt("NPC." + npc + ".chestplate.color.g"), 
		        		core.getConfig().getInt("NPC." + npc + ".chestplate.color.b")));
		        if (core.getConfig().getString("NPC." + npc + ".chestplate.itemisenchant").equalsIgnoreCase("yes"))
		        {
		        	itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		        }
		        item.setItemMeta((ItemMeta)itemM);
		        armorStand.setChestplate(item);
	        }
	        else
	        {
	        	ItemStack item = ItemStackSerializer.deserialize(core.getConfig().getString("NPC." + npc + ".chestplate.material"));
		        ItemMeta itemM = item.getItemMeta();
		        if (core.getConfig().getString("NPC." + npc + ".chestplate.itemisenchant").equalsIgnoreCase("yes"))
		        {
		        	itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		        }
		        item.setItemMeta((ItemMeta)itemM);
		        armorStand.setChestplate(item);
	        }
	        
	        if (core.getConfig().getString("NPC." + npc + ".leggings.material").contains("leather"))
	        {
	        	ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		        LeatherArmorMeta itemM = (LeatherArmorMeta)item.getItemMeta();
		        itemM.setColor(Color.fromRGB(
		        		core.getConfig().getInt("NPC." + npc + ".leggings.color.r"), 
		        		core.getConfig().getInt("NPC." + npc + ".leggings.color.g"), 
		        		core.getConfig().getInt("NPC." + npc + ".leggings.color.b")));
		        if (core.getConfig().getString("NPC." + npc + ".leggings.itemisenchant").equalsIgnoreCase("yes"))
		        {
		        	itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		        }
		        item.setItemMeta((ItemMeta)itemM);
		        armorStand.setLeggings(item);
	        }
	        else
	        {
	        	ItemStack item = ItemStackSerializer.deserialize(core.getConfig().getString("NPC." + npc + ".leggings.material"));
		        ItemMeta itemM = item.getItemMeta();
		        if (core.getConfig().getString("NPC." + npc + ".leggings.itemisenchant").equalsIgnoreCase("yes"))
		        {
		        	itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		        }
		        item.setItemMeta((ItemMeta)itemM);
		        armorStand.setLeggings(item);
	        }
	        
	        if (core.getConfig().getString("NPC." + npc + ".boots.material").contains("leather"))
	        {
	        	ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
		        LeatherArmorMeta itemM = (LeatherArmorMeta)item.getItemMeta();
		        itemM.setColor(Color.fromRGB(
		        		core.getConfig().getInt("NPC." + npc + ".boots.color.r"), 
		        		core.getConfig().getInt("NPC." + npc + ".boots.color.g"), 
		        		core.getConfig().getInt("NPC." + npc + ".boots.color.b")));
		        if (core.getConfig().getString("NPC." + npc + ".boots.itemisenchant").equalsIgnoreCase("yes"))
		        {
		        	itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		        }
		        item.setItemMeta((ItemMeta)itemM);
		        armorStand.setBoots(item);
	        }
	        else
	        {
	        	ItemStack item = ItemStackSerializer.deserialize(core.getConfig().getString("NPC." + npc + ".boots.material"));
		        ItemMeta itemM = item.getItemMeta();
		        if (core.getConfig().getString("NPC." + npc + ".boots.itemisenchant").equalsIgnoreCase("yes"))
		        {
		        	itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		        }
		        item.setItemMeta((ItemMeta)itemM);
		        armorStand.setBoots(item);
	        }
		}
		
	}
}
