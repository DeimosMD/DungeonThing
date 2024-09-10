package org.deimoscm.sprites.characters

import marodi.control.MarodiRunnable
import marodi.physics.Direction
import org.deimoscm.App
import org.deimoscm.sprites.Entity
import java.awt.Color

abstract class Character : Entity() {

    var health = 1.0
    var maxHealth = 1.0
    private var waitingToRegen = false
    val isDead get() = health <= 0
    var facingLeft = false
    var directionVertical = Direction.NONE

    fun move(acc: Float, frameProportion: Float, up: Boolean, down: Boolean, left: Boolean, right: Boolean) {
        if (up && left && !right && !down) {
            accAtAngle(acc, 135.0, frameProportion)
        } else if (up && right && !left && !down) {
            accAtAngle(acc, 45.0, frameProportion)
        } else if (down && left && !up && !right) {
            accAtAngle(acc, 225.0, frameProportion)
        } else if (down && right && !up && !left) {
            accAtAngle(acc, 315.0, frameProportion)
        } else {
            if (up) {
                accAtAngle(acc, 90.0, frameProportion)
            }
            if (down) {
                accAtAngle(acc, 270.0, frameProportion)
            }
            if (left) {
                accAtAngle(acc, 180.0, frameProportion)
            }
            if (right) {
                accAtAngle(acc, 0.0, frameProportion)
            }
        }
        if (left && !right) {
            facingLeft = true
        } else if (right && !left) {
            facingLeft = false
        }
        directionVertical = if (down && !up) {
            Direction.DOWN
        } else if (up && !down) {
            Direction.UP
        } else {
            Direction.NONE
        }
    }

    // draws the health bar centered at the character's position and offset by a certain amount
    fun drawHealthBar(app: App, w: Float, h: Float, color: Color, xOffset: Float, yOffset: Float) {
        val dx = x+xOffset-w/2
        val dy = y+yOffset-h/2
        app.camera.drawRect(w, h, Color.BLACK, dx, dy)
        app.camera.drawRect((w*(health/maxHealth)).toFloat(), h, color, dx, dy)
    }

    fun updateHealthRegen(app: App, timeSec: Double) {
        if (health < maxHealth && !waitingToRegen) {
            waitingToRegen = true
            app.delayRunnableSec(object : MarodiRunnable {
                override fun run() {
                    waitingToRegen = false
                    health++
                }
            }, timeSec)
        }
    }
}