package net.cordicus.raccoons.common.damage;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RaccoonsRabiesDamageTypes
{
    public static final RegistryKey<DamageType> RABIES = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RaccoonsRabies.id("rabies"));


}

