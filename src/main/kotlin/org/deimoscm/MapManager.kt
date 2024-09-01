package org.deimoscm

import marodi.component.Updatable
import marodi.control.Game
import org.deimoscm.sprites.map.Room
import org.deimoscm.sprites.characters.Character
import org.deimoscm.sprites.characters.Player
import org.deimoscm.sprites.map.Door

class MapManager (
    var currentRoom: Room
) : Updatable {

    override fun update(game: Game?) {
        removeRoomContainedSprites(game as App)
        currentRoom.checkCharacterLocations()
        currentRoom.loadSprites(game)
    }

    private fun removeRoomContainedSprites(app: App) {
        for (spr in ArrayList(app.currentWorld)) {
            if (
                (spr is Character && spr !is Player)
                || spr is Room
                || spr is Door
                )
                app.currentWorld.remove(spr)
        }
    }
}