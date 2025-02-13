package net.cordicus.raccoons.entity.client;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class RaccoonModel extends GeoModel<RaccoonEntity> {
	//AWAKE
	private static final Identifier RACCOON = RaccoonsRabies.id("textures/entity/raccoon.png");
	private static final Identifier AMETHYST = RaccoonsRabies.id("textures/entity/amethyst_raccoon.png");
	private static final Identifier ALBINO = RaccoonsRabies.id("textures/entity/albino_raccoon.png");
	//SLEEPING
	private static final Identifier RACCOON_S = RaccoonsRabies.id("textures/entity/raccoon_sleeping.png");
	private static final Identifier AMETHYST_S = RaccoonsRabies.id("textures/entity/amethyst_raccoon_sleeping.png");
	private static final Identifier ALBINO_S = RaccoonsRabies.id("textures/entity/albino_raccoon_sleeping.png");

	private static final Identifier CORD = RaccoonsRabies.id("textures/entity/cord_raccoon.png");
	private static final Identifier NITRON = RaccoonsRabies.id("textures/entity/nitron_raccoon.png");
	private static final Identifier CORD_S = RaccoonsRabies.id("textures/entity/cord_raccoon_sleeping.png");
	private static final Identifier NITRON_S = RaccoonsRabies.id("textures/entity/nitron_raccoon_sleeping.png");
	private static final Identifier BANDIT = RaccoonsRabies.id("textures/entity/bandit_raccoon.png");
	private static final Identifier BANDIT_S = RaccoonsRabies.id("textures/entity/bandit_raccoon_sleeping.png");
	private static final Identifier YAK = RaccoonsRabies.id("textures/entity/yak_raccoon.png");
	private static final Identifier YAK_S = RaccoonsRabies.id("textures/entity/yak_raccoon_sleeping.png");
	private static final Identifier ROCKET = RaccoonsRabies.id("textures/entity/rocket_raccoon.png");
	private static final Identifier ROCKET_S = RaccoonsRabies.id("textures/entity/rocket_raccoon_sleeping.png");


	@Override
	public Identifier getModelResource(RaccoonEntity animatable, GeoRenderer<RaccoonEntity> renderer) {
		return RaccoonsRabies.id("geo/raccoon.geo.json");
	}

	@Override
	public Identifier getTextureResource(RaccoonEntity raccoon, GeoRenderer<RaccoonEntity> renderer) {
		return getRaccoonTexture(raccoon);
	}

	// this is marked for deprecation but the horse is making me do it
	@SuppressWarnings("removal")
	@Override
	public Identifier getModelResource(RaccoonEntity raccoon) {
		return RaccoonsRabies.id("geo/raccoon.geo.json");
	}

	@SuppressWarnings("removal")
    @Override
	public Identifier getTextureResource(RaccoonEntity raccoon) {
		return getRaccoonTexture(raccoon);
	}
	// this one too. why must I implement you. and yet you yell at me

	@Override
	public Identifier getAnimationResource(RaccoonEntity animatable) {
		return RaccoonsRabies.id("animations/raccoon.animation.json");
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
			case 8 -> {
				if (raccoonType.isInSittingPose()) {
					yield ROCKET_S;
				}
				yield ROCKET;
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
