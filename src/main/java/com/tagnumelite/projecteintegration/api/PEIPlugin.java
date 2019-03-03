package com.tagnumelite.projecteintegration.api;

import com.tagnumelite.projecteintegration.api.mappers.PEIMapper;
import com.tagnumelite.projecteintegration.utils.ConfigHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public abstract class PEIPlugin {
	public final String modid;
	public final Configuration config;
	public final String category;

	public PEIPlugin(String modid, Configuration config) {
		this.modid = modid;
		this.config = config;
		this.category = ConfigHelper.getPluginCategory(modid);
	}
	
	/**
	 * addEMC and addMapper should be run here
	 */
	public abstract void setupIntegration();
	
	/**
	 * 
	 * @param item {@code Item} The item to add
	 * @param meta {@code int} Metadata value of the item
	 * @param base_emc {@code int} The Base EMC Value
	 */
	protected void addEMC(Item item, int meta, int base_emc) {
		addEMC(new ItemStack(item, 1, meta), base_emc);
	}
	
	protected void addEMC(Item item, int base_emc) {
		addEMC(new ItemStack(item), base_emc);
	}
	
	protected void addEMC(ItemStack item, int base_emc) {
		addEMC(item, base_emc, "");
	}
	
	/**
	 * @param item
	 * @param base_emc
	 */
	protected void addEMC(ItemStack item, int base_emc, String extra) {
		setEMC(item, config.getInt("emc_item_" + item.getUnlocalizedName(), category, base_emc, -1, Integer.MAX_VALUE, "Set the EMC for the item '" + item.getDisplayName() + "' " + extra));
	}

	protected void addEMC(String name, Object obj, int base_emc) {
		addEMC(name, obj, base_emc, "");
	}
	
	/**
	 * @param name {@code String} The name of the object
	 * @param obj {@code Object} The object that will have the emc attached to it.
	 * @param base_emc {@code int} The Base emc of the object
	 * @param extra {@code String} Extra information to be added to the comment.
	 */
	protected void addEMC(String name, Object obj, int base_emc, String extra) {
		setEMC(obj, config.getInt("emc_" + name.toLowerCase(), category, base_emc, -1, Integer.MAX_VALUE, "Set the EMC value for " + name + ' ' + extra));
	}
	
	private void setEMC(Object obj, long emc) {
		PEIApi.emc_proxy.registerCustomEMC(obj, emc);
	}
	
	private void setEMC(ItemStack item, long emc) {
		PEIApi.emc_proxy.registerCustomEMC(item, emc);
	}
	
	protected void addMapper(PEIMapper mapper) {
		if (config.getBoolean("enable_" + mapper.name.toLowerCase().replace(' ', '_') + "_mapper", category, true, mapper.desc))
			mapper.setup();
	}
}
