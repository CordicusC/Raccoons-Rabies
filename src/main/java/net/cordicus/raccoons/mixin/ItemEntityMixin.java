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
                    if (stack.hasNbt()) {
                        NbtCompound nbt = stack.getNbt();
                        if (nbt != null) { // if  nbt is not null, then it can be assumed that everything else is there
                            raccoon.setTamed(true);
                            raccoon.setOwnerUuid(nbt.getUuid("Owner"));
                            raccoon.setRaccoonType(nbt.getInt("Type"));
                            raccoon.setBaby(nbt.getBoolean("Baby"));
                        }
                    }
                    else { // in case there is NO nbt, falls back to not tamed and default type (should only happen from /give). it also dupes with /give. I don't care how or why and it's not exactly intended so it's whatever
                        raccoon.setTamed(false);
                        raccoon.setRaccoonType(0);
                    }
                    if (!stack.getName().equals(RaccoonsRabiesItems.RACCOON.getDefaultStack().getName())) {
                        raccoon.setCustomName(stack.getName().copy().formatted(Formatting.RESET));
                    }
                    raccoon.updatePosition(this.getX(), this.getY(), this.getZ());
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
        if (this.getStack().isOf(RaccoonsRabiesItems.RACCOON)) { // item should never be able to be picked up, as it will always spawn a raccoon when dropped, nbt or not
            cir.setReturnValue(true);
        }
    }


}
