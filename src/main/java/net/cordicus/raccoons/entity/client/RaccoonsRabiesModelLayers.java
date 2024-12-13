package net.cordicus.raccoons.entity.client;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesModelLayers {
    public static final EntityModelLayer RACCOON =
            new EntityModelLayer(new Identifier(RaccoonsRabies.MOD_ID, "raccoon"), "main");
}
