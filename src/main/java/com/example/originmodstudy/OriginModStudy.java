package com.example.originmodstudy;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OriginModStudy implements ModInitializer {
	public static final String MOD_ID = "arachne";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Arachne's powers are entirely data-driven (data/arachne/origins, data/arachne/powers) and
		// registered automatically by Origins/Apoli's own data loaders — nothing to register here
		// yet. This entrypoint exists for the arthropod-passive-targeting mixin's config (declared
		// in fabric.mod.json) and as the place future custom PowerFactory registrations would go.
		LOGGER.info("Arachne origin loaded");
	}
}
