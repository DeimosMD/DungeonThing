package org.deimoscm.sprites.map

import marodi.component.Drawable
import marodi.component.Sprite
import marodi.control.Game
import marodi.control.MarodiRunnable
import marodi.physics.Direction
import marodi.physics.Hitbox
import org.deimoscm.App
import java.awt.Color
import kotlin.math.ceil

class Door (
    private val x1: Float,
    private val y1: Float,
    private val direction: Direction,
    private val room: Room
) : Sprite() {

    private val x2: Float
    private val y2: Float

    var destination: Room? = null

    init {
        x = x1
        y = y1
        when (direction) {
            Direction.UP, Direction.DOWN -> {
                x2 = x1 + 200
                y2 = y1 + 100
            }
            Direction.LEFT, Direction.RIGHT -> {
                x2 = x1 + 100
                y2 = y1 + 200
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
        when (direction) {
            Direction.UP -> {
                hitbox = arrayOf(
                    Hitbox(48f, 48f, -48f,  100f),
                    Hitbox(48f, 48f, 200f, 100f)
                )
            }
            Direction.DOWN -> {
                hitbox = arrayOf(
                    Hitbox(48f, 48f, -48f, -48f),
                    Hitbox(48f, 48f, 200f, -48f)
                )
            }
            Direction.LEFT -> {
                hitbox = arrayOf(
                    Hitbox(48f, 48f, -48f, -48f),
                    Hitbox(48f, 48f, -48f, 200f)
                )
            }
            Direction.RIGHT -> {
                hitbox = arrayOf(
                    Hitbox(48f, 48f, 100f, -48f),
                    Hitbox(48f, 48f, 100f, 200f)
                )
            }
            else -> {}
        }
    }

    override fun update(game: Game) {
        val app = game as App
        when (direction) {
            Direction.UP -> {
                if (app.player.minY >= y2) {
                    onEnter(app)
                }
            }
            Direction.DOWN -> {
                if (app.player.maxY <= y1) {
                    onEnter(app)
                }
            }
            Direction.LEFT -> {
                if (app.player.maxX <= x1) {
                    onEnter(app)
                }
            }
            Direction.RIGHT -> {
                if (app.player.minX >= x2) {
                    onEnter(app)
                }
            }
            else -> {}
        }
    }

    override fun draw(game: Game) {
        val app = game as App
        if (app.player.isTouchingArea(x1, x2, y1, y2)) {
            when (direction) {
                Direction.UP -> {
                    app.statDraw.color = Color(0, 0, 0, ceil((app.player.y - y1 + app.player.height) / (y2 - y1 + app.player.height) * 255).toInt())
                }
                Direction.DOWN -> {
                    app.statDraw.color = Color(0, 0, 0, ceil((y2 - app.player.y) / (y2 - y1 + app.player.height) * 255).toInt())
                }
                Direction.LEFT -> {
                    app.statDraw.color = Color(0,0,0, ceil((x2-app.player.x)/(x2-x1+app.player.width)*255).toInt())
                }
                Direction.RIGHT -> {
                    app.statDraw.color = Color(0,0,0, ceil((app.player.x-x1+app.player.width)/(x2-x1+app.player.width)*255).toInt())
                }
                else -> {}
            }
            app.statDraw.fillRect(0, 0, app.graphicsPanel.width, app.graphicsPanel.height)
            app.statDraw.color = null
        } else {
            var dark = false
            when (direction) {
                Direction.UP -> {
                    if (app.player.minY >= y2) {
                        dark = true
                    }
                }
                Direction.DOWN -> {
                    if (app.player.maxY <= y1) {
                        dark = true
                    }
                }
                Direction.LEFT -> {
                    if (app.player.maxX <= x1) {
                        dark = true
                    }
                }
                Direction.RIGHT -> {
                    if (app.player.minX >= x2) {
                        dark = true
                    }
                }
                else -> {}
            }
            if (dark) {
                app.statDraw.color = Color(0,0,0,255)
                app.statDraw.fillRect(0, 0, app.graphicsPanel.width, app.graphicsPanel.height)
                app.statDraw.color = null
            }
        }
    }

    override fun start(game: Game) {}

    private fun onEnter(app: App) {
        ensureDestination(app)
        room.queueRemoveSprites(app, true)
        room.queueRemoveNeighbors(app)
        destination!!.queueAddSprites(app, true)
        destination!!.queueAddNeighbors(app)
        app.camera.setPos(destination)
        val drawable = object : Drawable {
            override fun draw(game: Game) {
                app.statDraw.color = Color(0,0,0,255)
                app.statDraw.fillRect(0, 0, app.graphicsPanel.width, app.graphicsPanel.height)
                app.statDraw.color = null
                val drawable = this
                app.queueRunnable(object: MarodiRunnable {
                    override fun run() {
                        app.foregroundDrawList.remove(drawable)
                    }
                })
            }
        }
        app.foregroundDrawList.add(drawable)
    }

    private fun ensureDestination(app: App) {
        if (destination == null) {
            when (direction) {
                Direction.UP -> {
                    destination = generateAppropriateRoom()
                    destination!!.x = x1 - destination!!.bottomDoor!!.x1
                    destination!!.y = y2 - destination!!.bottomDoor!!.y1 + app.player.height
                    destination!!.bottomDoor!!.destination = room
                }
                Direction.DOWN -> {
                    destination = generateAppropriateRoom()
                    destination!!.x = x1 - destination!!.topDoor!!.x1
                    destination!!.y = y1 - destination!!.topDoor!!.y2 - app.player.height
                    destination!!.topDoor!!.destination = room
                }
                Direction.LEFT -> {
                    destination = generateAppropriateRoom()
                    destination!!.x = x1 - destination!!.rightDoor!!.x2 - app.player.width
                    destination!!.y = y1 - destination!!.rightDoor!!.y1
                    destination!!.rightDoor!!.destination = room
                }
                Direction.RIGHT -> {
                    destination = generateAppropriateRoom()
                    destination!!.x = x2 - destination!!.leftDoor!!.x1 + app.player.width
                    destination!!.y = y1 - destination!!.leftDoor!!.y1
                    destination!!.leftDoor!!.destination = room
                }
                else -> {}
            }
        }
    }

    private fun generateAppropriateRoom(): Room {
        if (Math.random() > 0.5)
            when (direction) {
                Direction.UP, Direction.DOWN -> {
                    return VerticalHallwayRoom()
                }
                Direction.LEFT, Direction.RIGHT -> {
                    return HorizontalHallwayRoom()
                }
                else -> {}
            }
        return FourWayRoom()
    }
}