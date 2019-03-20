package com.tagnumelite.projecteintegration.plugins;

import com.brandon3055.draconicevolution.DEFeatures;
import com.brandon3055.draconicevolution.api.fusioncrafting.FusionRecipeAPI;
import com.brandon3055.draconicevolution.api.fusioncrafting.IFusionRecipe;
import com.tagnumelite.projecteintegration.api.PEIPlugin;
import com.tagnumelite.projecteintegration.api.RegPEIPlugin;
import com.tagnumelite.projecteintegration.api.mappers.PEIMapper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

@RegPEIPlugin(modid = "draconicevolution")
public class PluginDraconicEvolution extends PEIPlugin {
	public PluginDraconicEvolution(String modid, Configuration config) {
		super(modid, config);
	}

	@Override
	public void setup() {
		addEMC(DEFeatures.draconiumDust, 8192);
		addEMC(DEFeatures.dragonHeart, 262144);
		addEMC(DEFeatures.chaosShard, 1024000);

		addMapper(new FusionMapper());
	}

	private class FusionMapper extends PEIMapper {
		public FusionMapper() {
			super("fusion", "Draconic Evolution Fusion Crafting");
		}

		@Override
		public void setup() {
			for (IFusionRecipe recipe : FusionRecipeAPI.getRecipes()) {
				addRecipe(recipe.getRecipeOutput(ItemStack.EMPTY), recipe.getRecipeIngredients().toArray());
			}
		}
	}
}
