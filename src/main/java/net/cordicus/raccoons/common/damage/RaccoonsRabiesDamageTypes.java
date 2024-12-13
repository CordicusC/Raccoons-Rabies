package net.cordicus.raccoons.common.damage;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class RaccoonsRabiesDamageTypes {
    public static final RegistryKey<DamageType> RABIES = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RaccoonsRabies.id("rabies"));

    public static DamageSource create(World world, RegistryKey<DamageType> key) {
        return new DamageSource((world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key)));
    }
}

