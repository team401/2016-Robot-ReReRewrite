package org.team401.robot2016

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

object Constants {
    object MotorControllers {
        const val DRIVE_LEFT_1 = 0
        const val DRIVE_LEFT_2 = 1
        const val DRIVE_LEFT_3 = 2
        const val DRIVE_RIGHT_1 = 3
        const val DRIVE_RIGHT_2 = 4
        const val DRIVE_RIGHT_3 = 5

        const val SHOOTER_LEFT = 6
        const val SHOOTER_RIGHT = 7
        const val ARM = 8
    }

    object Pneumatics {
        const val DRIVE_SHIFTER = 1
        const val KICKER = 0
    }

    object DrivetrainParameters {
        const val TRANSLATION_REDUCTION = .33
        const val ROTATION_REDUCTION = .5

        const val HIGH_GEAR = false
        const val LOW_GEAR = true
    }

    object ArmParameters {
        const val ARM_REDUCTION = .5
    }

    object ShooterParameters {
        const val INVERT_LEFT = true
        const val INVERT_RIGHT = false

        const val INTAKE_RATE = -.5
        const val SHOOT_REDUCTION = .33
    }
}