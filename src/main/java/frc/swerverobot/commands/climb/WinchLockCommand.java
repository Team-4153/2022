package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class WinchLockCommand extends CommandBase{
    private ClimberSubsystem climb;
    private final States state;

    public WinchLockCommand(ClimberSubsystem climb, States state) {
        this.climb = climb;
        this.state = state;
        addRequirements(climb);
    }


    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        switch (state){
            case UNLOCKED:
                climb.winchLock();
                break;
            case LOCKED:
                climb.winchUnlock();
                break;
            case TOGGLE:
                if(climb.isLocked()) {
                    climb.winchUnlock();
                }
                else {
                    climb.winchLock();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }

    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }

}
