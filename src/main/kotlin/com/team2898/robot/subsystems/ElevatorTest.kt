package com.team2898.robot.subsystems


import com.revrobotics.CANSparkBase
import com.revrobotics.CANSparkLowLevel
import com.revrobotics.CANSparkMax
import com.team2898.robot.RobotMap.ElevatorID
import com.team2898.robot.subsystems.ElevatorTest.ElevatorMotor
import edu.wpi.first.math.controller.ElevatorFeedforward
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.units.Measure
import edu.wpi.first.units.Units
import edu.wpi.first.units.Units.Volt
import edu.wpi.first.units.Voltage
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine

object ElevatorTest : SubsystemBase() {
   val ElevatorMotor = CANSparkMax(ElevatorID, CANSparkLowLevel.MotorType.kBrushless)
    private val ks = 0.0
    private val kv = 0.0
    private val kg = 0.0
    private val ka = 0.0
    private val eff = ElevatorFeedforward(ks, kg, kv, ka)
    private val pid = PIDController(0.0, 0.0, 0.0)
    private var MotorSpeed = 0.0


    init {
        ElevatorMotor.restoreFactoryDefaults()
        ElevatorMotor.setSmartCurrentLimit(40)
        ElevatorMotor.idleMode = CANSparkBase.IdleMode.kBrake
        ElevatorMotor.burnFlash()
    }
    val routine: SysIdRoutine = SysIdRoutine(
        SysIdRoutine.Config(),
        SysIdRoutine.Mechanism(
            { volts: Measure<Voltage> ->
                ElevatorMotor.setVoltage(volts.`in`(Volt))
            },
            { log: SysIdRoutineLog ->
                log.motor("Elevator")
                    .voltage(Units.Volts.of(ElevatorMotor.appliedOutput * ElevatorMotor.busVoltage))
                    .angularPosition(Units.Rotations.of(ElevatorMotor.encoder.position))
                    .current(Units.Amps.of(ElevatorMotor.outputCurrent))
                    .linearPosition(Units.Centimeters.of(ElevatorMotor.encoder.position))
            },
            this
        )
    )
    fun QuasistaticSysIDroutine(direction: SysIdRoutine.Direction): Command {
        return routine.quasistatic(direction)
    }

    fun DynamicSysIDroutine(direction: SysIdRoutine.Direction): Command {
        return routine.dynamic(direction)
    }
    fun GetSysIDVals(): Command{
        return SequentialCommandGroup(
            QuasistaticSysIDroutine(SysIdRoutine.Direction.kForward),
            WaitCommand(5.0),
            QuasistaticSysIDroutine(SysIdRoutine.Direction.kReverse),
            WaitCommand(5.0),
            DynamicSysIDroutine(SysIdRoutine.Direction.kForward),
            WaitCommand(5.0),
            DynamicSysIDroutine(SysIdRoutine.Direction.kReverse)
        )
    }
    fun Cardio(speed:Double){
        MotorSpeed = eff.calculate(speed) + pid.calculate(speed)
    }
}