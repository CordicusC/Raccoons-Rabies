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
        if (this.getStack().isOf(RaccoonsRabiesItems.RACCOON)) {
            if (!this.getWorld().isClient()) {
                RaccoonEntity raccoon = RaccoonsRabiesEntities.RACCOON.create(this.getWorld());
                if (raccoon != null) {
                    NbtCompound nbt = this.getStack().getNbt();
                    raccoon.setTamed(true);
                    raccoon.setOwnerUuid(nbt.getUuid("Owner"));
                    raccoon.setRaccoonType(nbt.getInt("Type"));
                    raccoon.setBaby(nbt.getBoolean("Baby"));
                    if (!this.getStack().getName().equals(RaccoonsRabiesItems.RACCOON.getDefaultStack().getName())) { // custom item name AGAIN! :3
                        raccoon.setCustomName(this.getStack().getName().copy().formatted(Formatting.RESET));
                    }
                    raccoon.setPos(this.getX(), this.getY(), this.getZ());
                    raccoon.updatePosition(this.getX(), this.getY(), this.getZ());
                    this.getWorld().spawnEntity(raccoon);
                    this.getStack().decrement(1);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "cannotPickup", at = @At("HEAD"), cancellable = true)
    private void raccoonsRabies$noRaccoonItemPickup(CallbackInfoReturnable<Boolean> cir) {
        if (this.getStack().isOf(RaccoonsRabiesItems.RACCOON)) {
            cir.setReturnValue(true);
        }
    }

}
