package com.team2898.robot.commands

import com.team2898.robot.subsystems.Intake
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Command

class IntakeNote : Command() {
    val time = Timer()

    init {
        addRequirements(Intake)


 }

    override fun initialize() {
        time.reset()
        time.start()
        Intake.intake(0.65)
    }

    override fun isFinished(): Boolean {
        return Intake.hasNote
    }

    override fun end(interrupted: Boolean) {
        Intake.intake(-0.1)
    }
}