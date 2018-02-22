package org.team401.robot2016.subsystems

import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj.Talon
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

const val DRIVE_MACHINE = "drive"
object DriveStates {
    const val DRIVE = "openLoop"
    const val DRIVE_REDUCED = "openLoopKiddie"
}

const val SHIFT_MACHINE = "shifter"
object ShifterStates {
    const val HIGH = "high"
    const val LOW = "low"
}

val DrivetrainSubsystem: Subsystem = buildSubsystem {
    val left1 = Talon(Constants.MotorControllers.DRIVE_LEFT_1)
    val left2 = Talon(Constants.MotorControllers.DRIVE_LEFT_2)
    val left3 = Talon(Constants.MotorControllers.DRIVE_LEFT_3)
    val right1 = Talon(Constants.MotorControllers.DRIVE_RIGHT_1)
    val right2 = Talon(Constants.MotorControllers.DRIVE_RIGHT_2)
    val right3 = Talon(Constants.MotorControllers.DRIVE_RIGHT_3)
    val left = MotorGroup(left1, left2, left3)
    val right = MotorGroup(right1, right2, right3)

    val shifter = Solenoid(Constants.Pneumatics.DRIVE_SHIFTER)

    setup {
        right1.inverted = true
        left2.inverted = true
        left3.inverted = true
    }

    val driveMachine = stateMachine(DRIVE_MACHINE) {
        var translation = 0.0
        var rotation = 0.0

        fun drive() {
            left.set(translation + rotation)
            right.set(translation - rotation)
        }

        state (DriveStates.DRIVE) {
            action {
                translation = Gamepad.readAxis { LEFT_Y }
                rotation = Gamepad.readAxis { RIGHT_X }
                drive()
            }
        }

        state (DriveStates.DRIVE_REDUCED) {
            action {
                translation = Gamepad.readAxis { LEFT_Y } * Constants.DrivetrainParameters.TRANSLATION_REDUCTION
                rotation = Gamepad.readAxis { RIGHT_X } * Constants.DrivetrainParameters.ROTATION_REDUCTION
                drive()
            }
        }

        default {
            entry {
                translation = 0.0
                rotation = 0.0
                drive()
            }
        }
    }

    val shiftMachine = stateMachine(SHIFT_MACHINE) {
        state(ShifterStates.HIGH) {
            entry {
                shifter.set(Constants.DrivetrainParameters.HIGH_GEAR)
            }
        }

        state(ShifterStates.LOW) {
            entry {
                shifter.set(Constants.DrivetrainParameters.LOW_GEAR)
            }
        }

        default {
            entry {
                shifter.set(false)
            }
        }
    }

    on (Events.TELEOP_ENABLED) {
        driveMachine.setState(DriveStates.DRIVE_REDUCED)
        shiftMachine.setState(ShifterStates.LOW)
    }
}
