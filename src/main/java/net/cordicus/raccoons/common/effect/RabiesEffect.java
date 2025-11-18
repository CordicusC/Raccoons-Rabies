package net.cordicus.raccoons.common.effect;

import net.cordicus.raccoons.common.damage.RaccoonsRabiesDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;

public class RabiesEffect extends StatusEffect {
    public RabiesEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x675f5b);
    }


    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier)
    {
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        int i = 45 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        else {
            return true;
        }
    }
}
