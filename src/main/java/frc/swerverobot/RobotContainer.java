package frc.swerverobot;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.swerverobot.commands.drive.*;
import frc.swerverobot.commands.climb.*;
import frc.swerverobot.commands.intake.*;
import frc.swerverobot.commands.shooter.*;
import frc.swerverobot.commands.auto.*;
import frc.swerverobot.subsystems.ClimberSubsystem;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem;

//Robot Map
import static frc.swerverobot.RobotMap.*;

import java.util.function.DoubleSupplier;

import org.frcteam2910.common.math.RigidTransform2;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;
import org.frcteam2910.common.robot.input.DPadButton.Direction;
import org.opencv.core.Mat;

public class RobotContainer {
    private final Controller driveController = RobotMap.Driver_controller;
    private final Controller manipulatorController = RobotMap.Shooter_controller;
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final ClimberSubsystem climb = new ClimberSubsystem();

    private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    private boolean pointRotation = false;
    private DriveCommand drivecommand;

    private final UpdateManager updateManager = new UpdateManager(
        drivetrain
    );

    
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
    }

    private void initRobot() {
	intake.init();
	climb.init();
    }

    private void configureButtonBindings() {
        //[Drive Subsystem]
        driveController.getBackButton().whenPressed(
                //[Drive Subsystem]  Reset Gyro (Update if this is wrong I dont know)
 //               () -> drivetrain.resetGyroAngle(Rotation2.ZERO)
                () -> drivecommand.resetPose()
        );
//        controller.getBButton().whenPressed(
//                //[Drive Subsystem]  Drives in Square (Update if this is wrong I dont know)
//                new SquareCommand(drivetrain, 0.4, 1)
//        );
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

        driveController.getRightBumperButton().whenPressed(
                new FollowBallCommand(drivetrain,
                        () -> driveController.getRightTriggerAxis().get(true),
                        () -> driveController.getRightBumperButton().get()
                )
        );

//        controller.getRightTriggerAxis().getButton(0.1).whenPressed(
//                new FollowBallCommand(drivetrain,
//                () -> controller.getRightTriggerAxis().get(true)
//                )
//        );


/*        controller.getStartButton().whenPressed(
                new PathCommand(drivetrain, 1.0, 0.0, 0.0)
        );
*/

/*        controller.getStartButton().whenPressed(
                //[Drive Subsystem]  Drives in Square (Update if this is wrong I dont know)
                () -> drivetrain.resetPose(new RigidTransform2(new Vector2(0, 0), new Rotation2(0, 0, false)))
        );
*/

 
        //[Climber Subsystem]
 
        // manipulatorController.getYButton().whenPressed(
        //         //[Climber Subsystem] Climb
        //         new WinchLockCommand(climb)
        // );

        // manipulatorController.getBButton().whenPressed(
        //         new PullandGrabCommand(climb)
        // );

        // manipulatorController.getAButton().whenPressed(
        //         new GetToNextRungCommand(climb)
        // );

        // manipulatorController.getXButton().whenHeld(
        //         new SpoolCommand(climb)
        // );

        // manipulatorController.getLeftBumperButton().whenPressed(
        //         new StaticHookCommand(climb)
        // );
        

        shooter.ControllerButtonInit();//Initalizes all the controller buttons for the Shooter Subsystem
    }
}
