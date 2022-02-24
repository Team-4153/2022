package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class SpoolCommand extends CommandBase{
    private final ClimberSubsystem climb;
    private boolean buttonPressed;
    
    public SpoolCommand(ClimberSubsystem climb, BooleanSupplier buttonPressed) {
        this.climb = climb;
        this.buttonPressed = buttonPressed.getAsBoolean();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(!climb.isLocked()) {
            climb.setLocked(true);
        }


        while(buttonPressed) {
            climb.spool();
        }
        climb.StopMotor();
    }


    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return climb.GetSwitch();
    }
    
}
