package org.deimoscm.sprites

import marodi.component.World
import marodi.control.MarodiRunnable
import org.deimoscm.App
import java.awt.Color
import javax.swing.Spring.height

class PlayButton : MenuButton() {

    override fun update(app: App) {
        super.update(app)

    }

    override fun onPress(app: App) {
        app.currentWorld = World()
        app.currentWorld.add(HardButton())
        app.currentWorld.add(MediumButton())
        app.currentWorld.add(EasyButton())
    }

    override fun start(app: App) {
        super.start(app)
        x = -width/2
        y = -200f
    }

    override fun draw(app: App) {
        if (app.mouse.isTouching(this)) {
            app.camera.drawRect(width + 30, height + 30, Color.ORANGE, x - 15, y - 15)
        }
        super.draw(app)
    }
}