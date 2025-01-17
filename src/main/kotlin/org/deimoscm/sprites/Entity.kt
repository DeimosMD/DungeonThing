package org.deimoscm.sprites

import marodi.component.Sprite
import marodi.control.Game
import marodi.physics.Hitbox
import org.deimoscm.App

abstract class Entity : Sprite() {

    var width: Float = 0f
    var height: Float = 0f

    override fun start(game: Game) {
        start(game as App)
        hitbox = arrayOf(Hitbox(width, height, 0f, 0f))
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
}