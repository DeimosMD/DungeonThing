package org.deimoscm

import marodi.component.Drawable
import marodi.control.Game
import java.awt.Color

class MenuBackground : Drawable {
    override fun draw(game: Game) {
        val w = 48f
        val h = 48f
        val rowNum = game.graphicsPanel.width/w.toInt()+2
        val columnNum = game.graphicsPanel.height/h.toInt()+2
        for (row in -rowNum/2..<rowNum/2)
            for (column in -columnNum/2..<columnNum/2) {
                if ((row + column).mod(2) == 0)
                    game.camera.drawRect(w, h, Color.GRAY, row * w, column * h)
                else
                    game.camera.drawRect(w, h, Color.DARK_GRAY, row * w, column * h)
            }
    }
}