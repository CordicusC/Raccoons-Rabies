package net.cordicus.raccoons.item.custom;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RaccoonHandheldItem extends Item {

    public RaccoonHandheldItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Vec3d hitResult = context.getHitPos();

        Vec3d spawnLocation = new Vec3d(hitResult.x, hitResult.y, hitResult.z);
        NbtCompound nbt = context.getStack().getOrCreateNbt();
        if (!context.getWorld().isClient) {
            RaccoonEntity entity = new RaccoonEntity(RaccoonsRabiesEntities.RACCOON, context.getWorld());
            entity.updatePosition(spawnLocation.x, spawnLocation.y, spawnLocation.z);
            entity.setTamed(true);
            entity.setOwnerUuid(nbt.getUuid("Owner"));
            entity.setRaccoonType(nbt.getInt("Type"));
            entity.setBaby(nbt.getBoolean("Baby"));
            context.getStack().decrement(1);
            context.getWorld().spawnEntity(entity);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int type = stack.getOrCreateNbt().getInt("Type");

        if(stack.getOrCreateNbt().getBoolean("Baby")){
            if(type == 1){
                tooltip.add(Text.literal("Amethyst (Baby)").formatted(Formatting.LIGHT_PURPLE));
            } else if(type == 2){
                tooltip.add(Text.literal("Albino (Baby)").formatted(Formatting.GRAY));
            }else if(type == 4){
                tooltip.add(Text.literal("Cordicus (Baby)").formatted(Formatting.RED));
            }else if(type == 5){
                tooltip.add(Text.literal("Nitron (Baby)").formatted(Formatting.RED));
            }else if(type == 6){
                tooltip.add(Text.literal("Bandit (Baby)").formatted(Formatting.RED));
            } else {
                tooltip.add(Text.literal("Normal (Baby)").formatted(Formatting.DARK_GRAY));
            }
        } else {
            if(type == 1){
                tooltip.add(Text.literal("Amethyst").formatted(Formatting.LIGHT_PURPLE));
            } else if(type == 2){
                tooltip.add(Text.literal("Albino").formatted(Formatting.GRAY));
            }else if(type == 4){
                tooltip.add(Text.literal("Cordicus").formatted(Formatting.RED));
            }else if(type == 5){
                tooltip.add(Text.literal("Nitron").formatted(Formatting.RED));
            }else if(type == 6) {
                tooltip.add(Text.literal("Bandit").formatted(Formatting.RED));
            }else {
                tooltip.add(Text.literal("Normal").formatted(Formatting.DARK_GRAY));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
