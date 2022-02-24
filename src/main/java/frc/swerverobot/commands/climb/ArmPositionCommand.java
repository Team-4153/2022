package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class ArmPositionCommand extends CommandBase{
    private final ClimberSubsystem climb;
    private boolean buttonPressed;
    
    public ArmPositionCommand(ClimberSubsystem climb, BooleanSupplier buttonPressed) {
        this.climb = climb;
        this.buttonPressed = buttonPressed.getAsBoolean();

        addRequirements(climb);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(buttonPressed && climb.isArmUp()) {
            climb.armDown();
        }
        else if(buttonPressed && !climb.isArmUp()) {
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
