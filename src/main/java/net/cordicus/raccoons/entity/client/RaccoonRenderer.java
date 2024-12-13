package net.cordicus.raccoons.entity.client;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RaccoonRenderer extends GeoEntityRenderer<RaccoonEntity> {
    public RaccoonRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RaccoonModel());
    }
}
