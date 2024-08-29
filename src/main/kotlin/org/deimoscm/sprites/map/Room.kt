package org.deimoscm.sprites.map

import marodi.component.Sprite
import marodi.control.Game
import marodi.control.MarodiRunnable
import org.deimoscm.App
import org.deimoscm.sprites.characters.Character
import org.deimoscm.sprites.characters.Player

abstract class Room : Sprite() {

    var topDoor: Door? = null
    var bottomDoor: Door? = null
    var leftDoor: Door? = null
    var rightDoor: Door? = null

    var characters: ArrayList<Character> = ArrayList()

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
                app.currentWorld.removeAll(characters.toSet())
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
                app.currentWorld.addAll(characters.toSet())
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
        val app = game as App
        if (isVisible) {
            checkEntityLocations(app)
        }
        update(app)
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

    // checks if an entity has left its current room, and if so changes it's visibility and the room's entity list
    private fun checkEntityLocations(app: App) {
        for (ch in app.activePhysicalPositionals)
            if (ch is Character && ch !is Player && ch.hitbox.isNotEmpty()) {
                ch.isVisible = true
                characters.add(ch)
                leftDoor?.destination?.characters?.remove(ch)
                rightDoor?.destination?.characters?.remove(ch)
                topDoor?.destination?.characters?.remove(ch)
                bottomDoor?.destination?.characters?.remove(ch)
                if (leftDoor != null)
                    if (leftDoor!!.x1 >= ch.maxX) {
                        ch.isVisible = false
                        characters.remove(ch)
                        leftDoor!!.ensureDestination(app)
                        leftDoor!!.destination?.characters?.add(ch)
                    }
                if (rightDoor != null)
                    if (rightDoor!!.x2 <= ch.minX) {
                        ch.isVisible = false
                        characters.remove(ch)
                        rightDoor!!.ensureDestination(app)
                        rightDoor!!.destination?.characters?.add(ch)
                    }
                if (bottomDoor != null)
                    if (bottomDoor!!.y1 >= ch.maxY) {
                        ch.isVisible = false
                        characters.remove(ch)
                        bottomDoor!!.ensureDestination(app)
                        bottomDoor!!.destination?.characters?.add(ch)
                    }
                if (topDoor != null)
                    if (topDoor!!.y2 <= ch.minY) {
                        ch.isVisible = false
                        characters.remove(ch)
                        topDoor!!.ensureDestination(app)
                        topDoor!!.destination?.characters?.add(ch)
                    }
            }
    }
}