package net.cordicus.raccoons.mixin;

import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.component.RaccoonHandheldDataComponent;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ForgingSlotsManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager slotsManager) {
        super(type, syncId, playerInventory, context, slotsManager);
    }

    @Inject(method = "onTakeOutput", at = @At("TAIL"))
    private void raccoonsRabies$renamesUpdateRaccoonType(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!stack.isOf(RaccoonsRabiesItems.RACCOON)) return; // exits if stack is not a raccoon

        if (RaccoonEntity.getNameToVariant().containsKey(stack.getName().getString().toLowerCase()) && stack.contains(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA)) {
            stack.set(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA,
                    new RaccoonHandheldDataComponent(RaccoonEntity.getNameToVariant().get(stack.getName().getString().toLowerCase()),
                            stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).owner(),
                            stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).baby()));
        }
    }

    @Inject(method = "updateResult", at = @At("TAIL"))
    private void raccoonsRabies$renamesUpdateRaccoonResultType(CallbackInfo ci) {
        ItemStack stack = this.output.getStack(getResultSlotIndex());
        if (!stack.isOf(RaccoonsRabiesItems.RACCOON)) return; // exits if stack is not a raccoon

        if (RaccoonEntity.getNameToVariant().containsKey(stack.getName().getString().toLowerCase()) && stack.contains(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA)) {
            stack.set(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA,
                    new RaccoonHandheldDataComponent(RaccoonEntity.getNameToVariant().get(stack.getName().getString().toLowerCase()),
                            stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).owner(),
                            stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA).baby()));
        }
    }

}
