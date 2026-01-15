package frc.robot.vision;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;

import org.photonvision.simulation.VisionSystemSim;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.subsystems.Drivebase;
import swervelib.telemetry.SwerveDriveTelemetry;

public class Vision extends SubsystemBase {
    private VisionSystemSim visionSim;
    private Camera camera1;
    private Camera camera2;
    private Camera camera3;
    private Drivebase drivebase;

    private final StructPublisher<Pose3d> robotPoseSimPublisher = NetworkTableInstance.getDefault()
            .getStructTopic("/SmartDashboard/VisionSystemSim-Main/Sim Field/Robot (AS)", Pose3d.struct).publish();

    public Vision(Drivebase drivebase) {
        this.drivebase = drivebase;

        // Cam mounted facing forward, half a meter forward of center, half a meter up
        // from center.
        camera1 = new CameraPV("camera1",
                new Transform3d(new Translation3d(0.5, 0.2, 0.5),
                        new Rotation3d(0, 0, 0)));

        // rear left corner
        camera2 = new CameraPV("camera2",
                new Transform3d(new Translation3d(-0.5, 0.2, -0.5),
                        new Rotation3d(0, 0, Radians.convertFrom(135, Degrees))));

        // rear right corner
        camera3 = new CameraPV("camera3",
                new Transform3d(new Translation3d(0.5, 0.2, -0.5),
                        new Rotation3d(-135, 0, 0)));

        if (Robot.isSimulation()) {
            this.setupSimulation();
        }
    }

    private void setupSimulation() {
        visionSim = new VisionSystemSim("Main");
        visionSim.addAprilTags(
                AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField));

        camera1.addToSim(visionSim);
        // raw stream simulation: http://localhost:1181/
        // processed stream simulation: http://localhost:1182/

        camera2.addToSim(visionSim);
        // raw stream simulation: http://localhost:1183/
        // processed stream simulation: http://localhost:1184/

        camera3.addToSim(visionSim);
        // raw stream simulation: http://localhost:1185/
        // processed stream simulation: http://localhost:1186/

    }

    @Override
    public void periodic() {
        if (SwerveDriveTelemetry.isSimulation && drivebase.getSimulationDriveTrainPose().isPresent()) {
            visionSim.update(drivebase.getSimulationDriveTrainPose().get());

            // republish in advantagescope format
            robotPoseSimPublisher.set(visionSim.getRobotPose());
        }
    }
}
