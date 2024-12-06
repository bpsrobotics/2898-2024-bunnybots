package com.team2898.robot.subsystems

import com.revrobotics.CANSparkBase
import com.revrobotics.CANSparkLowLevel
import com.revrobotics.CANSparkMax
import com.team2898.robot.RobotMap

import com.team2898.robot.RobotMap.RollerBot
import com.team2898.robot.RobotMap.RollerLeft
import com.team2898.robot.RobotMap.RollerRight
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.DoubleSolenoid.Value
import edu.wpi.first.wpilibj.DutyCycleEncoder
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj2.command.SubsystemBase

object ToteManipulator : SubsystemBase() {
    private val rollerRight = CANSparkMax(RollerRight, CANSparkLowLevel.MotorType.kBrushed)
    private val rollerLeft = CANSparkMax(RollerLeft, CANSparkLowLevel.MotorType.kBrushed)
    private val rollerBot = CANSparkMax(RollerBot, CANSparkLowLevel.MotorType.kBrushless)
    private val rightFinger = DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1)
    private val leftFinger = DoubleSolenoid(PneumaticsModuleType.REVPH, 2, 3)



    var grabState = Value.kReverse
    var speed = 0.0

    val motors = arrayOf(rollerRight, rollerLeft, rollerBot)

    init {
        for (motor in motors) {
            motor.restoreFactoryDefaults()
            motor.setSmartCurrentLimit(30)
            motor.idleMode = CANSparkBase.IdleMode.kBrake
            motor.inverted = true
            motor.burnFlash()
        }

        rollerLeft.follow(rollerBot, true)
        rollerRight.follow(rollerBot)



    }

    override fun periodic() {
        rightFinger.set(grabState)
        leftFinger.set(grabState)
        rollerBot.setVoltage(speed)

    }


    fun setGrabbers() {
        rightFinger.toggle()
        leftFinger.toggle()
    }

    fun setMotors(motorSpeed: Double) {
        speed = motorSpeed
    }


}