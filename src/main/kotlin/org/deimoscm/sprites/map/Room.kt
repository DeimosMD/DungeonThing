package org.deimoscm.sprites.map

import marodi.component.Sprite
import marodi.control.Game
import marodi.control.MarodiRunnable
import org.deimoscm.App

abstract class Room : Sprite() {

    var topDoor: Door? = null
    var bottomDoor: Door? = null
    var leftDoor: Door? = null
    var rightDoor: Door? = null

    var sprites: ArrayList<Sprite> = ArrayList()

    fun load(app: App) {
        if (bottomDoor != null) app.currentWorld.add(bottomDoor!!)
        if (topDoor != null) app.currentWorld.add(topDoor!!)
        if (leftDoor != null) app.currentWorld.add(leftDoor!!)
        if (rightDoor != null) app.currentWorld.add(rightDoor!!)
    }

    fun queueRemoveSprites(app: App, isMainRoom: Boolean) {
        val room = this
        app.queueRunnable(object : MarodiRunnable {
            override fun run() {
                app.currentWorld.removeAll(sprites.toSet())
                if (isMainRoom) {
                    if (bottomDoor != null) app.currentWorld.remove(bottomDoor!!)
                    if (topDoor != null) app.currentWorld.remove(topDoor!!)
                    if (leftDoor != null) app.currentWorld.remove(leftDoor!!)
                    if (rightDoor != null) app.currentWorld.remove(rightDoor!!)
                }
                app.currentWorld.remove(room)
            }
        })
    }

    fun queueAddSprites(app: App, isMainRoom: Boolean) {
        val room = this
        isVisible = isMainRoom
        app.queueRunnable(object : MarodiRunnable {
            override fun run() {
                app.currentWorld.addAll(sprites.toSet())
                if (isMainRoom)
                    load(app)
                app.currentWorld.add(room)
            }
        })
    }

    fun queueAddNeighbors(app: App) {
        leftDoor?.destination?.queueAddSprites(app, false)
        rightDoor?.destination?.queueAddSprites(app, false)
        topDoor?.destination?.queueAddSprites(app, false)
        bottomDoor?.destination?.queueAddSprites(app, false)
    }

    fun queueRemoveNeighbors(app: App) {
        leftDoor?.destination?.queueRemoveSprites(app, false)
        rightDoor?.destination?.queueRemoveSprites(app, false)
        topDoor?.destination?.queueRemoveSprites(app, false)
        bottomDoor?.destination?.queueRemoveSprites(app, false)
    }

    override fun start(game: Game) {
        start(game as App)
        depth = 10f
        game.queueRunnable(object : MarodiRunnable {
            override fun run() {
                load(game)
            }
        })
    }

    abstract fun start(app: App)

    override fun draw(game: Game) {
        draw(game as App)
    }

    abstract fun draw(app: App)

    override fun update(game: Game) {
        update(game as App)
    }

    abstract fun update(app: App)

    override fun setX(x: Float) {
        super.setX(x)
        setDoors()
    }

    override fun setY(y: Float) {
        super.setY(y)
        setDoors()
    }

    abstract fun setDoors()
}