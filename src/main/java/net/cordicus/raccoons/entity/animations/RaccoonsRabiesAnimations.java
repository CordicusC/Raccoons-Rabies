package net.cordicus.raccoons.entity.animations;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class RaccoonsRabiesAnimations {

    public static final Animation WALKING = Animation.Builder.create(0.5f).looping()
            .addBoneAnimation("leg0",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg1",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg2",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg3",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(40f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation IDLE = Animation.Builder.create(0.041676664f)
            .addBoneAnimation("leg0",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg1",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg2",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg3",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
}