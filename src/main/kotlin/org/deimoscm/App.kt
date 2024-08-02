package org.deimoscm

import marodi.component.World
import marodi.control.Game
import marodi.control.MarodiRunnable
import org.deimoscm.sprites.PlayButton
import org.deimoscm.sprites.Player
import org.deimoscm.sprites.Title

class App : Game() {

    var difficulty: Difficulty? = null

    init {
        runtimeSettings.isResizable = true
        runtimeSettings.drawFromCenter = true
        runtimeSettings.windowTitle = "Dungeon Thing"
    }

    override fun launch() {
        super.launch()
        currentWorld.add(Title())
        currentWorld.add(PlayButton())
        backgroundDrawList.add(MenuBackground())
    }

    fun startGame(difficulty: Difficulty) {
        this.difficulty = difficulty
        queueRunnable(object : MarodiRunnable {
                override fun run() {
                    currentWorld = World()
                    currentWorld.add(Player())
                }
            })
    }
}