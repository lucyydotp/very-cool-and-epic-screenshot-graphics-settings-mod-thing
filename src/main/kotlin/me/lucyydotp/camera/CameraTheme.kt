package me.lucyydotp.camera

import com.noxcrew.sheeplib.theme.DefaultTheme
import com.noxcrew.sheeplib.theme.Theme
import com.noxcrew.sheeplib.util.opacity

public object CameraTheme : Theme by DefaultTheme {
    
    override val theme: CameraTheme = this
    public override val colors: Theme.Colors = object : Theme.Colors by DefaultTheme.colors {
        override val dialogBackgroundAlt: Int = 0x202020 opacity 127
    }
}
