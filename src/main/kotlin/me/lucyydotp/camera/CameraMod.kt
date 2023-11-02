package me.lucyydotp.camera

import com.mojang.blaze3d.shaders.FogShape
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource

public object CameraMod : ClientModInitializer {

    public var isActive: Boolean = false

    // TODO: these defaults are vanilla-ish but not quite spot on
    public var fov: Int = 70
    public var fogStart: Float = 0.95f
    public var fogFalloff: Float = 0.3f
    public var fogShape: FogShape = FogShape.SPHERE


    override fun onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(literal<FabricClientCommandSource>("cameramod").executes {
                CameraDialog.open()
                0
            })
        }
    }
}
