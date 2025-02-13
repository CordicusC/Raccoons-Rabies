package net.cordicus.raccoons;

import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesLootTableModifiers {
    public static final RegistryKey<LootTable> STRONGHOLD_CORRIDOR_ID =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_corridor"));
    public static final RegistryKey<LootTable> STRONGHOLD_CROSSING_ID =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_crossing"));

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.equals(STRONGHOLD_CORRIDOR_ID)) {
                LootPool.Builder poolCorridorBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.3f)) // Drop %
                        .with(ItemEntry.builder(RaccoonsRabiesItems.BANDIT_UPGRADE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                tableBuilder.pool(poolCorridorBuilder.build());
            }
            if (key.equals(STRONGHOLD_CROSSING_ID)) {
                LootPool.Builder poolCrossingBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.3f)) // Drop %
                        .with(ItemEntry.builder(RaccoonsRabiesItems.BANDIT_UPGRADE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                tableBuilder.pool(poolCrossingBuilder.build());
            }
        });
    }
}
