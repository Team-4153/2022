package frc.swerverobot.commands.LED;

import frc.swerverobot.commands.LED.SetHighGoalAuto;
import frc.swerverobot.commands.LED.SetT;
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

//Raspi Auto 3 Ball High Goal

public class TickLEDSStep2 extends SequentialCommandGroup{
    private final LEDSubsystem LED;
    private double angle;

    public TickLEDSStep2(LEDSubsystem LED, Boolean initalWait) {
        this.LED = LED;

        addRequirements(LED);

        if (initalWait) {
            addCommands(
                new WaitCommand(500),                                                                         //Wait for 1 Second
                new TickLEDS(LED),                                                                          //Move LEDS forward 1
                new TickLEDSStep2(LED, false)                                                                      //Run Again In 1 Second
            );
        }
        else {
            addCommands(
                new WaitCommand(1),                                                                         //Wait for 1 Second
                new TickLEDS(LED),                                                                          //Move LEDS forward 1
                new TickLEDSStep2(LED, false)                                                                      //Run Again In 1 Second
            );
        }

        

    }

}
