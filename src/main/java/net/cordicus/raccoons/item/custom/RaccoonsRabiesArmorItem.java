package net.cordicus.raccoons.item.custom;

import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RaccoonsRabiesArmorItem extends ArmorItem {
    public RaccoonsRabiesArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public boolean isHidden(ItemStack stack) {
        if (stack.getNbt() == null) {
            setHidden(stack,false);
        }
        return stack.getNbt().getBoolean("hidden");
    }

    public void setHidden(ItemStack stack, boolean hidden) {
        stack.getOrCreateNbt().putBoolean("hidden", hidden);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip (ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player && RaccoonsRabiesArmorMaterials.isWearingFullArmorSet(player))
            tooltip.add(Text.translatable("item.raccoons-rabies.bandit_armor.tooltip").formatted(Formatting.GRAY));
        if(stack.getItem().asItem() == RaccoonsRabiesItems.BANDIT_HELMET) {
            if (isHidden(stack)) {
                tooltip.add(Text.translatable("tooltip.bandit_hood.hidden"));
            } else {
                tooltip.add(Text.translatable("tooltip.bandit_hood.visible"));
            }
            tooltip.add(Text.translatable("tooltip.bandit_hood.hint"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (stack.getItem().asItem() == RaccoonsRabiesItems.BANDIT_HELMET) {
            if (otherStack.isEmpty() && clickType == ClickType.RIGHT) {
                setHidden(stack, !isHidden(stack));
                slot.markDirty();
                return true;
            }
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}
