package net.cordicus.raccoons.common.effect;

import net.cordicus.raccoons.common.damage.RaccoonsRabiesDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class RabiesEffect extends StatusEffect {
    public RabiesEffect() {
        super(StatusEffectCategory.HARMFUL, 0x675f5b);
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.damage(RaccoonsRabiesDamageTypes.create(player.getWorld(), RaccoonsRabiesDamageTypes.RABIES), 0.5F);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 45 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        else {
            return true;
        }
    }
}
