package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Telemetry extends SubsystemBase {
    private Field2d field;
    private final StructPublisher<Pose2d> robotPosePublisher = NetworkTableInstance.getDefault()
            .getStructTopic("SmartDashboard/Field/Robot (AS)", Pose2d.struct).publish();

    public Telemetry(Field2d field) {
        super();
        this.field = field;
    }

    @Override
    public void periodic() {
        super.periodic();
        
        // republish in advantagescope format
        robotPosePublisher.set(field.getRobotPose());
    }
}
