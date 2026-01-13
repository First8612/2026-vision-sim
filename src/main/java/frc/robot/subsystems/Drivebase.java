package frc.robot.subsystems;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

public class Drivebase extends SubsystemBase {
    public static final double kMaxSpeed = 4.0; // 4 meters per second
    public static final double kMaxAngularSpeed = 1.5 * Math.PI; // 1.5 rotations per second

    private final SwerveDrive swerveDrive;

    public Drivebase() {
        super();

        File directory = new File(Filesystem.getDeployDirectory(), "swerve");

        try {
            swerveDrive = new SwerveParser(directory).createSwerveDrive(kMaxSpeed);
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
}
