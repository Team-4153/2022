package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class StaticHookCommand extends CommandBase{
    private final ClimberSubsystem climb;

    public StaticHookCommand(ClimberSubsystem climb){
        this.climb = climb;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(climb.isHookOpen()) {
            climb.hookClose();
        }
        else if(!climb.isHookOpen()) {
            climb.hookOpen();
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }

}