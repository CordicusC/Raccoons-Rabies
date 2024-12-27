package net.cordicus.raccoons;

import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesLootTableModifiers {
    public static final Identifier STRONGHOLD_CORRIDOR_ID =
            new Identifier("minecraft", "chests/stronghold_corridor");
    public static final Identifier STRONGHOLD_CROSSING_ID =
            new Identifier("minecraft","chests/stronghold_crossing");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (STRONGHOLD_CORRIDOR_ID.equals(id)) {
                LootPool.Builder poolCorridorBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.3f)) // Drop %
                        .with(ItemEntry.builder(RaccoonsRabiesItems.BANDIT_UPGRADE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                tableBuilder.pool(poolCorridorBuilder.build());
            }
            if (STRONGHOLD_CROSSING_ID.equals(id)) {
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
