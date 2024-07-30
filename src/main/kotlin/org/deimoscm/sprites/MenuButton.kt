package org.deimoscm.sprites

import marodi.control.Mouse
import marodi.physics.Hitbox
import org.deimoscm.App
import java.awt.Color

abstract class MenuButton : Entity() {

    override fun start(app: App) {
        width = 600f
        height = 180f
        hitbox = arrayOf(Hitbox(width, height, 0f, 0f))
    }

    override fun draw(app: App) {
        app.camera.drawRect(width, height, Color.GREEN, this)
    }

    override fun update(app: App) {
        if (app.mouse.isTouching(this) && app.mouse.isBeginPressed(Mouse.LEFT_BUTTON) && visible)
            onPress(app)
    }

    abstract fun onPress(app: App)
}