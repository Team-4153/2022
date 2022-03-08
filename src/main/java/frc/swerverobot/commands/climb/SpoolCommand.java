package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

import static frc.swerverobot.commands.climb.States.LOCKED;

public class SpoolCommand extends CommandBase{
    private final ClimberSubsystem climb;
    
    public SpoolCommand(ClimberSubsystem climb) {
        this.climb = climb;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        climb.spool();
    }


    @Override
    public void end(boolean interrupted) {
        climb.StopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
