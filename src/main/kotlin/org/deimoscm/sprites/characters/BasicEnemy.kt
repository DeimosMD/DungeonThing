package org.deimoscm.sprites.characters

import org.deimoscm.App
import java.awt.Color

class BasicEnemy : Enemy() {

    override fun start(app: App) {
        width = 48f
        height = 48f
        setResistance(0.9995f)
        maxHealth = 3.0
        health = 3.0
    }

    override fun draw(app: App) {
        app.camera.drawRect(width, height, Color.RED, this)
        drawHealthBar(app, 60f, 10f, Color.RED, width/2, height+20)
    }


    override fun update(app: App) {
        updateHealthRegen(app, 1.5)
        faceTowards(app.player)
        if (distanceTo(app.player) > 75f)
            accForward(1000f, app.frameProportion)
        else
            accForward(-1000f, app.frameProportion)
    }
}