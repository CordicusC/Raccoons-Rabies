package net.cordicus.raccoons.mixin;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.item.custom.RaccoonsRabiesArmorMaterials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method="isAffectedBySplashPotions", at=@At("HEAD"), cancellable = true)
    private void raccoonsRabies$rabiesCancelSplash(CallbackInfoReturnable<Boolean>cir) {
        LivingEntity entity = (LivingEntity)(Object) this;
        if (entity.hasStatusEffect(RaccoonsRabies.RABIES_EFFECT))
            cir.setReturnValue(false);
    }
    @Inject(method ="hurtByWater", at=@At("HEAD"), cancellable = true)
    private void raccoonRabies$rabiesHurtByWater(CallbackInfoReturnable<Boolean>cir) {
        LivingEntity entity = (LivingEntity)(Object) this;
        if (entity.hasStatusEffect(RaccoonsRabies.RABIES_EFFECT))
            cir.setReturnValue(true);
    }
    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void raccoonRabies$canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity)(Object) this;
        if (RaccoonsRabiesArmorMaterials.isWearingFullArmorSet(entity)&& effect.getEffectType().equals(RaccoonsRabies.RABIES_EFFECT))
            cir.setReturnValue(false);
    }
}
