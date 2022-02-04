package frc.swerverobot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class Climb extends CommandBase {
    private final ClimberSubsystem subsystem;
    public Climb(ClimberSubsystem in){
        subsystem = in;
    }

    @Override
    public void initialize() {
        subsystem.Unlock();
    }

    @Override
    public void execute() {
        while(!subsystem.GetSwitch()){
            subsystem.RunMotor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted){
            subsystem.StopMotor();
            subsystem.Lock();
        }
        else{
            subsystem.StopMotor();
        }

    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
