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
import org.frcteam2910.common.robot.input.DPadButton.Direction;

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
        //[Drive Subsystem]
        controller.getBackButton().whenPressed(
                //[Drive Subsystem]  Reset Gyro (Update if this is wrong I dont know)
                () -> drivetrain.resetGyroAngle(Rotation2.ZERO)
        );
        controller.getAButton().whenPressed(
                //[Drive Subsystem]  Drive (Update if this is wrong I dont know)
                () -> drivetrain.drive(vector0, 0, false)
        );
        controller.getBButton().whenPressed(
                //[Drive Subsystem]  Drives in Square (Update if this is wrong I dont know)
                new SquareCommand(drivetrain, 0.4, 1)
        );

        //[Climber Subsystem]
        controller.getYButton().whenPressed(
                //[Climber Subsystem] Climb
                new Climb(climber)
        );

        //[Intake Subsystem]
        controller.getLeftBumperButton().whenPressed(
                //[Intake Subsystem] Toggle Intake with Solenoid
                () ->  intake.Sol_toggle()
        );
        controller.getRightBumperButton().whileHeld(
                //[Intake Subsystem] Start Motor
                () ->  intake.Motor_Start()
        );
        controller.getRightBumperButton().whenReleased(
                //[Intake Subsystem] Stop Motor
                () ->  intake.Motor_Stop()
        );

        //[Shooter Subsystem]
          //shootingProcess1(Right Trigger) - Shoots balls this will use values from auto aim to shoot, The driver can also manually change these values with D-Pad Up || D-Pad Down
          //shootingProcess2(X for High Goal | A for Low Goal) - Auto aims the robot but doesent shoot, the boolean is for aiming for the high or low goal (true = High || false = Low)
          //shootingProcess3(Not A Button Yet) - Auto aims the robot and shoots, the boolean is for aiming for the high or low goal (true = High || false = Low)
          //manualShooterDistanceIncrease(D-Pad Up) - Increases the power to both shooter motors by 5%, doesent shoot balls
          //manualShooterDistanceDecrease(D-Pad Down) - Decrease the power to both shooter motors by 5%, doesent shoot balls
        if (controller.getRightTriggerAxis().getScale() > 0) {
                //[Shooter Subsystem] Shoot Balls
                shooter.shootingProcess1();
        }
        controller.getAButton().whenPressed(
                //[Shooter Subsystem] Low Goal Auto Aim
                () ->  shooter.shootingProcess2(false)
        );
        controller.getXButton().whenPressed(
                //[Shooter Subsystem] High Goal Auto Aim 
                () ->  shooter.shootingProcess2(true)
        );
        controller.getDPadButton(Direction.UP).whenPressed(
                //[Shooter Subsystem] Manually Increase Shooter Distance by 5%
                () ->  shooter.manualShooterDistanceIncrease()
        );
        controller.getDPadButton(Direction.DOWN).whenPressed(
                //[Shooter Subsystem] Manually Decrease Shooter Distance by 5%
                () ->  shooter.manualShooterDistanceDecrease()
        );
    }
}
