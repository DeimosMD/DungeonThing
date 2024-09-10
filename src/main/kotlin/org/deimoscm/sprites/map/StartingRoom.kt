package org.deimoscm.sprites.map

import marodi.physics.Direction
import marodi.physics.Hitbox
import org.deimoscm.App
import org.deimoscm.sprites.characters.BasicEnemy
import java.awt.Color

class StartingRoom : Room() {

    init {
        setDoors()
        interiorW = 500f
        interiorH = 500f
        interiorOX = -250f
        interiorOY = -250f
    }

    override fun start(app: App) {
        characters.add(BasicEnemy())
        hitbox = arrayOf(
            Hitbox(100f, 500f, 250f, -250f, Color.MAGENTA),
            Hitbox(250f, 100f, 100f, 250f, Color.MAGENTA),
            Hitbox(100f, 500f, -350f, -250f, Color.MAGENTA),
            Hitbox(700f, 100f, -350f, -350f, Color.MAGENTA),
            Hitbox(250f, 100f, -350f, 250f, Color.MAGENTA)
        )
    }

    override fun draw(app: App) {
        app.camera.drawRect(500f, 500f, Color.ORANGE, x-250f, x-250f)
        app.camera.drawRect(200f, 100f, Color.GREEN, x-100f, y+250f)
    }

    override fun update(app: App) {}

    override fun setDoors() {
        topDoor = Door(-100f+x, 250f+y, Direction.UP, this)
    }
}