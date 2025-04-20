package com.trongthang.villagercallforhelp;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagersCallForHelp implements ModInitializer {
	public static final String MOD_ID = "villagers-call-for-help";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (!world.isClient()) {
				BlockPos playerPos = player.getBlockPos();
				ServerWorld serverWorld = (ServerWorld) world;

				// Access the STRUCTURE registry dynamically via the server world
				Registry<Structure> structureRegistry = serverWorld.getRegistryManager()
						.get(RegistryKeys.STRUCTURE); // Use the RegistryKey for STRUCTURE

				for (Structure structure : structureRegistry) {
					StructureStart structureStart = serverWorld.getStructureAccessor()
							.getStructureAt(playerPos, structure);

					if (structureStart != null && structureStart.getBoundingBox().contains(playerPos)) {
						// Get the structure's identifier from the registry
						Identifier structureId = structureRegistry.getId(structure);
						System.out.println("Player is in structure: " + structureId);
					}
				}
			}
			return ActionResult.PASS;
		});

		LOGGER.info("Hello Fabric world!");
	}
}