package com.example.originmodstudy;

import com.example.originmodstudy.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OriginModStudy implements ModInitializer {
	public static final String MOD_ID = "arachne";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Most of Arachne's kit is entirely data-driven (data/arachne/origins, data/arachne/powers)
		// and registered automatically by Origins/Apoli's own data loaders. The Golden Spider Eye
		// (the carnivore-diet equivalent of a golden apple) is real game content though, so it needs
		// actual Java registration like any vanilla item would.
		ModItems.registerModItems();

		LOGGER.info("Arachne origin loaded");
	}

	/** Helper to build a ResourceLocation namespaced to this mod, e.g. arachne:golden_spider_eye. */
	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
