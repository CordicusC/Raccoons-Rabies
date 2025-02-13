package net.cordicus.raccoons;

import net.cordicus.raccoons.command.SpawnRaccoonCommand;
import net.cordicus.raccoons.common.effect.RabiesEffect;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.cordicus.raccoons.sounds.RaccoonsRabiesSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaccoonsRabies implements ModInitializer {
	public static final String MOD_ID = "raccoons-rabies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final RegistryEntry<StatusEffect> RABIES_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, id("rabies"), new RabiesEffect());
	public static final TagKey<Item> HITTABLE = TagKey.of(Registries.ITEM.getKey(), id("hittable"));

	public static Identifier id(String path) {
		return Identifier.of(RaccoonsRabies.MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(RaccoonsRabiesEntities.RACCOON, RaccoonEntity.createRaccoonAttributes());
		RaccoonsRabiesItems.registerModItems();
		RaccoonsRabiesItemComponents.registerItemComponents();
		RaccoonsRabiesEntities.registerModEntities();
		RaccoonsRabiesSounds.registerSounds();
		RaccoonsRabiesLootTableModifiers.modifyLootTables();
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST),
				SpawnGroup.CREATURE,
				RaccoonsRabiesEntities.RACCOON,
				8, 1, 4);


		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			SpawnRaccoonCommand.register(server.getCommandManager().getDispatcher(), null);
		});

	}
}
