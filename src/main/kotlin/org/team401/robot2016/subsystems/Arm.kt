package org.team401.robot2016.subsystems

import edu.wpi.first.wpilibj.Counter
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
    val topSensor = Counter(1)
    val bottomSensor = Counter(0)

    val armMachine = stateMachine(ARM_MACHINE) {
        state (ArmStates.MOVE) {
            var desiredDrive: Double
            action {
                println("top count: " + topSensor.get())
                desiredDrive = Gamepad.readAxis { RIGHT_Y }
                if (topSensor.get() > 0 && desiredDrive <= 0){
                    desiredDrive = 0.0
                } else if (bottomSensor.get() > 0 && desiredDrive >= 0){
                    desiredDrive = 0.0
                }
                else {
                    topSensor.reset()
                    bottomSensor.reset()
                }

                motor.set(desiredDrive * Constants.ArmParameters.ARM_REDUCTION)
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