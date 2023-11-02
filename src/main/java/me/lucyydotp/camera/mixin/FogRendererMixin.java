package me.lucyydotp.camera.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.lucyydotp.camera.CameraMod;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(
            method = "setupFog",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void setFog(Camera camera, FogRenderer.FogMode fogMode, float f, boolean bl, float g, CallbackInfo ci) {
        if (fogMode == FogRenderer.FogMode.FOG_SKY) return;
        if (CameraMod.INSTANCE.isActive()) {
            final var renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();

            final var start = CameraMod.INSTANCE.getFogStart();
            RenderSystem.setShaderFogStart(start * renderDistance);
            RenderSystem.setShaderFogEnd(renderDistance * (CameraMod.INSTANCE.getFogFalloff() + start));
            RenderSystem.setShaderFogShape(CameraMod.INSTANCE.getFogShape());
            ci.cancel();
        }
    }
}
