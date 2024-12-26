package net.cordicus.raccoons.item.custom;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.client.RaccoonHandheldRenderer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RaccoonHandheldItem extends Item implements GeoItem {

    public RaccoonHandheldItem(Settings settings) {
        super(settings);
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Vec3d hitResult = context.getHitPos();

        Vec3d spawnLocation = new Vec3d(hitResult.x, hitResult.y, hitResult.z);
        if (!context.getWorld().isClient) {
            RaccoonEntity entity = new RaccoonEntity(RaccoonsRabiesEntities.RACCOON, context.getWorld());
            entity.updatePosition(spawnLocation.x, spawnLocation.y, spawnLocation.z);
            if (context.getStack().hasNbt()) {
                NbtCompound nbt = context.getStack().getOrCreateNbt();
                if (nbt != null) {
                    entity.setTamed(true);
                    entity.setOwnerUuid(nbt.getUuid("Owner"));
                    entity.setRaccoonType(nbt.getInt("Type"));
                    entity.setBaby(nbt.getBoolean("Baby"));
                }
            }
            else { // in the case of no nbt, falls back on this as the default
                entity.setTamed(false);
                entity.setRaccoonType(0);
            }
            if (!context.getStack().getName().equals(this.getDefaultStack().getName())) { // custom item name :p
                entity.setCustomName(context.getStack().getName().copy().formatted(Formatting.RESET));
            }
            context.getStack().decrement(1);
            context.getWorld().spawnEntity(entity);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int type = stack.getOrCreateNbt().getInt("Type");

        if(stack.getOrCreateNbt().getBoolean("Baby")) {
            switch (type) {
                case 1: tooltip.add(Text.literal("Amethyst (Baby)").formatted(Formatting.LIGHT_PURPLE)); break;
                case 2: tooltip.add(Text.literal("Albino (Baby)").formatted(Formatting.GRAY)); break;
                case 4: tooltip.add(Text.literal("Cordicus (Baby)").formatted(Formatting.RED)); break;
                case 5: tooltip.add(Text.literal("Nitron (Baby)").formatted(Formatting.RED)); break;
                case 6: tooltip.add(Text.literal("Bandit (Baby)").formatted(Formatting.RED)); break;
                case 7: tooltip.add(Text.literal("Yak (Baby)").formatted(Formatting.RED)); break;
                default: tooltip.add(Text.literal("Normal (Baby)").formatted(Formatting.DARK_GRAY)); break;
            }
        }
        else {
            switch (type) {
                case 1: tooltip.add(Text.literal("Amethyst").formatted(Formatting.LIGHT_PURPLE)); break;
                case 2: tooltip.add(Text.literal("Albino").formatted(Formatting.GRAY)); break;
                case 4: tooltip.add(Text.literal("Cordicus").formatted(Formatting.RED)); break;
                case 5: tooltip.add(Text.literal("Nitron").formatted(Formatting.RED)); break;
                case 6: tooltip.add(Text.literal("Bandit").formatted(Formatting.RED)); break;
                case 7: tooltip.add(Text.literal("Yak").formatted(Formatting.RED)); break;
                default: tooltip.add(Text.literal("Normal").formatted(Formatting.DARK_GRAY)); break;
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private RaccoonHandheldRenderer renderer;

            @Override
            public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if(this.renderer == null)
                    this.renderer = new RaccoonHandheldRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public static boolean hasRaccoonEquipped(LivingEntity livingEntity) {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(livingEntity);
        return trinketComponent.map(component -> component.isEquipped(RaccoonsRabiesItems.RACCOON)).orElse(false);
    }

    public static ItemStack getRaccoonOnHead(LivingEntity livingEntity) {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(livingEntity);
        if (trinketComponent.isPresent()) {
            return trinketComponent.get().getEquipped(RaccoonsRabiesItems.RACCOON).get(0).getRight();
        }
        return ItemStack.EMPTY;
    }

}
