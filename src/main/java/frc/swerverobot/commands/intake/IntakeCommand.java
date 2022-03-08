package frc.swerverobot.commands.intake;
import static frc.swerverobot.RobotMap.*;

import frc.swerverobot.RobotMap;
import frc.swerverobot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final IntakeSubsystem intake;
    private boolean compress;
    private boolean done;

    /**
     * Creates a new IntakeCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public IntakeCommand(IntakeSubsystem intake, boolean compress) {
        this.intake = intake;
        this.compress = compress;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        intake.setIntakeSol(compress);

        if(compress) {
            intake.setIntakeMotor(0.0);
            RobotMap.feedMotor.set(0.0);
            done = true;
        }
        else {
            intake.setIntakeMotor(-0.7);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return photoEye.get() || done;
    }
}
