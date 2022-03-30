package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

@Deprecated
@SuppressWarnings("unused")

public class ArmPositionCommand extends CommandBase{
    private final ClimberSubsystem climb;
    private final States state;
    
    public ArmPositionCommand(ClimberSubsystem climb, States state) {
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
            case LOCKED:
                climb.armUp();
                break;
            case UNLOCKED:
                climb.armDown();
                break;
            case TOGGLE:
                if(climb.isArmUp()) {
                    climb.armDown();
                }
                else {
                    climb.armUp();
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
