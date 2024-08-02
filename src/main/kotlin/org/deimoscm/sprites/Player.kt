package org.deimoscm.sprites

import org.deimoscm.App
import java.awt.Color
import java.awt.event.KeyEvent

class Player : Character() {
    override fun start(app: App) {
        width = 48f
        height = 48f
        setResistance(0.9995f)
    }

    override fun draw(app: App) {
        app.camera.drawRect(width, height, Color.BLUE, this)
    }

    override fun update(app: App) {
        val up = app.keyHandler.isPressed(KeyEvent.VK_W)
        val down = app.keyHandler.isPressed((KeyEvent.VK_S))
        val left = app.keyHandler.isPressed(KeyEvent.VK_A)
        val right = app.keyHandler.isPressed(KeyEvent.VK_D)
        move(2000f, app.frameProportion, up, down, left, right)
    }
}