package frc.swerverobot.commands.auto;

import org.frcteam2910.common.math.Vector2;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;

@Deprecated

public class SpinNSearchCommand extends CommandBase{
    private final DrivetrainSubsystem drivetrain;

    public SpinNSearchCommand(DrivetrainSubsystem drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drivetrain.drive(
            Vector2.ZERO, 0.3, true);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return SmartDashboard.getNumber("IntakeBall/BallX", 0.0) > 0.0 && SmartDashboard.getNumber("IntakeBall/BallRadius", 0.0) > 0.3;
    }


}
