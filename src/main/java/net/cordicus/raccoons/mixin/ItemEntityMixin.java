package net.cordicus.raccoons.mixin;

import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.cordicus.raccoons.item.component.RaccoonHandheldDataComponent;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

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
                    if (stack.get(DataComponentTypes.CUSTOM_DATA) != null) {
                        NbtCompound nbt = stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt();
                        if (nbt != null) {
                            raccoon.readNbt(nbt);
                            raccoon.readCustomDataFromNbt(nbt);
                        }
                    }
                    if (stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA) != null) {
                        RaccoonHandheldDataComponent component = stack.get(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA);
                        if (!component.owner().isEmpty()) {
                            raccoon.setTamed(true, true);
                            raccoon.setOwnerUuid(UUID.fromString(component.owner()));
                        }
                        else {
                            raccoon.setTamed(false, true);
                        }
                        raccoon.setSitting(false);
                        raccoon.setInSittingPose(false);

                        raccoon.setRaccoonType(component.type());
                        raccoon.setBaby(component.baby());
                    }
                    else { // data fallback
                        raccoon.setTamed(false, true);
                        raccoon.setSitting(false);
                        raccoon.setInSittingPose(false);
                    }
                    if (!stack.getName().equals(RaccoonsRabiesItems.RACCOON.getDefaultStack().getName())) {
                        raccoon.setCustomName(stack.getName().copy().formatted(Formatting.RESET));
                    }
                    raccoon.updatePosition(this.getX(), this.getY(), this.getZ());
                    raccoon.copyPositionAndRotation(this);
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
