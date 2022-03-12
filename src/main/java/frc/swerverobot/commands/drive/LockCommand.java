package frc.swerverobot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.commands.climb.States;
import frc.swerverobot.subsystems.DrivetrainSubsystem;

public class LockCommand extends CommandBase {
    private final DrivetrainSubsystem subsystem;
    private final States state;
    public LockCommand(DrivetrainSubsystem in, States state) {
        subsystem = in;
        this.state = state;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        subsystem.lock(state);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }
}
