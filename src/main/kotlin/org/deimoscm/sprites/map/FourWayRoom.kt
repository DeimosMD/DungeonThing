package org.deimoscm.sprites.map

import marodi.physics.Direction
import marodi.physics.Hitbox
import org.deimoscm.App
import java.awt.Color

class FourWayRoom : Room() {

    init {
        setDoors()
        interiorW = 500f
        interiorH = 500f
        interiorOX = -250f
        interiorOY = -250f
    }

    override fun start(app: App) {
        hitbox = arrayOf(
            Hitbox(250f, 100f, -350f, 250f, Color.MAGENTA),
            Hitbox(250f, 100f, 100f, 250f, Color.MAGENTA),
            Hitbox(250f, 100f, -350f, -350f, Color.MAGENTA),
            Hitbox(250f, 100f, 100f, -350f, Color.MAGENTA),
            Hitbox(100f, 150f, -350f, 100f, Color.MAGENTA),
            Hitbox(100f, 150f, 250f, 100f, Color.MAGENTA),
            Hitbox(100f, 150f, 250f, -250f, Color.MAGENTA),
            Hitbox(100f, 150f, -350f, -250f, Color.MAGENTA)
        )
    }

    override fun draw(app: App) {
        app.camera.drawRect(500f, 500f, Color.ORANGE, x-250f, y-250f)
        app.camera.drawRect(200f, 100f, Color.GREEN, x-100f, y+250f)
        app.camera.drawRect(200f, 100f, Color.GREEN, x-100f, y-350f)
        app.camera.drawRect(100f, 200f, Color.GREEN, x+250f, y-100f)
        app.camera.drawRect(100f, 200f, Color.GREEN, x-350f, y-100f)
    }

    override fun update(app: App) {}

    override fun setDoors() {
        topDoor = Door(-100f+x, 250f+y, Direction.UP, this)
        bottomDoor = Door(x-100f, y-350f, Direction.DOWN, this)
        rightDoor = Door(x+250f, y-100f, Direction.RIGHT, this)
        leftDoor = Door(x-350f, y-100f, Direction.LEFT, this)
    }
}