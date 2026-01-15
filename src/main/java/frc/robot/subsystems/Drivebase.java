package frc.robot.subsystems;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.photonvision.PhotonPoseEstimator;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class Drivebase extends SubsystemBase {
    public static final double kMaxSpeed = 4.0; // 4 meters per second
    public static final double kMaxAngularSpeed = 1.5 * Math.PI; // 1.5 rotations per second
    private final SwerveDrive swerveDrive;

    public Drivebase(Pose2d initialPose) {
        super();

        File directory = new File(Filesystem.getDeployDirectory(), "swerve");

        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
        try {
            swerveDrive = new SwerveParser(directory)
                .createSwerveDrive(kMaxSpeed, initialPose);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drive(double xSpeed, double ySpeed, double rot) {
        swerveDrive.drive(
                new Translation2d(
                        xSpeed * swerveDrive.getMaximumChassisVelocity(),
                        ySpeed * swerveDrive.getMaximumChassisVelocity()),
                rot * swerveDrive.getMaximumChassisAngularVelocity(),
                true,
                false);
    }

    public void driveFieldRelative(ChassisSpeeds chassisSpeed) {
        swerveDrive.driveFieldOriented(chassisSpeed);
    }

    public void driveRobotRelative(ChassisSpeeds chassisSpeed) {
        swerveDrive.drive(chassisSpeed);
    }

    public Optional<Pose2d> getSimulationDriveTrainPose()
    {
        return swerveDrive.getSimulationDriveTrainPose();
    }

    public Field2d getField()
    {
        return swerveDrive.field;
    }

    public SwerveDrivePoseEstimator getPoseEstimator()
    {
        return swerveDrive.swerveDrivePoseEstimator;
    }
}
