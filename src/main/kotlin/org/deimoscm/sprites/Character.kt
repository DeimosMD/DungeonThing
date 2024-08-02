package org.deimoscm.sprites

abstract class Character : Entity() {

    var health = 1
    val isAlive get() = health > 0
    val isDead get() = health <= 0
    var facingLeft = false

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
    }
}