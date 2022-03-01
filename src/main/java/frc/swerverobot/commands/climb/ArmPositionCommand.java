package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class ArmPositionCommand extends CommandBase{
    private final ClimberSubsystem climb;
    
    public ArmPositionCommand(ClimberSubsystem climb) {
        this.climb = climb;

        addRequirements(climb);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(climb.isArmUp()) {
            climb.armDown();
        }
        else if(!climb.isArmUp()) {
            climb.armUp();
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
