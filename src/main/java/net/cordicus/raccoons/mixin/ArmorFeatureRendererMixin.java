package net.cordicus.raccoons.mixin;

import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    private void raccoonRabies$removeHood(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci){
        if(entity.getEquippedStack(EquipmentSlot.HEAD).contains(RaccoonsRabiesItemComponents.HIDE_BANDIT_HOOD) && armorSlot.equals(EquipmentSlot.HEAD)){
            if (entity.getEquippedStack(EquipmentSlot.HEAD).getOrDefault(RaccoonsRabiesItemComponents.HIDE_BANDIT_HOOD, false)) {
                ci.cancel();
            }
        }
    }
}