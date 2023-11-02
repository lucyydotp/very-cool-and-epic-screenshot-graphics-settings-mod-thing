package me.lucyydotp.camera

import com.mojang.blaze3d.shaders.FogShape
import com.noxcrew.sheeplib.DialogContainer
import com.noxcrew.sheeplib.dialog.Dialog
import com.noxcrew.sheeplib.dialog.title.DialogTitleWidget
import com.noxcrew.sheeplib.dialog.title.TextTitleWidget
import com.noxcrew.sheeplib.layout.GridLayoutBuilder
import com.noxcrew.sheeplib.layout.grid
import com.noxcrew.sheeplib.theme.Themed
import com.noxcrew.sheeplib.widget.SliderWidget
import com.noxcrew.sheeplib.widget.ToggleButton
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.layouts.Layout
import net.minecraft.network.chat.Component
import kotlin.math.roundToInt

public class CameraDialog private constructor(x: Int, y: Int) : Dialog(x, y), Themed by CameraTheme {
    public companion object {
        private const val SLIDER_WIDTH = 100
        private const val TEXT_WIDTH = 70

        private var dialog: CameraDialog? = null

        public fun open() {
            if (dialog == null) {
                dialog = CameraDialog(10, 10).also { DialogContainer += it }
                CameraMod.isActive = true
            } else {
                DialogContainer.focused = dialog
            }
        }
    }

    override fun onClose() {
        dialog = null
        CameraMod.isActive = false
    }

    override val title: DialogTitleWidget = TextTitleWidget(this, Component.translatable("screenshot-settings-mod.dialog.title"))

    private inline fun GridLayoutBuilder.slider(
            name: String,
            min: Int,
            max: Int,
            current: Int,
            crossinline onSet: (Int) -> Unit
    ) {
        val widget = StringWidget(
                Component.translatable(name, current),
                Minecraft.getInstance().font,
        ).atBottom(0).also { it.width = TEXT_WIDTH }
        SliderWidget(SLIDER_WIDTH, min, max, this@CameraDialog, current) {
            onSet(it)
            widget.message = Component.translatable(name, it)
        }.onLastRow(1)
    }

    override fun layout(): Layout = grid {

        slider("screenshot-settings-mod.fov", 1, 170, CameraMod.fov) { CameraMod.fov = it }

        StringWidget(Component.translatable("screenshot-settings-mod.fog"), Minecraft.getInstance().font).alignCenter().atBottom(0, 2)

        slider(
                "screenshot-settings-mod.fog.start",
                0,
                100,
                (CameraMod.fogStart * 100).roundToInt(),
        ) { CameraMod.fogStart = it / 100f }
        slider(
                "screenshot-settings-mod.fog.falloff",
                0,
                100,
                (CameraMod.fogFalloff * 100).roundToInt(),
        ) { CameraMod.fogFalloff = it / 100f }

        ToggleButton.enum<FogShape>(
                "screenshot-settings-mod.fog.shape",
                Component.translatable("screenshot-settings-mod.fog.shape"),
                this@CameraDialog,
        ) {
            CameraMod.fogShape = it
        }.atBottom(0, 2)
            // https://github.com/Noxcrew/sheeplib/issues/16
            .width = SLIDER_WIDTH
    }
}
