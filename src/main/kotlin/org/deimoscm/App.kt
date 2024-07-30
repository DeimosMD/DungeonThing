package org.deimoscm

import marodi.control.Game
import org.deimoscm.sprites.PlayButton
import org.deimoscm.sprites.Title

class App : Game() {

    init {
        runtimeSettings.isResizable = true
        runtimeSettings.drawFromCenter = true
        runtimeSettings.windowTitle = "Dungeon Thing"
    }

    override fun launch() {
        super.launch()
        currentWorld.add(Title())
        currentWorld.add(PlayButton())
    }
}