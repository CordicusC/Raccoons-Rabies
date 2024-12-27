package net.cordicus.raccoons;

import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.client.RaccoonsRabiesModelLayers;
import net.cordicus.raccoons.entity.client.RaccoonModel;
import net.cordicus.raccoons.entity.client.RaccoonRenderer;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.custom.RaccoonHandheldItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.GeckoLib;

public class RaccoonsRabiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GeckoLib.initialize();

        EntityRendererRegistry.register(RaccoonsRabiesEntities.RACCOON, RaccoonRenderer::new);

        ModelPredicateProviderRegistry.register(RaccoonsRabiesItems.RACCOON, new Identifier(RaccoonsRabies.MOD_ID, "type"),
                (stack, world, entity, seed) -> ((float) RaccoonHandheldItem.getType(stack) / 10.0f));
    }
}
