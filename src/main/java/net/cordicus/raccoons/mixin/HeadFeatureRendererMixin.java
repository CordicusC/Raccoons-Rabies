package net.cordicus.raccoons.mixin;

import net.cordicus.raccoons.entity.client.RaccoonRenderer;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeadFeatureRenderer.class)
public abstract class HeadFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    @Shadow @Final private HeldItemRenderer heldItemRenderer;

    public HeadFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    /*@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void raccoonsRabies$byebyebye(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if(livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem().asItem().equals(RaccoonsRabiesItems.BANDIT_HELMET)){
            if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getOrCreateNbt().getBoolean("hidden")) {
                ci.cancel();
            }
        }
    }*/ // commented out because it has issues with amarite masks and other rendered head items, should not affect the helmet's ability to hide itself

    /*@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void raccoonsRabies$renderRaccoonTrinkets(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if (TrinketsApi.getTrinketComponent(livingEntity).isPresent()) {
            if (!TrinketsApi.getTrinketComponent(livingEntity).get().getEquipped(RaccoonsRabiesItems.RACCOON).isEmpty()) {
                ItemStack stack = TrinketsApi.getTrinketComponent(livingEntity).get().getEquipped(RaccoonsRabiesItems.RACCOON).get(0).getRight();
                //matrixStack.peek().getPositionMatrix().setRotationXYZ(-360.0f, 360.0f, 0.0f);
                heldItemRenderer.renderItem(livingEntity, stack, ModelTransformationMode.HEAD, false, matrixStack, vertexConsumerProvider, i);
                //new RaccoonRenderer().getGeoModel().r
                //matrixStack.peek().getNormalMatrix().rotate(0f,180.0f, 180.0f, 0.0f);
                //MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR);

            } // awful awful awful rendering code, don't even ask
        }
    }*/
}