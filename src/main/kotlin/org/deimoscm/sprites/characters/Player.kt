package org.deimoscm.sprites.characters

import marodi.component.Drawable
import marodi.control.Game
import marodi.control.MarodiRunnable
import org.deimoscm.App
import java.awt.Color
import java.awt.event.KeyEvent

class Player : Character() {

    var energy: Float = 5f
    val maxEnergy: Float = 5f
    var waitingToGetEnergy = false
    val meleeAttackCooldown = 0.75f
    var timeSinceLastMeleeAttack = meleeAttackCooldown

    override fun start(app: App) {
        width = 48f
        height = 48f
        setResistance(0.9995f)
        maxHealth = 5.0
        health = 5.0
        app.foregroundDrawList.add(object : Drawable {
            override fun draw(game: Game) {
                app.statDraw.color = Color.BLUE
                app.statDraw.fillRect(
                    app.graphicsPanel.width/2-400,
                    app.graphicsPanel.height-100,
                    ((energy/maxEnergy) * 800).toInt(),
                    50
                )
                app.statDraw.color = null

                if (timeSinceLastMeleeAttack < meleeAttackCooldown) {
                    app.statDraw.color = Color.ORANGE
                    app.statDraw.fillRect(
                        app.graphicsPanel.width/2-400,
                        app.graphicsPanel.height-130,
                        ((timeSinceLastMeleeAttack/meleeAttackCooldown) * 800).toInt(),
                        15
                    )
                    app.statDraw.color = null
                }
            }
        })
    }

    override fun draw(app: App) {
        app.camera.drawRect(width, height, Color.BLUE, this)
        drawHealthBar(app, 60f, 10f, Color.BLUE, width/2, height+20)
    }

    override fun update(app: App) {
        updateHealthRegen(app, 1.5)
        val up = app.keyHandler.isPressed(KeyEvent.VK_W)
        val down = app.keyHandler.isPressed((KeyEvent.VK_S))
        val left = app.keyHandler.isPressed(KeyEvent.VK_A)
        val right = app.keyHandler.isPressed(KeyEvent.VK_D)
        if (
            app.keyHandler.isBeginPress(KeyEvent.VK_SPACE) &&
            energy >= 1 &&
            (left || right || up || down)
            ) {
            move(75000f, app.frameProportion, up, down, left, right)
            energy -= 1
        } else
            move(2000f, app.frameProportion, up, down, left, right)
        if (energy < maxEnergy && !waitingToGetEnergy) {
            waitingToGetEnergy = true
            app.delayRunnableSec(object : MarodiRunnable {
                override fun run() {
                    waitingToGetEnergy = false
                    energy++
                }
            }, 2.0)
        }
        if (app.keyHandler.isBeginPress(KeyEvent.VK_M) && timeSinceLastMeleeAttack > meleeAttackCooldown) {
            var closestEnemy: Enemy? = null
            for (ph in app.activePhysicalPositionals) {
                if (ph is Enemy) {
                    if (closestEnemy == null || (distanceTo(ph) < distanceTo(closestEnemy)))
                        closestEnemy = ph
                }
            }
            if (closestEnemy != null && distanceTo(closestEnemy) < 100) {
                closestEnemy.health--
                timeSinceLastMeleeAttack = 0f
            }
        }
        timeSinceLastMeleeAttack += app.frameProportion
    }
}
