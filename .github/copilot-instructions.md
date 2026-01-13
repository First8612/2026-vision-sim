## Quick context for AI code helpers

This is an FRC (WPILib) Java robot project using the GradleRIO plugin (Java 17).
Key layout:
- build.gradle — GradleRIO config, creates a fat jar and sets the robot main class
- src/main/java/frc/robot — main package; contains Robot, Main, RobotContainer, Constants
- src/main/java/frc/robot/subsystems — Subsystem classes (example: `ExampleSubsystem`, `Drivebase`)
- src/main/java/frc/robot/commands — Command classes and `Autos` factory
- vendordeps/ — vendor-provided libraries that are packaged into the jar
- src/main/deploy — static files copied to `/home/lvuser/deploy` on the RoboRIO

Architecture and patterns to follow
- Command-based pattern (WPILib): subsystem classes extend `SubsystemBase`; commands use
  `addRequirements(...)` and are created via factories (see `ExampleSubsystem.exampleMethodCommand`).
- `RobotContainer` is the canonical place for subsystems, controllers and trigger bindings.  Look
  at `RobotContainer.configureBindings()` for examples of `Trigger` usage and `CommandXboxController`.
- Autonomous flows are composed in `commands/Autos.java` via `Commands.sequence(...)` and
  returned by `RobotContainer.getAutonomousCommand()` for `Robot.autonomousInit()` to schedule.
- Keep robot-wide literals in `Constants.java` (static-only). Do not add behavior here.
- `Robot.robotPeriodic()` must call `CommandScheduler.getInstance().run()` for the command framework to work.

Build / run / simulation notes
- Uses the Gradle wrapper in the repo. Common tasks you will use:
  - `./gradlew build` — compile + assemble
  - `./gradlew test` — run unit tests
  - `./gradlew deploy` — deploy jar to RoboRIO (GradleRIO provides deploy tasks)
  If unsure, run `./gradlew tasks` to list available tasks in your environment.
- build.gradle config:
  - Java 17 target
  - `wpi.sim.addGui().defaultEnabled = true` and `wpi.sim.addDriverstation()` — simulation GUI & driver station are enabled
  - The jar task packages vendordeps and `src` samples into a fat jar and sets the manifest `ROBOT_MAIN_CLASS`.

Conventions and gotchas specific to this repo
- Main entry point is `frc.robot.Main` — if you rename the Robot class or package, also update the manifest/`ROBOT_MAIN_CLASS` in `build.gradle`.
- The `deploy` artifact maps `src/main/deploy` to `/home/lvuser/deploy`. Use that for non-Java resources (config or html).
- Vendor dependencies live under `vendordeps/` and are deliberately included in the jar—don't remove those files when adding vendor libs.
- `Drivebase` currently is an empty `SubsystemBase` (placeholder). Look at `ExampleSubsystem` for command-factory patterns and periodic/simulation hooks.

What to do when changing robot lifecycle or scheduling
- If you add periodic work that must run every scheduler cycle, put it in a subsystem `periodic()` or in a scheduled command.
- Never remove `CommandScheduler.getInstance().run()` from `Robot.robotPeriodic()`; otherwise triggers/commands stop working.


