package net.cordicus.raccoons.item.custom;

import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;

import java.util.List;

public class RaccoonsRabiesArmorItem extends ArmorItem {

    public RaccoonsRabiesArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public static boolean isWearingFullArmorSet(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(RaccoonsRabiesItems.BANDIT_HOOD)
                && entity.getEquippedStack(EquipmentSlot.CHEST).isOf(RaccoonsRabiesItems.BANDIT_GAMBESON)
                && entity.getEquippedStack(EquipmentSlot.LEGS).isOf(RaccoonsRabiesItems.BANDIT_GREAVES)
                && entity.getEquippedStack(EquipmentSlot.FEET).isOf(RaccoonsRabiesItems.BANDIT_BOOTS);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player && RaccoonsRabiesArmorItem.isWearingFullArmorSet(player)) {
            tooltip.add(Text.translatable("item.raccoons-rabies.bandit_armor.tooltip").formatted(Formatting.GRAY));
        }
        if (stack.isOf(RaccoonsRabiesItems.BANDIT_HOOD)) {
            if (stack.getOrDefault(RaccoonsRabiesItemComponents.HIDE_BANDIT_HOOD, false)) {
                tooltip.add(Text.translatable("tooltip.bandit_hood.hidden"));
            }
            else {
                tooltip.add(Text.translatable("tooltip.bandit_hood.visible"));
            }
            tooltip.add(Text.translatable("tooltip.bandit_hood.hint"));
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (stack.getItem().asItem() == RaccoonsRabiesItems.BANDIT_HOOD) {
            if (otherStack.isEmpty() && clickType == ClickType.RIGHT) {
                boolean hidden = stack.getOrDefault(RaccoonsRabiesItemComponents.HIDE_BANDIT_HOOD, false);
                stack.set(RaccoonsRabiesItemComponents.HIDE_BANDIT_HOOD, !hidden);
                if (player.getWorld().isClient()) {
                    player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 0.4f, 1.2f);
                }
                slot.markDirty();
                return true;
            }
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}
