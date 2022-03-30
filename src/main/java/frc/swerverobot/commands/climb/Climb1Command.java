package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.swerverobot.subsystems.ClimberSubsystem;

@Deprecated
@SuppressWarnings("unused")

public class Climb1Command extends Command {
    private final ClimberSubsystem subsystem;
    public Climb1Command(ClimberSubsystem in) {
        this.subsystem = in;
    }
    /**
     * The 'initialize' method is called one time just prior to the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {

    }

    /**
     * The 'execute' method is the main body of a command.  Is called repeatedly when
     * this * Command is scheduled to run. It is called repeatedly until either the
     * command finishes -- i.e. {@link #isFinished()}) returns true -- or it is
     * canceled.
     */
    @Override
    protected void execute() {
/*        if(!ClimberSubsystem.isStep2()){
            subsystem.winchUnlock();
            subsystem.unspool();
            subsystem.RunMotor();
            ClimberSubsystem.setStep2();
        }
        else{
            subsystem.Lock2();
        }*/
    }

    /**
     * <p>
     * Returns whether this command is finished. If it is, then the command will be removed and
     * {@link #end}()} will be called.
     * </p><p>
     * It may be useful for a team to reference the {@link #isTimedOut}()}
     * method for time-sensitive commands.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Returning true will result in the
     * command executing once and finishing immediately. It is recommended to use
     * {@link edu.wpi.first.wpilibj.command.InstantCommand} (added in 2017) for this.
     * </p>
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut}() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished}()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {

    }

    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command that requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end}()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {

    }
}
