package org.team401.robot2016

import org.snakeskin.annotation.Setup
import org.snakeskin.registry.Controllers
import org.snakeskin.registry.Subsystems
import org.team401.robot2016.subsystems.ArmSubsystem
import org.team401.robot2016.subsystems.DrivetrainSubsystem
import org.team401.robot2016.subsystems.ShooterSubsystem

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

@Setup fun setup() {
    Subsystems.add(DrivetrainSubsystem, ArmSubsystem, ShooterSubsystem)
    Controllers.add(Gamepad)
}