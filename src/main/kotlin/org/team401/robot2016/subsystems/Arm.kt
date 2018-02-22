package org.team401.robot2016.subsystems

import edu.wpi.first.wpilibj.Jaguar
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

const val ARM_MACHINE = "arm"
object ArmStates {
    const val MOVE = "move"
    const val LOCK = "idle"
}

val ArmSubsystem: Subsystem = buildSubsystem {
    val motor = Jaguar(Constants.MotorControllers.ARM)

    val armMachine = stateMachine(ARM_MACHINE) {
        state (ArmStates.MOVE) {
            action {
                motor.set(Gamepad.readAxis { RIGHT_Y } * Constants.ArmParameters.ARM_REDUCTION)
            }
        }

        state (ArmStates.LOCK) {
            action {
                motor.set(0.0)
            }
        }

        default {
            entry {
                motor.set(0.0)
            }
        }
    }

    on (Events.ENABLED) {
        armMachine.setState(ArmStates.LOCK)
    }
}