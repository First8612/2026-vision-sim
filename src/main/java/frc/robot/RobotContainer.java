// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.DriveFieldCentricCommand;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Telemetry;
import frc.robot.vision.Vision;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final CommandXboxController driverController = new CommandXboxController(0);
  private final Drivebase drivebase = new Drivebase(
    new Pose2d(1, 1, new Rotation2d())
  );
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Field2d field = drivebase.getField();
  private final Vision vision = new Vision(drivebase);
  private final Telemetry telemetry = new Telemetry(field);

  // commands
  private final DriveFieldCentricCommand teleopDriveCommand = new DriveFieldCentricCommand(drivebase, driverController);

  public RobotContainer() {
    configureBindings();

    SmartDashboard.putData(drivebase);

    // commenting out for now. PDP isn't working right.
    // SmartDashboard.putData(new PowerDistribution(17, ModuleType.kRev));
  }

  private void configureBindings() {
    drivebase.setDefaultCommand(teleopDriveCommand);
  }

  public Command getAutonomousCommand() {
    return Autos.exampleAuto(m_exampleSubsystem);
  }

  public void robotPeriodic() {
    vision.periodic();
    telemetry.periodic();
  }
}
