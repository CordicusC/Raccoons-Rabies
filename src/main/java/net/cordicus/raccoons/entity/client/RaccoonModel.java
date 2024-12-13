package net.cordicus.raccoons.entity.client;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.animations.RaccoonsRabiesAnimations;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class RaccoonModel extends GeoModel<RaccoonEntity> {
	//AWAKE
	private static final Identifier RACCOON = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/raccoon.png");
	private static final Identifier AMETHYST = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/amethyst_raccoon.png");
	private static final Identifier ALBINO = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/albino_raccoon.png");
	//SLEEPING
	private static final Identifier RACCOON_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/raccoon_sleeping.png");
	private static final Identifier AMETHYST_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/amethyst_raccoon_sleeping.png");
	private static final Identifier ALBINO_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/albino_raccoon_sleeping.png");
	@Override
	public Identifier getModelResource(RaccoonEntity animatable) {
		return new Identifier(RaccoonsRabies.MOD_ID, "geo/raccoon.geo.json");
	}

	@Override
	public Identifier getTextureResource(RaccoonEntity raccoon) {
		return getRaccoonTexture(raccoon);
	}

	@Override
	public Identifier getAnimationResource(RaccoonEntity animatable) {
		return new Identifier(RaccoonsRabies.MOD_ID, "animations/raccoon.animation.json");
	}

	public static Identifier getRaccoonTexture(RaccoonEntity raccoonType) {
		switch (raccoonType.getRaccoonType()) {
			case 1:
				if (raccoonType.isInSittingPose()) {
					return AMETHYST_S;
				}
				return AMETHYST;
			case 2:
				if (raccoonType.isInSittingPose()) {
					return ALBINO_S;
				}
				return ALBINO;
			default:
				if (raccoonType.isInSittingPose()) {
					return RACCOON_S;
				}
				return RACCOON;
		}
	}
}
