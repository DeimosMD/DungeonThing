package org.deimoscm.sprites.map

import marodi.physics.Direction
import marodi.physics.Hitbox
import org.deimoscm.App
import java.awt.Color

class VerticalHallwayRoom : Room() {

    init {
        setDoors()
    }

    override fun start(app: App) {
        hitbox = arrayOf(
            Hitbox(100f, 900f, 100f, -450f, Color.MAGENTA),
            Hitbox(100f, 900f, -200f, -450f, Color.MAGENTA)
        )
    }

    override fun draw(app: App) {
        app.camera.drawRect(200f, 700f, Color.ORANGE, x-100f, y-350f)
        app.camera.drawRect(200f, 100f, Color.GREEN, x-100f, y-450f)
        app.camera.drawRect(200f, 100f, Color.GREEN, x-100f, y+350f)
    }

    override fun update(app: App) {}

    override fun setDoors() {
        bottomDoor = Door(x-100, y-450, Direction.DOWN, this)
        topDoor = Door(x-100, y+350, Direction.UP, this)
    }
}