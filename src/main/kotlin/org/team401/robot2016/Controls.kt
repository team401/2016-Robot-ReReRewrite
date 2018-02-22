package org.team401.robot2016

import org.snakeskin.dsl.HumanControls
import org.snakeskin.dsl.machine
import org.team401.robot2016.subsystems.*

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

val Gamepad = HumanControls.f310(0) {
    invertAxis(Axes.RIGHT_X)
    whenButton(Buttons.START) {
        pressed {
            DrivetrainSubsystem.machine(DRIVE_MACHINE).setState(DriveStates.DRIVE)
            ShooterSubsystem.machine(SHOOTER_MACHINE).setState(ShooterStates.SHOOT)
        }
    }

    whenButton(Buttons.BACK) {
        pressed {
            DrivetrainSubsystem.machine(DRIVE_MACHINE).setState(DriveStates.DRIVE_REDUCED)
            ShooterSubsystem.machine(SHOOTER_MACHINE).setState(ShooterStates.SHOOT_REDUCED)
        }
    }

    whenButton(Buttons.B) {
        pressed {
            ShooterSubsystem.machine(KICKER_MACHINE).setState(KickerStates.KICK)
        }
        released {
            ShooterSubsystem.machine(KICKER_MACHINE).setState(KickerStates.STOW)
        }

    }

    whenButton(Buttons.LEFT_STICK) {
        pressed {
            ShooterSubsystem.machine(SHOOTER_MACHINE).setState(ShooterStates.INTAKE)
        }

        released {
            ShooterSubsystem.machine(SHOOTER_MACHINE).back()
        }
    }

    whenButton(Buttons.RIGHT_STICK) {
        pressed {
            ArmSubsystem.machine(ARM_MACHINE).setState(ArmStates.MOVE)
        }

        released {
            ArmSubsystem.machine(ARM_MACHINE).setState(ArmStates.LOCK)
        }
    }
}