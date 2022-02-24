
/* 

-Mechanics-
    - Pistons (5 TOTAL)
        - Elevator/arm pistons (1 solenoid controls both pistons)
            - Allign arm straight
        - Stationary hook pistons (1 solenoid controls both pistons)
            - Hook onto rung once elevated
        - Winch piston (uses double solenoid for lock and unlock)
            - Disengages and enganges winch so motors can start spooling/unspooling
    - Motors
        - Spooling motors
            - Spool thread

-Process-

BUTTON 1: (Toggle) Winch lock/unlock
BUTTON 2: (When pressed) Spools
BUTTON 3: (Toggle) Arm piston forward/back
BUTTON 4: (Toggle) Static hook open/close
(not implemented in current design) BUTTON 5: (Toggle) Release/engage arm piston pressure (do we include restrictor stuff with this command?)

Every-Single-Step Command sequence:
1. Arm piston goes forward - makes elevator vertical (BUTTON 3)
2. Unlock winch solenoid -> constant force spring fully extends elevator (BUTTON 1)
3. Lock winch solenoid (BUTTON 1)
--- DRIVER orients robot such that arm hook is above rung
4. Spool motor spools, making arm contract and pull robot up to rung (BUTTON 2)
5. Static hook closes on rung (BUTTON 4)
6. Unlock winch solenoid - step 2 again (BUTTON 1)
7. Lock winch solenoid (BUTTON 1)
8. Slightly spool motor to avoid hitting next rung (BUTTON 2)
9. Arm piston goes back - makes elevator lean to next rung (BUTTON 3)
10. Unlock winch solenoid - step 2 again (BUTTON 1)
11. Lock winch solenoid (BUTTON 1)
12. Arm piston goes forward - makes elevator push against rung (BUTTON 3)
13. Spool motor spools until arm is on the next rung (BUTTON 2)
(not implemented in current design) 14. Release pressure in arm piston so the piston doesn't get damaged when swinging from rung to rung (BUTTON 5)
15. Open static hook so robot swings to next rung (BUTTON 4)
16. Repeat from part 4 ---- (not implemented in current design) engage arm piston pressure again when static hook is closed - AKA at repeat of 5 (BUTTON 5)


More intuitive for driver:
1. Winch unlock command - elevator shoots up
--- DRIVER moves and orients the hook on rung
2. PullAndGrab command - lock winch, pull the elevator all the way up, and close the static hook when the limit switch is activated
--- DRIVER makes sure static hook is closed on rung
3. GetToNextRung command - unlock winch a little bit, turn the hook down to point towards next rung, fully extend, bring the hook up to orient for the next rung
--- DRIVER makes sure the arm is positioned correctly for spooling to hook
4. DRIVER presses SpoolCommand button until hook is securely on rung (potentially will add autonomously later)
5. DRIVER presses StaticHookCommand button to open static hook and swing robot (OMG)
6. Repeat from step 2 - 5 for last rung.


*/

package frc.swerverobot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.swerverobot.RobotMap;

public class ClimberSubsystem extends SubsystemBase{
    //Initiator
    public ClimberSubsystem() {
        super();
        this.spoolMotor = new PWMSparkMax(RobotMap.CLIMBER_MOTOR);
        this.armPiston = new DoubleSolenoid(RobotMap.PH_CAN_ID, PneumaticsModuleType.REVPH, RobotMap.ARM_FORWARD, RobotMap.ARM_BACK);
        this.stathookPiston = new DoubleSolenoid(RobotMap.PH_CAN_ID, PneumaticsModuleType.REVPH, RobotMap.HOOK_CLOSE, RobotMap.HOOK_OPEN);
        this.winchSolenoid = new DoubleSolenoid(RobotMap.PH_CAN_ID, PneumaticsModuleType.REVPH, RobotMap.WINCH_LOCK, RobotMap.WINCH_UNLOCK);
        this.Switch = new DigitalInput(RobotMap.CLIMBER_SWITCH);
    }

    private final PWMSparkMax spoolMotor;
    private final DoubleSolenoid armPiston;
    private final DoubleSolenoid stathookPiston;
    private final DoubleSolenoid winchSolenoid;
    private final DigitalInput Switch;
    private static boolean Step2;
    private boolean locked;
    private boolean armUp;
    private boolean hookOpen;

    public void spool(){
        spoolMotor.set(1);
    }

    public void StopMotor(){
        spoolMotor.set(0);
    }

    public void armUp(){
        armPiston.set(DoubleSolenoid.Value.kForward);
        setArmUp(true);
    }

    public void armDown() {
        armPiston.set(DoubleSolenoid.Value.kReverse);
        setArmUp(false);
    }

    public void hookClose(){
        stathookPiston.set(DoubleSolenoid.Value.kForward);
        setHookOpen(false);
    }

    public void hookOpen() {
        stathookPiston.set(DoubleSolenoid.Value.kReverse);
        setHookOpen(true);
    }

    public void winchLock(){
        winchSolenoid.set(DoubleSolenoid.Value.kForward);
        setLocked(true);
    }

    public void winchUnlock(){
        winchSolenoid.set(DoubleSolenoid.Value.kReverse);
        setLocked(false);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean set) {
        locked = set;
    }

    public boolean isArmUp() {
        return armUp;
    }

    public void setArmUp(boolean set) {
        armUp = set;
    }

    public boolean isHookOpen() {
        return hookOpen;
    }

    public void setHookOpen(boolean set) {
        hookOpen = set;
    }

    public boolean GetSwitch(){
        return Switch.get();
    }

    public static boolean isStep2() {
        return Step2;
    }

    public static void flipStep2(){
        Step2 = !Step2;
    }



}
