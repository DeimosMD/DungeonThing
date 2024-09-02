package org.deimoscm.sprites.characters

import org.deimoscm.App
import java.awt.Color
import java.awt.event.KeyEvent

class Player : Character() {
    override fun start(app: App) {
        width = 48f
        height = 48f
        setResistance(0.9995f)
        maxHealth = 10.0
        health = 10.0
    }

    override fun draw(app: App) {
        app.camera.drawRect(width, height, Color.BLUE, this)
        drawHealthBar(app, 60f, 10f, Color.RED, width/2, height+20)
    }

    override fun update(app: App) {
        val up = app.keyHandler.isPressed(KeyEvent.VK_W)
        val down = app.keyHandler.isPressed((KeyEvent.VK_S))
        val left = app.keyHandler.isPressed(KeyEvent.VK_A)
        val right = app.keyHandler.isPressed(KeyEvent.VK_D)
        if (app.keyHandler.isBeginPress(KeyEvent.VK_SHIFT) && overallVelo <= 280)
            move(75000f, app.frameProportion, up, down, left, right)
        else
            move(2000f, app.frameProportion, up, down, left, right)
    }
}
