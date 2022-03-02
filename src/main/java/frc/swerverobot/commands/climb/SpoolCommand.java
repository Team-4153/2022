package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

import static frc.swerverobot.commands.climb.States.LOCKED;

public class SpoolCommand extends CommandBase{
    private final ClimberSubsystem climb;
    private final States state;
    
    public SpoolCommand(ClimberSubsystem climb, States state) {
        this.climb = climb;
        this.state = state;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(state == LOCKED){
            climb.setLocked(false);
        }
        climb.spool();
    }


    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return climb.GetSwitch();
    }
    
}
