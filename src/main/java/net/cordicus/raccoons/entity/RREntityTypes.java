package net.cordicus.raccoons.entity;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Vec3d;

public class RREntityTypes {
    public static final EntityType<RaccoonEntity> RACCOON = register("raccoon", EntityType.Builder.create(RaccoonEntity::new, SpawnGroup.CREATURE)
            .dimensions(0.6f, 0.6f)
            .eyeHeight(0.55f)
            .passengerAttachments(new Vec3d(0.0, 0.7, -0.1))
            .maxTrackingRange(10)
    );

    private static <T extends Entity> EntityType<T> register(String path, EntityType.Builder<T> type) {
        var key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, RaccoonsRabies.id(path));
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    public static void initMobEntities() {
    }
}
