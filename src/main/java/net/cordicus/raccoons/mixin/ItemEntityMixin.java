package net.cordicus.raccoons.mixin;

import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.custom.RaccoonHandheldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStack();

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void raccoonsRabies$spawnRaccoonOnDrop(CallbackInfo ci) {
        ItemStack stack = this.getStack();
        if (stack != null && stack.isOf(RaccoonsRabiesItems.RACCOON)) {
            if (!this.getWorld().isClient()) {
                RaccoonEntity raccoon = RaccoonsRabiesEntities.RACCOON.create(this.getWorld());
                if (raccoon != null) {
                    NbtCompound nbt = stack.getNbt();
                    if (nbt != null) {
                        raccoon.setTamed(true);
                        if (nbt.contains("Owner")) {
                            raccoon.setOwnerUuid(nbt.getUuid("Owner"));
                            stack.decrement(1);
                            ci.cancel();
                            return;
                        }
                        if (nbt.contains("Type", NbtElement.NUMBER_TYPE)) {
                            raccoon.setRaccoonType(nbt.getInt("Type"));
                            stack.decrement(1);
                            ci.cancel();
                            return;
                        }
                        if (nbt.contains("Baby", NbtElement.BYTE_TYPE)) {
                            raccoon.setBaby(nbt.getBoolean("Baby"));
                            stack.decrement(1);
                            ci.cancel();
                            return;
                        }
                        if (!stack.getName().equals(RaccoonsRabiesItems.RACCOON.getDefaultStack().getName())) {
                            raccoon.setCustomName(stack.getName().copy().formatted(Formatting.RESET));
                        }
                    }
                    raccoon.setPos(this.getX(), this.getY(), this.getZ());
                    this.getWorld().spawnEntity(raccoon);
                    stack.decrement(1);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "cannotPickup", at = @At("HEAD"), cancellable = true)
    private void raccoonsRabies$noRaccoonItemPickup(CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = this.getStack();
        if (stack != null && stack.isOf(RaccoonsRabiesItems.RACCOON)) {
            cir.setReturnValue(true);
        }
    }


}
