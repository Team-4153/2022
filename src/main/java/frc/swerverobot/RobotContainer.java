package frc.swerverobot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler; 
import frc.swerverobot.commands.drive.*;
import frc.swerverobot.commands.climb.*;
import frc.swerverobot.commands.intake.*;
import frc.swerverobot.commands.LED.*;
import frc.swerverobot.commands.shooter.*;
import frc.swerverobot.commands.auto.*;
import frc.swerverobot.commands.auto.competition.FourBallAuto;
import frc.swerverobot.commands.auto.competition.HighThreeBall;
import frc.swerverobot.commands.auto.competition.HighTwoBall;
import frc.swerverobot.commands.auto.competition.LowOneBall;
import frc.swerverobot.commands.auto.competition.LowThreeBall;
import frc.swerverobot.commands.auto.competition.LowTwoBall;
import frc.swerverobot.commands.auto.competition.OneBallAuto;
import frc.swerverobot.commands.auto.competition.ThreeBallAuto;
import frc.swerverobot.commands.auto.competition.TwoBallAuto;
import frc.swerverobot.commands.auto.rando.DriveOffTarmac;
import frc.swerverobot.commands.auto.rando.HardIntake;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.ClimberSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.LEDSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

//Robot Map
import static frc.swerverobot.RobotMap.*;

import java.lang.Thread.State;

import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;

@Deprecated
@SuppressWarnings("unused")

public class RobotContainer {
        private final Controller driveController = RobotMap.Driver_controller;

        private final IntakeSubsystem intake = new IntakeSubsystem();
        private final ShooterSubsystem2 shooter = new ShooterSubsystem2();
        private final ClimberSubsystem climb = new ClimberSubsystem();
        private final LEDSubsystem LED = new LEDSubsystem();
        private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();

        private int allianceColor = 0;

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
                /**     Point rotation control method*/
                // CommandScheduler.getInstance().setDefaultCommand(drivetrain, new GoToAngleCommand(
                //         drivetrain,
                //         () -> driveController.getLeftXAxis().get(true),
                //         () -> -driveController.getLeftYAxis().get(true),
                //         () -> -driveController.getRightXAxis().get(true),
                //         () -> driveController.getRightYAxis().get(true)
                // ));
                
                updateManager.startLoop(5.0e-3);
                initRobot();
                configureButtonBindings();
                addAutoChoicesToGui();
        }

        private void initRobot() {
                NetworkTableInstance inst = NetworkTableInstance.getDefault();
                NetworkTable table = inst.getTable("FMSInfo");
                NetworkTableEntry isRedAlliance = table.getEntry("IsRedAlliance");

                if(isRedAlliance.getBoolean(false)) {
                        allianceColor = 1; // if red then color is 1
                } else {
                        allianceColor = 2; // if blue then color is 2
                }
                SmartDashboard.putNumber("IntakeBall/BallColor", allianceColor);


                intake.init();
                climb.init();
                LED.init();
                shooter.disableMotorSafeties();
        }

        public Command getAutonomousCommand() {
                String choice = SmartDashboard.getString("Auto Selector", null);

                switch(choice) {
                        case "LOW_ONE":
                                return new LowOneBall(drivetrain, shooter, intake);
                        case "LOW_TWO":
                                return new LowTwoBall(drivetrain, shooter, intake);
                        case "LOW_THREE":
                                return new LowThreeBall(drivetrain, shooter, intake);
                        case "HIGH_TWO":
                                return new HighTwoBall(drivetrain, shooter, intake);
                        case "HIGH_THREE":
                                return new HighThreeBall(drivetrain, shooter, intake);
                        case "THREE_BALL":
                                return new ThreeBallAuto(drivetrain, shooter, intake);
                        // case "FOUR_BALL":
                        //         return new FourBallAuto(drivetrain, shooter, intake);
                        default:
                                return new LowOneBall(drivetrain, shooter, intake);
                }

        }

        public Command getTestCommand() {
                return new TestCommand(drivetrain, shooter, intake, climb);
        }

        private void configureButtonBindings() {
                //[Drive Subsystem]
                ResetGyro.whenPressed(
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
                // driveController.getStartButton().whenPressed(
                //         new LockCommand(drivetrain, States.TOGGLE)
                // );
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
                ClimbOut.whenPressed(
                        //Moves Mobile Climber Out
                        new ArmPositionCommand(climb, States.LOCKED)
                );
                ClimbIn.whenPressed(
                        //Moves Mobile Climber In
                        new ArmPositionCommand(climb, States.UNLOCKED)
                );
                StatHookClose.whenPressed(
                        //Lock/Unlock the static hook
                        new StaticHookCommand(climb, States.UNLOCKED)
                );
                StatHookOpen.whenPressed(
                        //Lock/Unlock the static hook
                        new StaticHookCommand(climb, States.LOCKED)
                );
                Unspool.whenHeld(
                        //Moves robot down using mobile climber
                        new UnspoolCommand(climb)
                );
                Spool.whenHeld(
                        //Moves robot up using mobile climber
                        new SpoolCommand(climb)
                );
                // WinchLock.whenPressed(
                //         new WinchLockCommand(climb, States.LOCKED)
                // );
                // WinchUnlock.whenPressed(
                //         new WinchLockCommand(climb, States.UNLOCKED)
                // );
/*
-0.65, 0.825, -1
Miss - 7
Hit  - 13

*/
                //        [Shooter Subsystem]
                DropBallBTN.whenPressed(
                        //Drops the first ball in storage
//                        new DropBall(shooter)
                        new ManualShoot(shooter, -0.72, 0.72, -0.4) // -0.7625, 0.7625, -0.4
                );
                ManualShoot.whenPressed(
                        //Gets into low goal from 77 visual distance (77 is as close as we can get and still have a distance reading)
                        new ManualShoot(shooter, -0.4, 0.5, -1)
                );
                AimShootHigh.getButton(0.1).whenPressed(
                        //Autoaim to the high goal and then shoot
                        //Last boolean determines which dataset to use, true = high, false = low.
                        new AutoAim(shooter, drivetrain, true)
                );
                AimShootLow.getButton(0.1).whenPressed(
                        //Autoaim to the high goal and then shoot
                        //Last boolean determines which dataset to use, true = high, false = low.
                        new AutoAim(shooter, drivetrain, false)
                        // new ShootCommand(shooter, -0.7, 1.0, -0.4)
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
                String[] autoChoicesStrings = new String[enumValues.length];

                for (int i = 0; i < enumValues.length; i++) {
                        autoChoicesStrings[i] = enumValues[i].toString();
                }

                SmartDashboard.putStringArray("Auto List", autoChoicesStrings);
        }

        public void checkColor() {
                NetworkTableInstance inst = NetworkTableInstance.getDefault();
                NetworkTable table = inst.getTable("FMSInfo");
                NetworkTableEntry isRedAlliance = table.getEntry("IsRedAlliance");
        
                if(isRedAlliance.getBoolean(false)) {
                        allianceColor = 1; // if red then color is 1
                } else {
                        allianceColor = 2; // if blue then color is 2
                }
                SmartDashboard.putNumber("IntakeBall/BallColor", allianceColor);
        }
        
}
