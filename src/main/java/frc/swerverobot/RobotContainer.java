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

import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;
import org.frcteam2910.common.robot.input.DPadButton.Direction;

public class RobotContainer {
    private final Controller controller = Driver_controller;
    private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final ClimberSubsystem climber = new ClimberSubsystem(CLIMBER_MOTOR, HOOKa, HOOKb, WINCH_SOLa, WINCH_SOLb, CLIMBER_SWITCH);


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
       intake.init();
       shooter.Button_Binding();
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
        /*  
        controller.getYButton().whenPressed(
                //[Climber Subsystem] Climb
                (Command) new Climb1Command(climber)
        );
        */
    }
}
