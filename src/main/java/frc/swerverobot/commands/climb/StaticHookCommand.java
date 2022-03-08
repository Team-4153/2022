package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class StaticHookCommand extends CommandBase{
    private final ClimberSubsystem climb;
    private final States state;
    public StaticHookCommand(ClimberSubsystem climb, States state){
        this.climb = climb;
        this.state = state;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        switch (state){
            case LOCKED:
                climb.hookOpen();
                break;
            case UNLOCKED:
                climb.hookClose();
                break;
            case TOGGLE:
                if(climb.isHookOpen()) {
                    climb.hookClose();
                }
                else {
                    climb.hookOpen();
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