package org.deimoscm.sprites.map

import marodi.physics.Direction
import marodi.physics.Hitbox
import org.deimoscm.App
import java.awt.Color

class HorizontalHallwayRoom : Room() {

    init {
        setDoors()
        interiorW = 700f
        interiorH = 200f
        interiorOX = -350f
        interiorOY = -100f
    }

    override fun start(app: App) {
        hitbox = arrayOf(
            Hitbox(900f, 100f, -450f, 100f, Color.MAGENTA),
            Hitbox(900f, 100f, -450f, -200f, Color.MAGENTA)
        )
    }

    override fun draw(app: App) {
        app.camera.drawRect(700f, 200f, Color.ORANGE, x-350f, y-100f)
        app.camera.drawRect(100f, 200f, Color.GREEN, x-450f, y-100f)
        app.camera.drawRect(100f, 200f, Color.GREEN, x+350f, y-100f)
    }

    override fun update(app: App) {}

    override fun setDoors() {
        leftDoor = Door(x-450, y-100, Direction.LEFT, this)
        rightDoor = Door(x+350, y-100, Direction.RIGHT, this)
    }
}