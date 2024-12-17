package net.cordicus.raccoons.item.client;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.cordicus.raccoons.item.custom.RaccoonHandheldItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RaccoonHandheldModel extends GeoModel<RaccoonHandheldItem> {
    private static final Identifier RACCOON_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/raccoon_sleeping.png");
    private static final Identifier AMETHYST_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/amethyst_raccoon_sleeping.png");
    private static final Identifier ALBINO_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/albino_raccoon_sleeping.png");

    private static final Identifier CORD_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/cord_raccoon_sleeping.png");
    private static final Identifier NITRON_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/nitron_raccoon_sleeping.png");
    private static final Identifier BANDIT_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/bandit_raccoon_sleeping.png");


    @Override
    public Identifier getModelResource(RaccoonHandheldItem animatable) {
        return new Identifier(RaccoonsRabies.MOD_ID, "geo/raccoon_handheld.geo.json");
    }

    @Override
    public Identifier getTextureResource(RaccoonHandheldItem raccoon) {
        return getRaccoonTexture(raccoon.getDefaultStack().getOrCreateNbt().getInt("Type"));
    }

    @Override
    public Identifier getAnimationResource(RaccoonHandheldItem animatable) {
        return new Identifier(RaccoonsRabies.MOD_ID, "animations/raccoon.animation.json");
    }

    public static Identifier getRaccoonTexture(int type) {
        return switch (type) {
            case 1 -> {
                yield AMETHYST_S;
            }
            case 2 -> {
                yield ALBINO_S;
            }
            case 4 -> {
                yield CORD_S;
            }
            case 5 -> {
                yield NITRON_S;
            }
            case 6 -> {
                yield BANDIT_S;
            }
            default -> {
                yield RACCOON_S;
            }
        };
    }
}
