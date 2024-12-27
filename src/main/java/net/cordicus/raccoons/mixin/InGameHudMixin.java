package net.cordicus.raccoons.mixin;

import net.cordicus.raccoons.RaccoonsRabies;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique private static final Identifier RABIES_HEARTS = new Identifier(RaccoonsRabies.MOD_ID, "textures/gui/rabies_hearts.png");

    @Inject(method = "drawHeart", at = @At("HEAD"), cancellable = true)
    private void raccoonsRabies$rabiesHearts(DrawContext context, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (!blinking && (type == InGameHud.HeartType.NORMAL || type == InGameHud.HeartType.POISONED || type == InGameHud.HeartType.WITHERED || type == InGameHud.HeartType.FROZEN) && MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player && player.hasStatusEffect(RaccoonsRabies.RABIES_EFFECT)) {
            context.drawTexture(RABIES_HEARTS, x, y, halfHeart ? 9 : 0, v, 9, 9);
            ci.cancel();
        }
    }
}
