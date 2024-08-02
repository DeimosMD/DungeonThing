package org.deimoscm.sprites.rooms

import marodi.component.Sprite
import marodi.control.Game
import org.deimoscm.App

abstract class Room : Sprite() {
    override fun start(game: Game) {
        start(game as App)
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