package frc.swerverobot;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.swerverobot.commands.*;
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
    private final Controller controller = Driver_controller;
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final ClimberSubsystem climber = new ClimberSubsystem(CLIMBER_MOTOR, HOOKa, HOOKb, WINCH_SOLa, WINCH_SOLb, CLIMBER_SWITCH);

    private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    private boolean pointRotation = false;
    private DriveCommand drivecommand;

    private final UpdateManager updateManager = new UpdateManager(
        drivetrain
    );

    
    public RobotContainer() {
        this.drivecommand = new DriveCommand(
                drivetrain,
                () -> controller.getLeftXAxis().get(true),
                () -> -controller.getLeftYAxis().get(true),
                () -> controller.getRightXAxis().get(true),
                () -> controller.getLeftTriggerAxis().get(true),
                () -> controller.getRightTriggerAxis().get(true)
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
    }

    private void configureButtonBindings() {
        //[Drive Subsystem]
        controller.getBackButton().whenPressed(
                //[Drive Subsystem]  Reset Gyro (Update if this is wrong I dont know)
 //               () -> drivetrain.resetGyroAngle(Rotation2.ZERO)
                () -> drivecommand.resetPose()
        );
//        controller.getBButton().whenPressed(
//                //[Drive Subsystem]  Drives in Square (Update if this is wrong I dont know)
//                new SquareCommand(drivetrain, 0.4, 1)
//        );
        controller.getYButton().whenPressed(
                new GoToAngleCommand(
                        drivetrain,
                        () -> 0.0,
                        () -> 0.0,
                        () -> 0.0,
                        () -> 1.0
                        )
        );

        controller.getBButton().whenPressed(
                new GoToAngleCommand(
                        drivetrain,
                        () -> 0.0,
                        () -> 0.0,
                        () -> -1.0,
                        () -> 0.0
                        )
        );

        controller.getXButton().whenPressed(
                new GoToAngleCommand(
                        drivetrain,
                        () -> 0.0,
                        () -> 0.0,
                        () -> 1.0,
                        () -> 0.0
                        )
        );

        controller.getAButton().whenPressed(
                new GoToAngleCommand(
                        drivetrain,
                        () -> 0.0, 
                        () -> 0.0,
                        () -> 0.0,
                        () -> -1.0
                        )
        );

        controller.getRightBumperButton().whenPressed(
                new FollowBallCommand(drivetrain,
                        () -> controller.getRightTriggerAxis().get(true),
                        () -> controller.getRightBumperButton().get()
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
        /*  
        controller.getYButton().whenPressed(
                //[Climber Subsystem] Climb
                (Command) new Climb1Command(climber)
        );
        */

        shooter.ControllerButtonInit();//Initalizes all the controller buttons for the Shooter Subsystem
    }
}
