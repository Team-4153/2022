package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class WinchLockCommand extends CommandBase{
    private ClimberSubsystem climb;
    private boolean buttonPressed;

    public WinchLockCommand(ClimberSubsystem climb, BooleanSupplier buttonPressed) {
        this.climb = climb;
        this.buttonPressed = buttonPressed.getAsBoolean();

        addRequirements(climb);
    }


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(climb.isLocked() && buttonPressed) {
            climb.winchUnlock();
        }
        else if(!climb.isLocked() && buttonPressed) {
            climb.winchLock();
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }

}
