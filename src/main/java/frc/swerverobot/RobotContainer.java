package frc.swerverobot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler; 
import frc.swerverobot.commands.drive.*;
import frc.swerverobot.commands.climb.*;
import frc.swerverobot.commands.intake.*;
// import frc.swerverobot.commands.LED.*;
import frc.swerverobot.commands.shooter.*;
import frc.swerverobot.commands.auto.*;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.ClimberSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.LEDSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

//Robot Map
import static frc.swerverobot.RobotMap.*;

import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.DPadButton.Direction;

@Deprecated
@SuppressWarnings("unused")

public class RobotContainer {
        private final Controller driveController = RobotMap.Driver_controller;
        private final Controller manipulatorController = RobotMap.Shooter_controller;

        private final IntakeSubsystem intake = new IntakeSubsystem();
        private final ShooterSubsystem2 shooter = new ShooterSubsystem2();
        private final ClimberSubsystem climb = new ClimberSubsystem();
        private final LEDSubsystem LED = new LEDSubsystem();
        //private final LEDSubsystemCommand m_LEDCommand = new LEDSubsystemCommand(LED);
        private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();

        private DriveCommand drivecommand;

        private final UpdateManager updateManager = new UpdateManager(
                drivetrain
        );

        //Multiple Auto Option
        SendableChooser<PossibleAutos> autoChoice = new SendableChooser<PossibleAutos>();

        public RobotContainer() {
                this.drivecommand = new DriveCommand(
                        drivetrain,
                        () -> driveController.getLeftXAxis().get(true),
                        () -> -driveController.getLeftYAxis().get(true),
                        () -> driveController.getRightXAxis().get(true),
                        () -> driveController.getLeftTriggerAxis().get(true),
                        () -> driveController.getRightTriggerAxis().get(true)
                );
                // set the drivetrain's default command to the driver's controller values
                CommandScheduler.getInstance().setDefaultCommand(drivetrain, drivecommand);
                /**     Point rotation control method
                CommandScheduler.getInstance().setDefaultCommand(drivetrain, new GoToAngleCommand(
                        drivetrain,
                        () -> controller.getLeftXAxis().get(true),
                        () -> -controller.getLeftYAxis().get(true),
                        () -> -controller.getRightXAxis().get(true),
                        () -> controller.getRightYAxis().get(true)
                ));
                */
                updateManager.startLoop(5.0e-3);
                initRobot();
                configureButtonBindings();
                // addAutoChoicesToGui();
        }

        private void initRobot() {
                intake.init();
                climb.init();
                LED.init();
                shooter.disableMotorSafeties();
        }

        public Command getAutonomousCommand() {
                PossibleAutos choice = autoChoice.getSelected();
                return new AutonomousCommand(drivetrain, shooter, intake, choice);
        }

        private void configureButtonBindings() {
                //[Drive Subsystem]
                driveController.getBackButton().whenPressed(
                        //[Drive Subsystem]  Reset Gyro (Update if this is wrong I dont know)
                        () -> drivecommand.resetPose()
                );
                driveController.getYButton().whenPressed(
                        new GoToAngleCommand(
                                drivetrain,
                                () -> 0.0,
                                () -> 0.0,
                                () -> 0.0,
                                () -> 1.0
                        )
                );
                driveController.getBButton().whenPressed(
                        new GoToAngleCommand(
                                drivetrain,
                                () -> 0.0,
                                () -> 0.0,
                                () -> -1.0,
                                () -> 0.0
                        )
                );
                driveController.getXButton().whenPressed(
                        new GoToAngleCommand(
                                drivetrain,
                                () -> 0.0,
                                () -> 0.0,
                                () -> 1.0,
                                () -> 0.0
                        )
                );
                driveController.getAButton().whenPressed(
                        new GoToAngleCommand(
                                drivetrain,
                                () -> 0.0, 
                                () -> 0.0,
                                () -> 0.0,
                                () -> -1.0
                        )
                );
                driveController.getStartButton().whenPressed(
                        new LockCommand(drivetrain, States.TOGGLE)
                );
                // driveController.getBButton().whenPressed(
                //         //Follows Ball
                //         new FollowBallCommand(drivetrain,
                //                 () -> driveController.getRightTriggerAxis().get(true),
                //                 () -> driveController.getRightBumperButton().get()
                //         )
                // );
                // driveController.getStartButton().whenPressed(
                //         //Currently broken
                //         new PathCommand(drivetrain, 1.0, 0.0, 0.0)
                // );



                //        [Climber Subsystem]
                // manipulatorController.getDPadButton(Direction.UP).whenPressed(
                //         new WinchLockCommand(climb, States.UNLOCKED)
                // );
                // manipulatorController.getDPadButton(Direction.DOWN).whenPressed(
                //         new WinchLockCommand(climb, States.LOCKED)
                // );
                // manipulatorController.getDPadButton(Direction.LEFT).whenPressed(
                //         new PullandGrabCommand(climb)
                // );
                // manipulatorController.getDPadButton(Direction.RIGHT).whenPressed(
                //         new GetToNextRungCommand(climb)
                // );
                manipulatorController.getDPadButton(Direction.LEFT).whenPressed(
                        //Moves Mobile Climber Out
                        new ArmPositionCommand(climb, States.LOCKED)
                );
                manipulatorController.getDPadButton(Direction.RIGHT).whenPressed(
                        //Moves Mobile Climber In
                        new ArmPositionCommand(climb, States.UNLOCKED)
                );
                manipulatorController.getDPadButton(Direction.UP).whenPressed(
                        //Lock/Unlock the static hook
                        new StaticHookCommand(climb, States.TOGGLE)
                );
                manipulatorController.getLeftBumperButton().whenHeld(
                        //Moves robot down using mobile climber
                        new UnspoolCommand(climb)
                );
                manipulatorController.getRightBumperButton().whenHeld(
                        //Moves robot up using mobile climber
                        new SpoolCommand(climb)
                );



                //        [Shooter Subsystem]
                Ejectball.whenPressed(
                        //Drops the first ball in storage
                        new ShootCommand(shooter, -0.2, 0.2, DEFAULT_FEED_MOTOR_SPEED)
                );
                Shoot.whenPressed(
                        //Go to tape and then shoot into low goal
                        new ShootCommand(shooter, -0.3, 0.3, DEFAULT_FEED_MOTOR_SPEED)
                );
                manipulatorController.getRightTriggerAxis().getButton(0.1).whenPressed(
                        //Autoaim to the high goal and then shoot
                        new AutoAim(shooter, drivetrain)
                );


                
                //        [Intake Subsystem]
                Intake_Extension.whenPressed(
                        //Run the intake (It will autofeed the first ball and stop the motor when both balls are detected)
                        new IntakeSequence(intake, shooter)
                );
                Intake_Retract.whenPressed(
                        //Retracts intake 
                        new IntakeCommand(intake, true)
                );
        }

        //Add options for different start positions
        private void addAutoChoicesToGui() {
                PossibleAutos[] enumValues = PossibleAutos.values();
                for (int i = 0; i < enumValues.length; i++) {
                        autoChoice.addOption(enumValues[i].toString(), enumValues[i]);
                }
                SmartDashboard.putData(autoChoice);
        }
}
