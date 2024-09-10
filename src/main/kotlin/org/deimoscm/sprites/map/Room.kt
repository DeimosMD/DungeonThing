package org.deimoscm.sprites.map

import marodi.component.Sprite
import marodi.control.Game
import org.deimoscm.App
import org.deimoscm.sprites.characters.Character

abstract class Room : Sprite() {

    var topDoor: Door? = null
    var bottomDoor: Door? = null
    var leftDoor: Door? = null
    var rightDoor: Door? = null

    var characters: ArrayList<Character> = ArrayList()
    var inactiveCharacters: ArrayList<Character> = ArrayList() // added to characters when room is entered

    var interiorW = 0.0f
    var interiorH = 0.0f
    var interiorOX = 0.0f
    var interiorOY = 0.0f

    // how far away this room is from the starting room
    var root: Int = 0

    override fun start(game: Game) {
        start(game as App)
        depth = 10f
    }

    // moves characters to what rooms they supposed to be in based on their physical locations
    fun checkCharacterLocations() {

        // checks doors with a specific character and adds to door's room if necessary
        fun checkDoors(ch: Character): Boolean {
            for (door in arrayOf(bottomDoor, topDoor, leftDoor, rightDoor))
                if (door != null)
                    if (door.destination!!.isInHere(ch)) {
                        door.destination!!.characters.add(ch)
                        return true
                    }
            return false
        }

        // used to keep track of the most recent room any character has been in
        // is used when a character is not currently physically in any room
        val recentRoomMap: HashMap<Character, Room> = HashMap()
        for (ch in characters)
            recentRoomMap.put(ch, this)
        // chList is all characters that are in currently loaded rooms
        // removes characters from this room and adds them to chList
        val chList: ArrayList<Character> = ArrayList(characters)
        characters.clear()
        // removes characters from all neighboring rooms and adds them to chList
        // if changes are necessary, consider reimplementing as for loop before making changes
        if (bottomDoor != null) {
            val bottomChList = bottomDoor!!.destination!!.characters
            chList.addAll(bottomChList)
            for (ch in bottomChList)
                recentRoomMap.put(ch, bottomDoor!!.destination!!)
            bottomChList.clear()
        }
        if (topDoor != null) {
            val topChList = topDoor!!.destination!!.characters
            chList.addAll(topChList)
            for (ch in topChList)
                recentRoomMap.put(ch, topDoor!!.destination!!)
            topChList.clear()
        }
        if (leftDoor != null) {
            val leftChList = leftDoor!!.destination!!.characters
            chList.addAll(leftChList)
            for (ch in leftChList)
                recentRoomMap.put(ch, leftDoor!!.destination!!)
            leftChList.clear()
        }
        if (rightDoor != null) {
            val rightChList = rightDoor!!.destination!!.characters
            chList.addAll(rightChList)
            for (ch in rightChList)
                recentRoomMap.put(ch, rightDoor!!.destination!!)
            rightChList.clear()
        }
        // checks locations of characters and adds them to appropriate rooms
        for (ch in chList) {
            if (isInHere(ch)) {
                characters.add(ch)
                continue
            }
            if (checkDoors(ch)) {
                continue
            }
            // if it's not currently in any room, it just goes in its most recent room
            // is added into inactiveCharacters so it does not start updating while outside the room
            recentRoomMap[ch]!!.inactiveCharacters.add(ch)
        }
    }

    // loads room with doors and characters, and loads neighboring rooms with their characters (but not doors)
    // makes only current room and its doors and characters visible, other room's stuff will be invisible
    fun loadSprites(app: App) {
        // adds this room and it's characters
        app.currentWorld.add(this)
        isVisible = true
        characters += inactiveCharacters
        inactiveCharacters = ArrayList()
        for (ch in characters) {
            app.currentWorld.add(ch)
            ch.isVisible = true
        }
        // adds neighboring rooms and their characters and this room's doors
        for (door in arrayOf(bottomDoor, topDoor, leftDoor, rightDoor))
            if (door != null) {
                app.currentWorld.add(door)
                door.isVisible = true
                app.currentWorld.add(door.destination!!)
                door.destination!!.isVisible = false
                for (ch in door.destination!!.characters) {
                    app.currentWorld.add(ch)
                    ch.isVisible = false
                }
            }
    }

    // checks whether the given character is touching the interior or any of the doors
    private fun isInHere(ch: Character): Boolean {
        if (bottomDoor != null)
            if (
                ch.isTouchingArea(bottomDoor!!.x1, bottomDoor!!.x2, bottomDoor!!.y1, bottomDoor!!.y2)
                ) return true

        if (topDoor != null)
            if (
                ch.isTouchingArea(topDoor!!.x1, topDoor!!.x2, topDoor!!.y1, topDoor!!.y2)
                ) return true

        if (leftDoor != null)
            if (
                ch.isTouchingArea(leftDoor!!.x1, leftDoor!!.x2, leftDoor!!.y1, leftDoor!!.y2)
                ) return true

        if (rightDoor != null)
            if (
                ch.isTouchingArea(rightDoor!!.x1, rightDoor!!.x2, rightDoor!!.y1, rightDoor!!.y2)
                ) return true

        return ch.isTouchingArea(
            x+interiorOX,
            x+interiorOX+interiorW,
            y+interiorOY,
            y+interiorOY+interiorH
        )
    }

    // ensures all doors have a destination
    // called when a room is entered by the player
    // and is called at the start of the game
    fun ensureAllDoorDestinations(app: App) {
        for (door in arrayOf(bottomDoor, topDoor, leftDoor, rightDoor))
            door?.ensureDestination(app)
    }

    //fun removeCharacter

    abstract fun start(app: App)

    override fun draw(game: Game) {
        draw(game as App)
    }

    abstract fun draw(app: App)

    override fun update(game: Game) {
        val app = game as App
        update(app)
    }

    abstract fun update(app: App)

    override fun setX(x: Float) {
        super.setX(x)
        setDoors()
    }

    override fun setY(y: Float) {
        super.setY(y)
        setDoors()
    }

    // is called when a room is initialized
    // and called a second time when its position is adjusted in Door.ensureDestination
    abstract fun setDoors()
}