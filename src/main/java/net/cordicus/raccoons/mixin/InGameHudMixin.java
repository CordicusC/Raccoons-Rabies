package net.cordicus.raccoons.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
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

    @Unique private static final Identifier fullTexture = RaccoonsRabies.id("hud/heart/rabies_full");
    @Unique private static final Identifier fullBlinkingTexture = RaccoonsRabies.id("hud/heart/rabies_full_blinking");
    @Unique private static final Identifier halfTexture = RaccoonsRabies.id("hud/heart/rabies_half");
    @Unique private static final Identifier halfBlinkingTexture = RaccoonsRabies.id("hud/heart/rabies_half_blinking");
    @Unique private static final Identifier hardcoreFullTexture = RaccoonsRabies.id("hud/heart/rabies_hardcore_full");
    @Unique private static final Identifier hardcoreFullBlinkingTexture = RaccoonsRabies.id("hud/heart/rabies_hardcore_full_blinking");
    @Unique private static final Identifier hardcoreHalfTexture = RaccoonsRabies.id("hud/heart/rabies_hardcore_half");
    @Unique private static final Identifier hardcoreHalfBlinkingTexture = RaccoonsRabies.id("hud/heart/rabies_hardcore_half_blinking");

    @Inject(method = "drawHeart", at = @At("HEAD"), cancellable = true)
    private void raccoonsRabies$rabiesHearts(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
        if (type != InGameHud.HeartType.ABSORBING && type != InGameHud.HeartType.CONTAINER && MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player && player.hasStatusEffect(RaccoonsRabies.RABIES_EFFECT)) {
            RenderSystem.enableBlend();
            context.drawGuiTexture(getTexture(hardcore, half, blinking), x, y, 9, 9);
            RenderSystem.disableBlend();
            ci.cancel();
        }
    }

    @Unique
    public Identifier getTexture(boolean hardcore, boolean half, boolean blinking) {
        if (!hardcore) {
            if (half) {
                return blinking ? halfBlinkingTexture : halfTexture;
            } else {
                return blinking ? fullBlinkingTexture : fullTexture;
            }
        } else if (half) {
            return blinking ? hardcoreHalfBlinkingTexture : hardcoreHalfTexture;
        } else {
            return blinking ? hardcoreFullBlinkingTexture : hardcoreFullTexture;
        }
    }
}
