package frc.robot.vision;

import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;

public class CameraPV implements Camera {
        private Transform3d robotToCamera;
        private PhotonCamera camera;

        public CameraPV(String name, Transform3d robotToCamera) {
            this.camera = new PhotonCamera(name);
            this.robotToCamera = robotToCamera;
        }

        @Override
        public String getName() {
            return this.camera.getName();
        }

        public void addToSim(VisionSystemSim visionSim) {
            PhotonCameraSim cameraSim = new PhotonCameraSim(camera);
            visionSim.addCamera(cameraSim, robotToCamera);
            cameraSim.enableDrawWireframe(true);
        }

        @Override
        public Optional<Pose2d> getEstimatedPose() {
        return Optional.empty();
        }
    }

