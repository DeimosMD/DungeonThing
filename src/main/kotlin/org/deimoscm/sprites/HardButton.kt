package org.deimoscm.sprites

import org.deimoscm.App
import org.deimoscm.Difficulty
import java.awt.Color

class HardButton : MenuButton() {
    override fun onPress(app: App) {
        app.startGame(Difficulty.HARD)
    }

    override fun start(app: App) {
        super.start(app)
        x = -width/2
        y = 175f
    }

    override fun draw(app: App) {
        if (app.mouse.isTouching(this)) {
            app.camera.drawRect(width + 30, height + 30, Color.ORANGE, x - 15, y - 15)
        }
        super.draw(app)
    }
}