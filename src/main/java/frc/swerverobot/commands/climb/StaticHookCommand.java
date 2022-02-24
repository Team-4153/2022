package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class StaticHookCommand extends CommandBase{
    private final ClimberSubsystem climb;
    private boolean buttonPressed;

    public StaticHookCommand(ClimberSubsystem climb, BooleanSupplier buttonPressed){
        this.climb = climb;
        this.buttonPressed = buttonPressed.getAsBoolean();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(buttonPressed && climb.isHookOpen()) {
            climb.hookClose();
        }
        else if(buttonPressed && !climb.isHookOpen()) {
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