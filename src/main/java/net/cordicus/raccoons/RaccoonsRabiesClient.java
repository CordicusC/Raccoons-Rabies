package net.cordicus.raccoons;

import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.client.RaccoonRenderer;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.cordicus.raccoons.item.custom.RaccoonHandheldItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class RaccoonsRabiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(RaccoonsRabiesEntities.RACCOON, RaccoonRenderer::new);

        ModelPredicateProviderRegistry.register(RaccoonsRabiesItems.RACCOON, RaccoonsRabies.id("type"),
                (stack, world, entity, seed) -> ((float) RaccoonHandheldItem.getType(stack) / 10.0f));
        ModelPredicateProviderRegistry.register(RaccoonsRabiesItems.RACCOON, RaccoonsRabies.id("baby"),
                (stack, world, entity, seed) -> RaccoonHandheldItem.isBaby(stack) ? 1.0f : 0.0f);
    }
}
