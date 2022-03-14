package frc.swerverobot.commands.intake;

import frc.swerverobot.RobotMap;
import frc.swerverobot.subsystems.ShooterSubsystem2;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class FeedCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final ShooterSubsystem2 shooter;


    /**
     * Creates a new IntakeCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public FeedCommand(ShooterSubsystem2 shooter) {
        this.shooter = shooter;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        RobotMap.feedMotor.set(-0.4);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotMap.feedMotor.set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
        // return shooter.ball1color() != "none";
    }
}
