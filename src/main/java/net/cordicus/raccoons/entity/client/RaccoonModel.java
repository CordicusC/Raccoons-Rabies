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

import java.util.Objects;

public class RaccoonModel extends GeoModel<RaccoonEntity> {
	//AWAKE
	private static final Identifier RACCOON = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/raccoon.png");
	private static final Identifier AMETHYST = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/amethyst_raccoon.png");
	private static final Identifier ALBINO = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/albino_raccoon.png");
	//SLEEPING
	private static final Identifier RACCOON_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/raccoon_sleeping.png");
	private static final Identifier AMETHYST_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/amethyst_raccoon_sleeping.png");
	private static final Identifier ALBINO_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/albino_raccoon_sleeping.png");

	private static final Identifier CORD = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/cord_raccoon.png");
	private static final Identifier NITRON = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/nitron_raccoon.png");
	private static final Identifier CORD_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/cord_raccoon_sleeping.png");
	private static final Identifier NITRON_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/nitron_raccoon_sleeping.png");
	private static final Identifier BANDIT = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/bandit_raccoon.png");
	private static final Identifier BANDIT_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/bandit_raccoon_sleeping.png");
	private static final Identifier YAK = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/yak_raccoon.png");
	private static final Identifier YAK_S = new Identifier(RaccoonsRabies.MOD_ID, "textures/entity/yak_raccoon_sleeping.png");


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
		return switch (raccoonType.getRaccoonType()) {
			case 1 -> {
				if (raccoonType.isInSittingPose()) {
					yield AMETHYST_S;
				}
				yield AMETHYST;
			}
			case 2 -> {
				if (raccoonType.isInSittingPose()) {
					yield ALBINO_S;
				}
				yield ALBINO;
			}
			case 4 -> {
				if (raccoonType.isInSittingPose()) {
					yield CORD_S;
				}
				yield CORD;
			}
			case 5 -> {
				if (raccoonType.isInSittingPose()) {
					yield NITRON_S;
				}
				yield NITRON;
			}
			case 6 -> {
				if (raccoonType.isInSittingPose()) {
					yield BANDIT_S;
				}
				yield BANDIT;
			}
			case 7 -> {
				if (raccoonType.isInSittingPose()) {
					yield YAK_S;
				}
				yield YAK;
			}
			default -> {
				if (raccoonType.isInSittingPose()) {
					yield RACCOON_S;
				}
				yield RACCOON;
			}
		};
	}
}
