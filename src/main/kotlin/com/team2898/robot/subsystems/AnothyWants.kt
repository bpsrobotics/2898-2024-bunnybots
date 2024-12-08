package com.team2898.robot.subsystems

import com.revrobotics.CANSparkBase
import com.revrobotics.CANSparkLowLevel
import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase

object AnthonyWants : SubsystemBase() {
    private val rollerUp = CANSparkMax(RollerUp, CANSparkLowLevel.MotorType.kBrushed)
    private val rollerDown = CANSparkMax(RollerDown, CANSparkLowLevel.MotorType.kBrushed)

    // Start speed at 0.0
    private var upSpeed = 0.0
    private var downSpeed = 0.0

    // Store motors in an array for easier initialization
    private val motors = arrayOf(rollerUp, rollerDown)

    init {
        // Initialize motors
        for (motor in motors) {
            motor.restoreFactoryDefaults()
            motor.setSmartCurrentLimit(30)
            motor.idleMode = CANSparkBase.IdleMode.kBrake
            motor.inverted = false // Adjust based on motor direction
            motor.burnFlash()
        }
    }

    override fun periodic() {
        // Continuously set the motor speeds
        rollerUp.set(upSpeed)
        rollerDown.set(downSpeed)
    }

    // Control the rollerUp motor
    fun moveRollerUp(speed: Double) {
        upSpeed = speed
    }

    // Control the rollerDown motor
    fun moveRollerDown(speed: Double) {
        downSpeed = speed
    }

    // Stop both motors
    fun stopRollers() {
        upSpeed = 0.0
        downSpeed = 0.0
    }
}
