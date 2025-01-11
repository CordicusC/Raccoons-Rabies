package net.cordicus.raccoons.entity.custom;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
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
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class RaccoonEntity extends TameableEntity implements Angerable, GeoEntity {
    private static final TrackedData<Integer> TYPE = DataTracker.registerData(RaccoonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IDLED = DataTracker.registerData(RaccoonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(RaccoonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    @Nullable
    private UUID angryAt;


    public RaccoonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super((EntityType<? extends TameableEntity>) entityType, world);
    }

    private static final Map<String, Integer> NAME_TO_VARIANT = Map.of(
            "cord", 4,
            "cordicus", 4,
            "nitron", 5,
            "n1tr0n", 5,
            "n1tr0n__", 5,
            "bandit", 6,
            "yak", 7,
            "thetrueyak", 7,
            "rocket", 8,
            "rocket raccoon", 8
    );

    public static Map<String, Integer> getNameToVariant() {
        return NAME_TO_VARIANT;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", this.getRaccoonType());
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        if (name != null) {
            String nameString = name.getString();
            if (NAME_TO_VARIANT.containsKey((nameString.toLowerCase()))) {  // named variants are no longer case sensitive
                this.setRaccoonType(NAME_TO_VARIANT.getOrDefault(nameString.toLowerCase(), 0));
            }
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        int type = nbt.getInt("Type");
        this.setRaccoonType(type);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, 0);
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    public int getRaccoonType() {
        return this.dataTracker.get(TYPE);
    }
    public boolean getRaccoonIdle() {
        return this.dataTracker.get(IDLED);
    }

    public void setRaccoonType(int type) {
        this.dataTracker.set(TYPE, type);
    }

    public void setRaccoonIdle(boolean bool) {
        this.dataTracker.set(IDLED, bool);
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
        this.setRaccoonType(this.getRandomRaccoonType());

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public int getRandomRaccoonType() { // gets random raccoon type using original code
        int number = this.random.nextInt(100) + 1;
        if (number == 1) {
            // AMETHYST
            return 1;
        } else if (number <= 19) {
            // ALBINO
            return 2;
        }
        // NORMAL
        return 0;
    }

    public int getWeightedRaccoonType(int type1, int type2) { // takes 2 ints, ~45% chance of being type1, ~45% chance of being type2, and ~10% chance to be a random type
        int number = this.random.nextInt(11);
        if (number < 4) { // 0-4, type1
            return type1;
        }
        else if (number < 10) { // 5-9, type 2
            return type2;
        }
        return getRandomRaccoonType(); // 10, random type
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
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0);
        this.setHealth(8.0F);


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

        if(!this.getWorld().isClient){
            if(itemStack.isOf(Items.AIR) && player.isSneaking() && ((this.isTamed() && this.isOwner(player)) || player.getAbilities().creativeMode)) { // creative mode players can always pick up raccoons, otherwise requires them to be tamed and the player to own them
                this.discard();
                player.setStackInHand(hand, new ItemStack(RaccoonsRabiesItems.RACCOON));
                ItemStack handStack = player.getStackInHand(hand);
                NbtCompound itemNbt = handStack.getOrCreateNbt();
                NbtCompound subNbt = new NbtCompound();
                this.saveNbt(subNbt);
                this.writeNbt(subNbt);
                this.writeCustomDataToNbt(subNbt);
                itemNbt.putInt("Type", this.getRaccoonType());
                if (this.isTamed()) {
                    itemNbt.putUuid("Owner", this.getOwnerUuid());
                }
                itemNbt.putBoolean("Baby", this.isBaby());
                handStack.getOrCreateSubNbt(RaccoonsRabies.MOD_ID).put("raccoon", subNbt);
                if (this.getCustomName() != null) { // sets custom name to item name too
                    handStack.setCustomName(this.getCustomName().copy().formatted(Formatting.ITALIC));
                }
                return ActionResult.SUCCESS;
            }
        }

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
            RaccoonEntity raccoonEntity = (RaccoonEntity) ((RaccoonEntity) this.animal).createChild(serverWorld, this.mate);
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
        int childType = getWeightedRaccoonType(this.getRaccoonType(), ((RaccoonEntity) passiveEntity).getRaccoonType());
        RaccoonEntity raccoonEntity = RaccoonsRabiesEntities.RACCOON.create(serverWorld);
        if (raccoonEntity != null) {
            raccoonEntity.setRaccoonType(childType);
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
        return (Integer)this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if(this.getRaccoonType() == 1){
            return SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME;
        }
        return RaccoonsRabiesSounds.ENTITY_RACCOON_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if(this.getRaccoonType() == 1){
            return SoundEvents.BLOCK_AMETHYST_BLOCK_STEP;
        }
        return RaccoonsRabiesSounds.ENTITY_RACCOON_HURT;
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        super.dropLoot(damageSource, causedByPlayer);

        if (this.getRaccoonType() == 1) {
            this.dropStack(new ItemStack(Items.AMETHYST_SHARD, this.random.nextInt(3)));
        } else if (this.getRaccoonType() == 2) {
            this.dropStack(new ItemStack(RaccoonsRabiesItems.ALBINO_RACCOON_FUR, this.random.nextInt(3)));
        } else {
            this.dropStack(new ItemStack(RaccoonsRabiesItems.RACCOON_FUR, this.random.nextInt(3)));
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        if(this.getRaccoonType() == 1){
            return SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK;
        }
        return RaccoonsRabiesSounds.ENTITY_RACCOON_DEATH;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }

    @Override
    public boolean damage(DamageSource source, float amount) { // owner cannot hit their own raccoons
        if (source.getAttacker() instanceof PlayerEntity player && this.isTamed()) {
            if (this.getOwner() != null && player.equals(this.getOwner())) {
                return false;
            }
        }
        return super.damage(source, amount);
    }
}
