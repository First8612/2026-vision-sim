package frc.robot.vision;

import java.util.Optional;
import org.photonvision.simulation.VisionSystemSim;
import edu.wpi.first.math.geometry.Pose2d;


public interface Camera {
    String getName();
    void addToSim(VisionSystemSim visionSim);
    Optional<Pose2d> getEstimatedPose();
}