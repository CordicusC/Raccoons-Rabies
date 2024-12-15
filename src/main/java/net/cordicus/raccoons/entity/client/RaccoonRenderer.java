package net.cordicus.raccoons.entity.client;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RaccoonRenderer extends GeoEntityRenderer<RaccoonEntity> {
    public RaccoonRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RaccoonModel());
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack, RaccoonEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        if (animatable.isBaby()) {
            poseStack.scale(0.7F, 0.7F, 0.7F);
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
