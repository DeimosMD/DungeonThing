package org.deimoscm

import marodi.component.World
import marodi.control.Game
import marodi.control.MarodiRunnable
import marodi.physics.CollisionFunctionType
import marodi.physics.CollisionType
import marodi.physics.Direction
import org.deimoscm.sprites.PlayButton
import org.deimoscm.sprites.characters.Player
import org.deimoscm.sprites.Title
import org.deimoscm.sprites.map.StartingRoom

class App : Game() {

    var difficulty: Difficulty? = null
    val player = Player()
    val mapManager = MapManager(StartingRoom())

    init {
        runtimeSettings.isResizable = true
        runtimeSettings.drawFromCenter = true
        runtimeSettings.windowTitle = "Dungeon Thing"
        //runtimeSettings.isPrintFramesPerSecond = true
    }

    override fun launch() {
        super.launch()
        currentWorld.add(Title())
        currentWorld.add(PlayButton())
        backgroundDrawList.add(MenuBackground())
    }

    fun startGame(difficulty: Difficulty) {
        this.difficulty = difficulty
        mapManager.currentRoom.ensureAllDoorDestinations(this)
        queueRunnable(object : MarodiRunnable {
                override fun run() {
                    updateList.add(mapManager)
                    backgroundDrawList.removeAllElements()
                    currentWorld = World()
                    currentWorld.add(player)
                    physics.collisionHandler.addRelation(
                        Class.forName("org.deimoscm.sprites.map.Room"),
                        Class.forName("org.deimoscm.sprites.characters.Character"),
                        CollisionType(Direction.ALL, false, 0f, CollisionFunctionType.ONE_WAY)
                    )
                    physics.collisionHandler.addRelation(
                        Class.forName("org.deimoscm.sprites.map.Door"),
                        Class.forName("org.deimoscm.sprites.characters.Character"),
                        CollisionType(Direction.ALL, false, 0f, CollisionFunctionType.ONE_WAY)
                    )
                }
            })
    }
}