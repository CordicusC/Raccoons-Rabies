package net.cordicus.raccoons.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.item.custom.RaccoonsRabiesArmourItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(method = "isAffectedBySplashPotions", at = @At("RETURN"))
    private boolean raccoonsRabies$rabiesCancelsSplash(boolean original) {
        if (this.hasStatusEffect(RaccoonsRabies.RABIES_EFFECT)) {
            return false;
        }
        return original;
    }

    @ModifyReturnValue(method = "hurtByWater", at = @At("RETURN"))
    private boolean raccoonRabies$rabiesHurtsInWater(boolean original) {
        if (this.hasStatusEffect(RaccoonsRabies.RABIES_EFFECT)) {
            return true;
        }
        return original;
    }

    @ModifyReturnValue(method = "canHaveStatusEffect", at = @At("RETURN"))
    private boolean raccoonRabies$banditArmorRabiesImmunity(boolean original, StatusEffectInstance effect) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (RaccoonsRabiesArmourItem.isWearingFullArmorSet(entity) && effect.equals(RaccoonsRabies.RABIES_EFFECT)) {
            return false;
        }
        return original;
    }
}
