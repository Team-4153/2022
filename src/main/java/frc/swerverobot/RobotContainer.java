package frc.swerverobot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.swerverobot.commands.*;
import frc.swerverobot.subsystems.ClimberSubsystem;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem;



import java.util.function.DoubleSupplier;

import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;

public class RobotContainer {
    private final Controller controller = new XboxController(0);
    private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final ClimberSubsystem climber = new ClimberSubsystem(RobotMap.CLIMBER_MOTOR, RobotMap.CLIMBER_PISTON, RobotMap.CLIMBER_SWITCH);


    private final Vector2 vector0 = new Vector2(1, 0);
    private final Vector2 vector1 = new Vector2(0, 1);

    private final UpdateManager updateManager = new UpdateManager(
            drivetrain
//            shooter
    );

    
    public RobotContainer() {

        // set the drivetrain's default command to the driver's controller values
        CommandScheduler.getInstance().setDefaultCommand(drivetrain, new DriveCommand(
                drivetrain,
                () -> controller.getLeftXAxis().get(true),
                () -> -controller.getLeftYAxis().get(true),
                () -> controller.getRightXAxis().get(true)
        ));


        updateManager.startLoop(5.0e-3);
        initRobot();
        configureButtonBindings();
    }

    private void initRobot() {
        intake.Sol_init();
        intake.Motor_init();
    }

    private void configureButtonBindings() {
        //[Drive Subystem]
        controller.getBackButton().whenPressed(
                //[Drive Subystem]  Reset Gyro (Update if this is wrong I dont know)
                () -> drivetrain.resetGyroAngle(Rotation2.ZERO)
        );
        controller.getAButton().whenPressed(
                //[Drive Subystem]  Drive (Update if this is wrong I dont know)
                () -> drivetrain.drive(vector0, 0, false)
        );
        controller.getBButton().whenPressed(
                //[Drive Subystem]  Drives in Square (Update if this is wrong I dont know)
                new SquareCommand(drivetrain, 0.4, 1)
        );

        //[Climber Subystem]
        controller.getYButton().whenPressed(
                //[Climber Subystem] Climb
                new Climb(climber)
        );

        //[Intake Subystem]
        controller.getLeftBumperButton().whenPressed(
                //[Intake Subystem] Toggle Intake with Solenoid
                () ->  intake.Sol_toggle()
        );
        controller.getRightBumperButton().whileHeld(
                //[Intake Subystem] Start Motor
                () ->  intake.Motor_Start()
        );
        controller.getRightBumperButton().whenReleased(
                //[Intake Subystem] Stop Motor
                () ->  intake.Motor_Stop()
        );

        //[Shooter Subystem]
        controller.getAButton().whenPressed(
                //[Shooter Subystem] Low Goal Auto Aim
                () ->  shooter.shootingProcess2(true)
        );
        controller.getXButton().whenPressed(
                //[Shooter Subystem] High Goal Auto Aim 
                () ->  shooter.shootingProcess2(false)
        );
        if (controller.getRightTriggerAxis().getScale() > 0) {
                //[Shooter Subystem] Shoot Balls
                shooter.shootingProcess1();
        }
    }
}
