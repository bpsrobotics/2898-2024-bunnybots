package com.team2898.robot.commands.swerve

import com.team2898.engine.utils.odometry.Vision
import com.team2898.robot.subsystems.Drivetrain
import com.team2898.robot.Constants.VisionConstants
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Transform3d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Command
import org.photonvision.PhotonUtils


class TrackToNearestTote: Command() {
    private val runtime = Timer()
    private val vision = Vision("Camera_Module_v1")
    private val swerve: Drivetrain
    private var robotPose = Pose2d()
    private val validTags = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var trackedID = 0
    private var translation = Translation2d()
    private var finished = false

    private fun getClosestTag(): Int {
        val results = vision.getAllTrackedTargets()
        val trackedDistances = mutableListOf<Double>()
        val trackedTags = mutableListOf<Int>()
        for (i in results){
            if (i.fiducialId in validTags){
                trackedDistances.add(i.bestCameraToTarget.x)
                trackedTags.add(i.fiducialId)
            }
        }
        var minIndex = 0
        for (i in 1 until trackedDistances.size) {
            if (trackedDistances[i] < trackedDistances[minIndex]) {
                minIndex = i
            }
        }
        return trackedTags[minIndex]
    }


    init {
        this.swerve = Drivetrain
    }

    override fun initialize() {
        trackedID = getClosestTag()
        robotPose = Drivetrain.getPose()
        runtime.reset()
        runtime.start()

    }

    override fun execute() {
        val results = vision.getCameraData(trackedID)


        translation = PhotonUtils.estimateCameraToTargetTranslation(
            results.x, Rotation2d.fromDegrees(-vision.getCameraYaw(trackedID))

        )
        swerve.drive(Translation2d(translation.x - VisionConstants.CAMERA_TO_ROBOT_OFFSET_X, translation.y - VisionConstants.CAMERA_TO_ROBOT_OFFSET_Y), translation.angle.degrees, true)
        if (results.x < 0.05) {
            finished = true
        }
        if (runtime.hasElapsed(2.0)){
            finished = true
        }


    }
    override fun isFinished(): Boolean {
        return finished
    }

}