package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.commands.LED.SetHighGoalAuto;
import frc.swerverobot.commands.auto.FollowBallCommand;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.drive.GoToAngleCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.intake.IntakeSequence;
import frc.swerverobot.commands.shooter.AutoAim;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.*;
import frc.swerverobot.RobotMap;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

@Deprecated
@SuppressWarnings("unused")

//Raspi Auto 4 Balls High Goal

public class FourBallAuto extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private final LEDSubsystem LED;
    private double angle;

    public FourBallAuto(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, LEDSubsystem LED) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.LED = LED;

        addRequirements(drivetrain, shooter, intake);

        //Didn't add comments to this one because I have no idea what is going on

        addCommands(
            new SetHighGoalAuto(LED),                                                                               //Set the LED's to High goal colors
            new IntakeCommand(intake, false).withTimeout(0.3),
            new WaitCommand(0.5),
            new FollowBallCommand(drivetrain, () -> 0.3),
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.5),
            new IntakeCommand(intake, true),
            new AutoAim(shooter, drivetrain, true),
            new WaitCommand(0.5),

            new GoToAngleCommand(drivetrain, () -> 0.0, () -> 0.0, () -> 1, () -> 0),
            new WaitCommand(0.5),
            new IntakeCommand(intake, false).withTimeout(0.3),
            new WaitCommand(0.5),
            new FollowBallCommand(drivetrain, () -> 0.3),
            new WaitCommand(0.5),
            new IntakeCommand(intake, true)
            // new GoToAngleCommand(drivetrain, () -> 0, () -> 0, () -> 1, () -> 0),
            // new AutoAim(shooter, drivetrain, true)


/*            
            new IntakeCommand(intake, false),
            new WaitCommand(0.5),            
            new DriveCommand(drivetrain, () -> -0.3, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.2),
//            new DriveWithSetRotationCommand(drivetrain, () -> 0.1, () -> 0, 0).withTimeout(1),
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1),
            new IntakeCommand(intake, true),
            new WaitCommand(1),
            
            new ShootCommand(shooter, RobotMap.DEFAULT_TOP_MOTOR_SPEED, RobotMap.DEFAULT_BOTTOM_MOTOR_SPEED, RobotMap.DEFAULT_FEED_MOTOR_SPEED)
*/

        );

        
        // determineAngle(choice);

    }

    // private void determineAngle(String choice) {
    //     switch(choice) {
    //         case "LEFT":
    //             angle = 136.5;
    //         case "CENTER":
    //             angle = 226.5;
    //         case "RIGHT":
    //             angle = 271.5;
    //         default:
    //             angle = 0;
    //     }

    //     drivetrain.setAngle(angle);
    // }

}
