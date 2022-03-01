package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class WinchLockCommand extends CommandBase{
    private ClimberSubsystem climb;

    public WinchLockCommand(ClimberSubsystem climb) {
        this.climb = climb;

        addRequirements(climb);
    }


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(climb.isLocked()) {
            climb.winchUnlock();
        }
        else if(!climb.isLocked()) {
            climb.winchLock();
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }

}
