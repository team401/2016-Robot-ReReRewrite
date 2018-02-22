package org.team401.robot2016.subsystems

import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj.VictorSP
import org.snakeskin.component.MotorGroup
import org.snakeskin.dsl.*
import org.snakeskin.event.Events
import org.team401.robot2016.Constants
import org.team401.robot2016.Gamepad

/*
 * 2016-Robot-ReReRewrite - Created on 2/22/18
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 2/22/18
 */

const val SHOOTER_MACHINE = "shooter"
object ShooterStates {
    const val INTAKE = "intake"
    const val SHOOT = "shoot"
    const val SHOOT_REDUCED = "kiddieShoot"
}

const val KICKER_MACHINE = "kicker"
object KickerStates {
    const val KICK = "kick"
    const val STOW = "stow"
}

val ShooterSubsystem: Subsystem = buildSubsystem {
    val left = VictorSP(Constants.MotorControllers.SHOOTER_LEFT)
    val right = VictorSP(Constants.MotorControllers.SHOOTER_RIGHT)

    val wheels = MotorGroup(left, right)

    val kicker = Solenoid(Constants.Pneumatics.KICKER)

    setup {
        left.inverted = Constants.ShooterParameters.INVERT_LEFT
        right.inverted = Constants.ShooterParameters.INVERT_RIGHT
    }

    val shooterMachine = stateMachine(SHOOTER_MACHINE) {
        state(ShooterStates.INTAKE) {
            action {
                wheels.set(Constants.ShooterParameters.INTAKE_RATE)
            }
        }

        state(ShooterStates.SHOOT) {
            action {
                wheels.set(Gamepad.readAxis { RIGHT_TRIGGER })
            }
        }

        state(ShooterStates.SHOOT_REDUCED) {
            action {
                wheels.set(Gamepad.readAxis { RIGHT_TRIGGER } * Constants.ShooterParameters.SHOOT_REDUCTION)
            }
        }

        default {
            entry {
                wheels.set(0.0)
            }
        }
    }

    val kickerMachine = stateMachine(KICKER_MACHINE) {
        state(KickerStates.KICK) {
            entry {
                kicker.set(true)
            }
        }

        state(KickerStates.STOW) {
            entry {
                kicker.set(false)
            }
        }

        default {
            entry {
                kicker.set(false)
            }
        }
    }

    on (Events.TELEOP_ENABLED) {
        shooterMachine.setState(ShooterStates.SHOOT_REDUCED)
        kickerMachine.setState(KickerStates.STOW)
    }

}