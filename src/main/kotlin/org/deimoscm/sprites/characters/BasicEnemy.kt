package org.deimoscm.sprites.characters

import org.deimoscm.App
import java.awt.Color

class BasicEnemy : Character() {

    override fun start(app: App) {
        width = 48f
        height = 48f
        setResistance(0.9995f)
        maxHealth = 2.0
        health = 2.0
    }

    override fun draw(app: App) {
        app.camera.drawRect(width, height, Color.RED, this)
        drawHealthBar(app, 60f, 10f, Color.GREEN, width/2, height+20)
    }


    override fun update(app: App) {
        move(1000f, app.frameProportion, up=true, down=false, left=false, right=false)
    }
}