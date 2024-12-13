package net.cordicus.raccoons.entity;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesEntities {
    public static final EntityType<RaccoonEntity> RACCOON = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(RaccoonsRabies.MOD_ID, "raccoon"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RaccoonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 0.6f)).build());

    public static void registerModEntities() {
        RaccoonsRabies.LOGGER.info("Registering Entities for "+ RaccoonsRabies.MOD_ID);
    }
}
