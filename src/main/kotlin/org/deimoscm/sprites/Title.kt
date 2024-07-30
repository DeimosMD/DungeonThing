package org.deimoscm.sprites

import org.deimoscm.App
import java.awt.Color

class Title : Entity() {
    override fun start(app: App) {
        width = 700f
        height = 250f
    }

    override fun draw(app: App) {
        x = -width/2
        y = 100f
        app.camera.drawRect(width, height, Color.BLUE, this)
    }

    override fun update(app: App) {

    }
}