package net.cordicus.raccoons.item.custom;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.component.RaccoonHandheldDataComponent;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RaccoonHandheldItem extends Item {

    public RaccoonHandheldItem(Settings settings) {
        super(settings);
    }

    public static int getType(ItemStack stack) {
        if (stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA) == null) {
            stack.set(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA, new RaccoonHandheldDataComponent(0, "", false));
        }
        return stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).type();
    }

    public static void setType(ItemStack stack, int type) {
        RaccoonHandheldDataComponent component = stack.getOrDefault(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA, new RaccoonHandheldDataComponent(type, "", false));
        stack.set(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA, component.withType(type));
    }

    public static boolean isBaby(ItemStack stack) {
        if (stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA) == null) {
            stack.set(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA, new RaccoonHandheldDataComponent(0, "", false));
        }
        return stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).baby();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Vec3d hitResult = context.getHitPos();

        Vec3d spawnLocation = new Vec3d(hitResult.x, hitResult.y, hitResult.z);
        if (!context.getWorld().isClient()) {
            RaccoonEntity entity = new RaccoonEntity(RaccoonsRabiesEntities.RACCOON, context.getWorld());
            entity.updatePosition(spawnLocation.x, spawnLocation.y, spawnLocation.z);
            if (context.getStack().get(DataComponentTypes.CUSTOM_DATA) != null) {
                NbtCompound nbt = context.getStack().get(DataComponentTypes.CUSTOM_DATA).copyNbt();
                if (nbt != null) {
                    entity.readNbt(nbt);
                    entity.readCustomDataFromNbt(nbt);
                    entity.updatePosition(spawnLocation.x, spawnLocation.y, spawnLocation.z);
                }
            }
            if (context.getStack().get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA) != null) {
                RaccoonHandheldDataComponent component = context.getStack().get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA);
                if (!component.owner().isEmpty()) {
                    entity.setTamed(true, true);
                    entity.setOwnerUuid(UUID.fromString(component.owner()));
                    entity.setSitting(context.getPlayer() != null && context.getPlayer().isSneaking()); // if player is sneaking when placing sets the raccoon to be sitting
                    entity.setInSittingPose(context.getPlayer() != null && context.getPlayer().isSneaking());
                }
                else {
                    entity.setTamed(false, true);
                    entity.setSitting(false);
                    entity.setInSittingPose(false);
                }
                entity.setRaccoonType(component.type());
                entity.setBaby(component.baby());
            }
            else { // in the case of no data at all, falls back on this as the default
                entity.setTamed(false, true);
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
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int raccoonType = stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).type();

        if(stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).baby()) {
            switch (raccoonType) {
                case 1: tooltip.add(Text.literal("Amethyst (Baby)").setStyle(Style.EMPTY.withColor(0xC890F0))); break;
                case 2: tooltip.add(Text.literal("Albino (Baby)").setStyle(Style.EMPTY.withColor(0x796A63))); break;
                case 4: tooltip.add(Text.literal("Cordicus (Baby)").setStyle(Style.EMPTY.withColor(0xfb6cc4))); break;
                case 5: tooltip.add(Text.literal("Nitron (Baby)").setStyle(Style.EMPTY.withColor(0xff004f))); break;
                case 6: tooltip.add(Text.literal("Bandit (Baby)").setStyle(Style.EMPTY.withColor(0x8C6E56))); break;
                case 7: tooltip.add(Text.literal("Yak (Baby)").setStyle(Style.EMPTY.withColor(0x3FC2EA))); break;
                case 8: tooltip.add(Text.literal("Rocket (Baby)").setStyle(Style.EMPTY.withColor(0x1B46DE))); break;
                default: tooltip.add(Text.literal("Normal (Baby)").formatted(Formatting.DARK_GRAY)); break;
            }
        }
        else {
            switch (raccoonType) {
                case 1: tooltip.add(Text.literal("Amethyst").setStyle(Style.EMPTY.withColor(0xC890F0))); break;
                case 2: tooltip.add(Text.literal("Albino").setStyle(Style.EMPTY.withColor(0x796A63))); break;
                case 4: tooltip.add(Text.literal("Cordicus").setStyle(Style.EMPTY.withColor(0xfb6cc4))); break;
                case 5: tooltip.add(Text.literal("Nitron").setStyle(Style.EMPTY.withColor(0xff004f))); break;
                case 6: tooltip.add(Text.literal("Bandit").setStyle(Style.EMPTY.withColor(0x8C6E56))); break;
                case 7: tooltip.add(Text.literal("Yak").setStyle(Style.EMPTY.withColor(0x3FC2EA))); break;
                case 8: tooltip.add(Text.literal("Rocket").setStyle(Style.EMPTY.withColor(0x1B46DE))); break;
                default: tooltip.add(Text.literal("Normal").formatted(Formatting.DARK_GRAY)); break;
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
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
