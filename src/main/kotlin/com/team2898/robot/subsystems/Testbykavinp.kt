package com.team2898.robot.subsystems


import com.revrobotics.CANSparkBase
import com.revrobotics.CANSparkLowLevel
import com.revrobotics.CANSparkMax


import edu.wpi.first.wpilibj2.command.SubsystemBase

//super sigma practice
object Testsystem:SubsystemBase() {
    private var Frontrightswerve = CANSparkMax(Frontrightswerve, CANSparkLowLevel.MotorType.kBrushless)
    private var frontleftswerve = CANSparkMax(frontleftswerve, CANSparkLowLevel.MotorType.kBrushless)

    //sigma speeds for swerve, hypothetical only
    private var forwardspeed = 0.0
    private var backwardspeed = 0.0
    private var motor = arrayOf(Frontrightswerve, frontleftswerve)
    init{
        for (motor in motor) {
            motor.restoreFactoryDefaults()
            motor.setSmartCurrentLimit(20)
            motor.idleMode = CANSparkBase.IdleMode.kBrake
            motor.inverted = false
            motor.burnFlash()
        }
    }

    override fun periodic() {
      frontleftswerve.set(forwardspeed)
      Frontrightswerve.set(forwardspeed)
    }
    fun move(MOVE: Double) {
        var forwardspeed = MOVE
        Thread.sleep(1000)
    }
    fun iwannagoback(REVERSE: Double) {
        var forwardspeed = 0.0
        val backwardspeed = REVERSE
        frontleftswerve.set(backwardspeed)
        Frontrightswerve.set(backwardspeed)
        Thread.sleep(1000)
    }



}
