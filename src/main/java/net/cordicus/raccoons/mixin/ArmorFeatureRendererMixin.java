package net.cordicus.raccoons.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends BipedEntityRenderState, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @WrapMethod(method = "renderArmor")
    private void raccoonRabies$removeHood(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, EquipmentSlot slot, int light, A armorModel, Operation<Void> original){
        if(!(stack.contains(RaccoonsRabiesItemComponents.HIDE_BANDIT_HOOD) && slot.equals(EquipmentSlot.HEAD))){
            if (!(stack.getOrDefault(RaccoonsRabiesItemComponents.HIDE_BANDIT_HOOD, false))) {
                original.call(matrices, vertexConsumers, stack, slot, light, armorModel);
            }
        }
    }
}