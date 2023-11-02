package me.lucyydotp.camera.mixin;

import me.lucyydotp.camera.CameraMod;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Alters rendering to apply camera settings.
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    /**
     * When the mod is active, override the camera's FOV.
     */
    @Inject(
            method = "getFov",
            at = @At("HEAD"),
            cancellable = true
    )
    public void overrideFov(Camera camera, float f, boolean bl, CallbackInfoReturnable<Double> cir) {
        if (CameraMod.INSTANCE.isActive()) {
            cir.setReturnValue((double) CameraMod.INSTANCE.getFov());
        }
    }
}
