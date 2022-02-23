
/* 

-Mechanics-
    - Pistons (5 TOTAL)
        - Elevator/arm pistons (2, left and right)
            - Allign arm straight
        - Stationary hook pistons (2, left and right)
            - Hook onto rung once elevated
        - Winch piston
            - Disengages and enganges winch so motors can start spooling/unspooling
    - Motors
        - Spooling motors
            - Spool thread

-Process-

1. Arm piston is activated - this should make the elevator straight to climb
2. Winch piston disengages



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
    public ClimberSubsystem(int spoolMotor, int solenoid_hook1, int solenoid_hook2, int solenoid_winch1, int solenoid_winch2, int aSwitch) {
        super();
        this.spoolMotor = new PWMSparkMax(spoolMotor);
        this.armPiston = new DoubleSolenoid(RobotMap.PH_CAN_ID, PneumaticsModuleType.REVPH, solenoid_hook1, solenoid_hook1);
        this.stathookPiston = new DoubleSolenoid(RobotMap.PH_CAN_ID, PneumaticsModuleType.REVPH, solenoid_winch1, solenoid_winch2);
        this.Switch = new DigitalInput(aSwitch);
    }

    private final PWMSparkMax spoolMotor;
    private final DoubleSolenoid armPiston;
    private final DoubleSolenoid stathookPiston;
    private final DigitalInput Switch;
    private static boolean Step2;

    public void RunMotor(){
        spoolMotor.set(1);
    }
    public void StopMotor(){
        spoolMotor.set(0);
    }
    public void Lock(){
        armPiston.set(DoubleSolenoid.Value.kForward);
    }
    public void Unlock(){
        armPiston.set(DoubleSolenoid.Value.kReverse);
    }
    public void Lock2(){
        stathookPiston.set(DoubleSolenoid.Value.kForward);
    }
    public void Unlock2() {
        stathookPiston.set(DoubleSolenoid.Value.kReverse);
        }
    public boolean GetSwitch(){
        return Switch.get();
    }
    public static boolean isStep2() {
        return Step2;
    }
    public static void setStep2(){
        Step2 = !Step2;
    }



}
