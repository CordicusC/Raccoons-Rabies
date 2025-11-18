package net.cordicus.raccoons;

import net.cordicus.raccoons.entity.RREntityTypes;
import net.cordicus.raccoons.entity.client.RaccoonRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RaccoonsRabiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(RREntityTypes.RACCOON, RaccoonRenderer::new);
    }
}