package net.cordicus.raccoons.entity.custom;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.sounds.RaccoonsRabiesSounds;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Random;
import java.util.UUID;

public class RaccoonEntity extends TameableEntity implements Angerable, GeoEntity {
    private static final TrackedData<Integer> TYPE = DataTracker.registerData(RaccoonEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public RaccoonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super((EntityType<? extends TameableEntity>) entityType, world);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", this.getRaccoonType());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setRaccoonType(nbt.getInt("Type"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, 0);
    }

    public int getRaccoonType() {
        return this.dataTracker.get(TYPE);
    }

    public void setRaccoonType(int type) {
        this.dataTracker.set(TYPE, type);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            this.idleAnimationTimeout--;
        }

    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.raccoon.idle");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.raccoon.walk");
    protected static final RawAnimation SIT = RawAnimation.begin().thenLoop("animation.raccoon.sitting");
    protected <E extends RaccoonEntity>PlayState raccoonAnimController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        RaccoonEntity raccoon = (RaccoonEntity) event.getAnimatable();

        if (event.getAnimatable().isInSittingPose()) {
            return event.setAndContinue(SIT);
        }
        else if (event.isMoving()) {
            return event.setAndContinue(WALK);
        }
        return event.setAndContinue(IDLE);
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "raccoon", 5, this::raccoonAnimController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? f = Math.min(posDelta * 6.0F, 1.0F): 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.150));
        this.goalSelector.add(2, new TemptGoal(this, 1.250, Ingredient.ofItems(Items.ROTTEN_FLESH), true));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.150));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(8, new UniversalAngerGoal<>(this, true));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setRaccoonType(this.random.nextInt(3));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static DefaultAttributeContainer.Builder createRaccoonAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0);
        }

        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return stack.getItem().isFood();
    }

    private static final Random RANDOM = new Random();

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(this.getDamageSources().mobAttack(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            this.applyDamageEffects(this, target);
            int check = RANDOM.nextInt(2 * 100 + 1) - 100;
            if (target instanceof LivingEntity livingEntity && check <= 50) {
                StatusEffectInstance statusEffectInstance = new StatusEffectInstance(RaccoonsRabies.RABIES_EFFECT, 100, 0);
                livingEntity.addStatusEffect(statusEffectInstance);
            }
        }
        return bl;
    }


    public boolean isUniversallyAngry(World world) {
        return world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER) && this.hasAngerTime() && this.getAngryAt() == null;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.getWorld().isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || itemStack.getItem().isFood() && !this.isTamed() && !this.hasAngerTime();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else if (this.isTamed()) {
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                this.heal((float)item.getFoodComponent().getHunger());
                return ActionResult.SUCCESS;
            } else {
                ActionResult actionResult = super.interactMob(player, hand);
                if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return ActionResult.SUCCESS;
                } else {
                    return actionResult;
                }
            }
        } else if (itemStack.isFood() && !this.hasAngerTime()) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            if (this.random.nextInt(3) == 0) {
                this.setOwner(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setSitting(true);
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            } else {
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
            }

            return ActionResult.SUCCESS;
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, (double)(0.55F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    class EscapeWhenNotAggressiveGoal extends EscapeDangerGoal {
        public EscapeWhenNotAggressiveGoal(double speed) {
            super(RaccoonEntity.this, speed);
        }

        @Override
        public boolean isInDanger() {
            return !RaccoonEntity.this.isAggressive() && super.isInDanger();
        }
    }

    private boolean isAggressive() {
        return false;
    }
    class MateGoal extends AnimalMateGoal {
        public MateGoal(double chance) {
            super(RaccoonEntity.this, chance);
        }

        @Override
        protected void breed() {
            ServerWorld serverWorld = (ServerWorld)this.world;
            RaccoonEntity raccoonEntity = (RaccoonEntity) this.animal.createChild(serverWorld, this.mate);
            if (raccoonEntity != null) {
                ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
                ServerPlayerEntity serverPlayerEntity2 = this.mate.getLovingPlayer();
                ServerPlayerEntity serverPlayerEntity3 = serverPlayerEntity;
                if (serverPlayerEntity != null) {
                    raccoonEntity.addTrustedUuid(serverPlayerEntity.getUuid());
                } else {
                    serverPlayerEntity3 = serverPlayerEntity2;
                }

                if (serverPlayerEntity2 != null && serverPlayerEntity != serverPlayerEntity2) {
                    raccoonEntity.addTrustedUuid(serverPlayerEntity2.getUuid());
                }

                if (serverPlayerEntity3 != null) {
                    serverPlayerEntity3.incrementStat(Stats.ANIMALS_BRED);
                    Criteria.BRED_ANIMALS.trigger(serverPlayerEntity3, this.animal, this.mate, raccoonEntity);
                }

                this.animal.setBreedingAge(6000);
                this.mate.setBreedingAge(6000);
                this.animal.resetLoveTicks();
                this.mate.resetLoveTicks();
                raccoonEntity.setBreedingAge(-24000);
                raccoonEntity.refreshPositionAndAngles(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);
                serverWorld.spawnEntityAndPassengers(raccoonEntity);
                this.world.sendEntityStatus(this.animal, EntityStatuses.ADD_BREEDING_PARTICLES);
                if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                    this.world
                            .spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), this.animal.getRandom().nextInt(7) + 1));
                }

            }
        }
    }

    private void addTrustedUuid(UUID uuid) {
    }
    @Nullable
    public RaccoonEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        RaccoonEntity raccoonEntity = RaccoonsRabiesEntities.RACCOON.create(serverWorld);
        if (raccoonEntity != null) {
            UUID uUID = this.getOwnerUuid();
            if (uUID != null) {
                raccoonEntity.setOwnerUuid(uUID);
                raccoonEntity.setTamed(true);
            }
        }

        return raccoonEntity;
    }

    @Override
    public int getMaxLookPitchChange() {
        return this.isInSittingPose() ? 20 : super.getMaxLookPitchChange();
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int angerTime) {

    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return null;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {

    }

    @Override
    public void chooseRandomAngerTime() {
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return RaccoonsRabiesSounds.ENTITY_RACCOON_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return RaccoonsRabiesSounds.ENTITY_RACCOON_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return RaccoonsRabiesSounds.ENTITY_RACCOON_DEATH;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }
}
