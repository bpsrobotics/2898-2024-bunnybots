package com.team2898.robot.subsystems

import com.revrobotics.CANSparkBase
import com.revrobotics.CANSparkLowLevel
import com.revrobotics.CANSparkMax
import com.team2898.robot.RobotMap
import com.team2898.robot.RobotMap.PivotLeft
import com.team2898.robot.RobotMap.PivotRight
import com.team2898.robot.RobotMap.RollerBot
import com.team2898.robot.RobotMap.RollerLeft
import com.team2898.robot.RobotMap.RollerRight
import edu.wpi.first.wpilibj.DutyCycleEncoder
import edu.wpi.first.wpilibj2.command.SubsystemBase

object ToteManipulator : SubsystemBase() {
    private val pivotRight = CANSparkMax(PivotRight, CANSparkLowLevel.MotorType.kBrushless)
    private val rollerRight = CANSparkMax(RollerRight, CANSparkLowLevel.MotorType.kBrushed)
    private val pivotLeft = CANSparkMax(PivotLeft, CANSparkLowLevel.MotorType.kBrushless)
    private val rollerLeft = CANSparkMax(RollerLeft, CANSparkLowLevel.MotorType.kBrushed)
    private val rollerBot = CANSparkMax(RollerBot, CANSparkLowLevel.MotorType.kBrushless)

    private val encoderRight = DutyCycleEncoder(RobotMap.encoderRight)
    private val encoderLeft = DutyCycleEncoder(RobotMap.encoderLeft)


    val motors = arrayOf(pivotRight, rollerRight, pivotLeft, rollerLeft, rollerBot)

    init {
        for (motor in motors) {
            motor.restoreFactoryDefaults()
            motor.setSmartCurrentLimit(30)
            motor.idleMode = CANSparkBase.IdleMode.kBrake
            motor.inverted = true
            motor.burnFlash()
        }



    }




}